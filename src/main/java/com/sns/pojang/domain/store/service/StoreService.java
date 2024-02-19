package com.sns.pojang.domain.store.service;

import com.sns.pojang.domain.member.entity.Member;
import com.sns.pojang.domain.member.repository.MemberRepository;
import com.sns.pojang.domain.store.dto.request.CreateStoreRequest;
import com.sns.pojang.domain.store.dto.request.UpdateStoreRequest;
import com.sns.pojang.domain.store.dto.response.CreateStoreResponse;
import com.sns.pojang.domain.store.dto.response.MyStoreResponse;
import com.sns.pojang.domain.store.dto.response.UpdateStoreResponse;
import com.sns.pojang.domain.store.entity.Store;
import com.sns.pojang.domain.store.exception.BusinessNumberDuplicateException;
import com.sns.pojang.domain.store.exception.BusinessNumberNotFoundException;
import com.sns.pojang.domain.store.repository.BusinessNumberRepository;
import com.sns.pojang.domain.store.repository.StoreRepository;
import com.sns.pojang.global.error.ErrorCode;
import com.sns.pojang.global.error.exception.EntityNotFoundException;
import com.sns.pojang.global.error.exception.InvalidValueException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.sns.pojang.global.error.ErrorCode.*;

@Service
@Transactional
public class StoreService {

    private final StoreRepository storeRepository;
    private final BusinessNumberRepository businessNumberRepository;
    private final MemberRepository memberRepository;

    public StoreService(StoreRepository storeRepository, BusinessNumberRepository businessNumberRepository, MemberRepository memberRepository) {
        this.storeRepository = storeRepository;
        this.businessNumberRepository = businessNumberRepository;
        this.memberRepository = memberRepository;
    }

    public CreateStoreResponse createStore(CreateStoreRequest createStoreRequest) throws BusinessNumberDuplicateException {
        //사업자 번호 대조
        if (businessNumberRepository.findByBusinessNumber(createStoreRequest.getBusinessNumber()).isEmpty()) {
            throw new BusinessNumberNotFoundException();
        }
        //사업자 번호 등록 여부 대조
        if (storeRepository.findByBusinessNumber(createStoreRequest.getBusinessNumber()).isPresent()) {
            throw new BusinessNumberDuplicateException();
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_FOUND));

        MultipartFile multipartFile = createStoreRequest.getImageUrl();
        String fileName = multipartFile != null ? multipartFile.getOriginalFilename() : null;

        Path path = null;

        if (fileName != null) {
            path = Paths.get("C:/Users/Playdata/Desktop/tmp", "_" + fileName);

            try {
                byte[] bytes = multipartFile.getBytes();
                Files.write(path, bytes, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            } catch (IOException e) {
                throw new InvalidValueException(IMAGE_INVALID_VALUE);
            }
        }

        Store store = storeRepository.save(createStoreRequest.toEntity(path != null ? path.toString() : null, member));

        return CreateStoreResponse.from(storeRepository.save(store));
    }

    public UpdateStoreResponse updateStore(Long id, UpdateStoreRequest updateStoreRequest) {
        Store store = storeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(STORE_NOT_FOUND));

        MultipartFile multipartFile = updateStoreRequest.getImageUrl();
        String fileName = multipartFile != null ? multipartFile.getOriginalFilename() : null;

        Path path = null;

        if (fileName != null) {
            path = Paths.get("C:/Users/Playdata/Desktop/tmp", "_" + fileName);

            try {
                byte[] bytes = multipartFile.getBytes();
                Files.write(path, bytes, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            } catch (IOException e) {
                throw new IllegalArgumentException("이미지를 사용할 수 없습니다.");
            }
        }

        store.updateStore(updateStoreRequest.getName(),
                updateStoreRequest.getCategory(),
                updateStoreRequest.getSido(),
                updateStoreRequest.getSigungu(),
                updateStoreRequest.getQuery(),
                updateStoreRequest.getAddressDetail(),
                updateStoreRequest.getStoreNumber(),
                updateStoreRequest.getIntroduction(),
                updateStoreRequest.getOperationTime(),
                path != null ? path.toString() : null);

        return UpdateStoreResponse.from(storeRepository.save(store));
    }

    public void deleteStore(Long id) {
        Store store = storeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(STORE_NOT_FOUND));
        store.isDelete();
    }

    public List<MyStoreResponse> myStore(Long memberId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_FOUND));
        //본인이 아닌 다른 owner회원이 조회하지 못하게 분기처리
        if (memberId != member.getId()) {
            throw new InvalidValueException(NOT_INVALID_VALUE_MEMBER);
        }
        List<Store> stores = storeRepository.findAllByMemberId(memberId);
        if (stores.size() == 0){
            throw new EntityNotFoundException(MY_STORE_NOT_FOUND);
        }
            return stores.stream().map(o -> MyStoreResponse.from(o)).collect(Collectors.toList());
    }
}
