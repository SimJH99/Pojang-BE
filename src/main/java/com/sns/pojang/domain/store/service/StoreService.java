package com.sns.pojang.domain.store.service;

import com.sns.pojang.domain.store.dto.request.CreateStoreRequest;
import com.sns.pojang.domain.store.dto.request.UpdateStoreRequest;
import com.sns.pojang.domain.store.dto.response.CreateStoreResponse;
import com.sns.pojang.domain.store.dto.request.SearchStoreRequest;
import com.sns.pojang.domain.store.dto.response.SearchStoreResponse;
import com.sns.pojang.domain.store.dto.response.UpdateStoreResponse;
import com.sns.pojang.domain.store.entity.Store;
import com.sns.pojang.domain.store.exception.BusinessNumberDuplicateException;
import com.sns.pojang.domain.store.exception.BusinessNumberNotFoundException;
import com.sns.pojang.domain.store.repository.BusinessNumberRepository;
import com.sns.pojang.domain.store.repository.StoreRepository;
import com.sns.pojang.global.error.ErrorCode;
import com.sns.pojang.global.error.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StoreService {

    private final StoreRepository storeRepository;
    private final BusinessNumberRepository businessNumberRepository;

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
            path = Paths.get("C:/Users/Playdata/Desktop/tmp", "_" + fileName);

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
        Store store = storeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorCode.STORE_NOT_FOUND));

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
//    가게 조회 및 검색기능
    public List<SearchStoreResponse> findStores(SearchStoreRequest searchStoreRequest, Pageable pageable) {
//        검색을 위해 Specification 객체 사용

        Specification<Store> spec = new Specification<Store>() {
            @Override
            public Predicate toPredicate(Root<Store> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (searchStoreRequest.getName() != null) {
                    predicates.add(criteriaBuilder.like(root.get("name"), "%" + searchStoreRequest.getName() + "%"));
                }
                if (searchStoreRequest.getCategory() != null) {
                    predicates.add(criteriaBuilder.like(root.get("category"), "%" + searchStoreRequest.getCategory() + "%"));
                }
//                predicates.add(criteriaBuilder.equal(root.get("delYn"), "N"));
                Predicate[] predicatesArr = new Predicate[predicates.size()];
                for (int i = 0; i < predicates.size(); i++) {
                    predicatesArr[i] = predicates.get(i);
                }
                Predicate predicate = criteriaBuilder.and(predicatesArr);
                return predicate;
            }
        };

        Page<Store> stores = storeRepository.findAll(spec , pageable);
        List<Store> storeList = stores.getContent();
        List<SearchStoreResponse> searchStoreResponses = new ArrayList<>();
        searchStoreResponses = storeList.stream().map(i -> SearchStoreResponse.builder()
                .name(i.getName())
                .category(i.getCategory())
                .imageUrl(i.getImageUrl())
                .status(i.getStatus())
                .build()
        ).collect(Collectors.toList());
        return searchStoreResponses;
    }
}
