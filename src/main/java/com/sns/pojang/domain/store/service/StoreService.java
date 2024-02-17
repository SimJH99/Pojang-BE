package com.sns.pojang.domain.store.service;

import com.sns.pojang.domain.store.dto.request.CreateStoreRequest;
import com.sns.pojang.domain.store.dto.request.UpdateStoreRequest;
import com.sns.pojang.domain.store.dto.response.CreateStoreResponse;
import com.sns.pojang.domain.store.dto.response.UpdateStoreResponse;
import com.sns.pojang.domain.store.entity.Store;
import com.sns.pojang.domain.store.exception.BusinessNumberDuplicateException;
import com.sns.pojang.domain.store.exception.BusinessNumberNotFoundException;
import com.sns.pojang.domain.store.repository.BusinessNumberRepository;
import com.sns.pojang.domain.store.repository.StoreRepository;
import com.sns.pojang.global.error.ErrorCode;
import com.sns.pojang.global.error.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
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
public class StoreService {

    private final StoreRepository storeRepository;
    private final BusinessNumberRepository businessNumberRepository;

    @Value("${image.path}")
    private String imagePath;

    public StoreService(StoreRepository storeRepository, BusinessNumberRepository businessNumberRepository) {
        this.storeRepository = storeRepository;
        this.businessNumberRepository = businessNumberRepository;
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

        MultipartFile multipartFile = createStoreRequest.getImageUrl();
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

        Store store = storeRepository.save(createStoreRequest.toEntity(path != null ? path.toString() : null));

        return CreateStoreResponse.from(storeRepository.save(store));
    }

    public UpdateStoreResponse updateStore(Long id, UpdateStoreRequest updateStoreRequest) {
        Store store = storeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(STORE_NOT_FOUND));

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
}
