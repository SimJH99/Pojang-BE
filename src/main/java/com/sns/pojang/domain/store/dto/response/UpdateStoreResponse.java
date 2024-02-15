package com.sns.pojang.domain.store.dto.response;

import com.sns.pojang.domain.store.entity.Address;
import com.sns.pojang.domain.store.entity.Store;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateStoreResponse {
    private String name;
    private String category;
    private Address address;
    private String storeNumber;
    private String introduction;
    private String operationTime;


    public static UpdateStoreResponse from(Store store) {
        return UpdateStoreResponse.builder()
                .name(store.getName())
                .category(store.getCategory())
                .address(store.getAddress())
                .storeNumber(store.getStoreNumber())
                .introduction(store.getIntroduction())
                .operationTime(store.getOperationTime())
                .build();
    }
}
