package com.sns.pojang.domain.store.service;

import com.sns.pojang.domain.store.dto.request.CreateStoreRequest;
import com.sns.pojang.domain.store.dto.response.CreateStoreResponse;
import com.sns.pojang.domain.store.entity.Store;
import com.sns.pojang.domain.store.exception.BusinessNumberDuplicateException;
import com.sns.pojang.domain.store.exception.BusinessNumberNotFoundException;
import com.sns.pojang.domain.store.repository.BusinessNumberRepository;
import com.sns.pojang.domain.store.repository.StoreRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
        if(storeRepository.findByBusinessNumber(createStoreRequest.getBusinessNumber()).isPresent()){
            throw new BusinessNumberDuplicateException();
        }

        Store store = createStoreRequest.toEntity();
        return CreateStoreResponse.from(storeRepository.save(store));
    }
}
