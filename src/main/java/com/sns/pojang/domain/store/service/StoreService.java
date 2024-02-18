package com.sns.pojang.domain.store.service;

import com.sns.pojang.domain.member.entity.Member;
import com.sns.pojang.domain.member.exception.MemberNotFoundException;
import com.sns.pojang.domain.member.repository.MemberRepository;
import com.sns.pojang.domain.store.dto.request.CreateStoreRequest;
import com.sns.pojang.domain.store.dto.request.RegisterBusinessNumberRequest;
import com.sns.pojang.domain.store.dto.request.UpdateStoreRequest;
import com.sns.pojang.domain.store.dto.response.CreateStoreResponse;
import com.sns.pojang.domain.store.dto.response.UpdateStoreResponse;
import com.sns.pojang.domain.store.entity.BusinessNumber;
import com.sns.pojang.domain.store.entity.Store;
import com.sns.pojang.domain.store.exception.BusinessNumberDuplicateException;
import com.sns.pojang.domain.store.exception.BusinessNumberNotFoundException;
import com.sns.pojang.domain.store.repository.BusinessNumberRepository;
import com.sns.pojang.domain.store.repository.StoreRepository;
import com.sns.pojang.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static com.sns.pojang.global.error.ErrorCode.STORE_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;
    private final BusinessNumberRepository businessNumberRepository;

    @Value("${image.path}")
    private String imagePath;

    public BusinessNumber registerBusinessNumber(RegisterBusinessNumberRequest registerBusinessNumberRequest){
        BusinessNumber businessNumber = new BusinessNumber(registerBusinessNumberRequest.getBusinessNumber());
        return businessNumberRepository.save(businessNumber);
    }

    public CreateStoreResponse createStore(CreateStoreRequest createStoreRequest) throws BusinessNumberDuplicateException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member findMember = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(MemberNotFoundException::new);

        //사업자 번호 대조
        if (businessNumberRepository.findByBusinessNumber(createStoreRequest.getBusinessNumber()).isEmpty()) {
            throw new BusinessNumberNotFoundException();
        }
        //사업자 번호 등록 여부 대조
        if (storeRepository.findByBusinessNumber(createStoreRequest.getBusinessNumber()).isPresent()) {
            throw new BusinessNumberDuplicateException();
        }

        MultipartFile multipartFile = createStoreRequest.getStoreImage();
        String fileName = multipartFile != null ? multipartFile.getOriginalFilename() : null;

        Path path = null;

        if (fileName != null) {
            path = Paths.get(imagePath, fileName);
            try {
                byte[] bytes = multipartFile.getBytes();
                Files.write(path, bytes, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            } catch (IOException e) {
                throw new IllegalArgumentException("이미지를 사용할 수 없습니다.");
            }
        }
        Store savedStore = storeRepository.save(createStoreRequest.toEntity(path != null ? path.toString() : null, findMember));
        // Member List<Store>에 생성된 store 추가
        savedStore.getMember().getStores().add(savedStore);
        return CreateStoreResponse.from(storeRepository.save(savedStore));
    }

    public UpdateStoreResponse updateStore(Long id, UpdateStoreRequest updateStoreRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member findMember = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(MemberNotFoundException::new);
        Store findStore = storeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(STORE_NOT_FOUND));
        if (!findStore.getMember().equals(findMember)){
            throw new AccessDeniedException(findStore.getName() + "의 사장님이 아닙니다.");
        }

        MultipartFile multipartFile = updateStoreRequest.getImageUrl();
        String fileName = multipartFile != null ? multipartFile.getOriginalFilename() : null;

        Path path = null;

        if (fileName != null) {
            path = Paths.get(imagePath, fileName);

            try {
                byte[] bytes = multipartFile.getBytes();
                Files.write(path, bytes, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            } catch (IOException e) {
                throw new IllegalArgumentException("이미지를 사용할 수 없습니다.");
            }
        }

        findStore.updateStore(updateStoreRequest.getName(),
                updateStoreRequest.getCategory(),
                updateStoreRequest.getSido(),
                updateStoreRequest.getSigungu(),
                updateStoreRequest.getQuery(),
                updateStoreRequest.getAddressDetail(),
                updateStoreRequest.getStoreNumber(),
                updateStoreRequest.getIntroduction(),
                updateStoreRequest.getOperationTime(),
                path != null ? path.toString() : null);

        return UpdateStoreResponse.from(storeRepository.save(findStore));
    }

    public void deleteStore(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member findMember = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(MemberNotFoundException::new);
        Store findStore = storeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(STORE_NOT_FOUND));
        if (!findStore.getMember().equals(findMember)){
            throw new AccessDeniedException(findStore.getName() + "의 사장님이 아닙니다.");
        }
        findStore.isDelete();
    }
}
