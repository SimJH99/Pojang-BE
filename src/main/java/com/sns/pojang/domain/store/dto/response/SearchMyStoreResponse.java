package com.sns.pojang.domain.store.dto.response;

import com.sns.pojang.domain.store.entity.Address;
import com.sns.pojang.domain.store.entity.Status;
import com.sns.pojang.domain.store.entity.Store;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SearchMyStoreResponse {
    private Long id;
    private String name;
    private String imageUrl;
    private Address address;
    private String businessNumber;
    private String category;
    private String storeNumber;
    private String introduction;
    private Status status;
    private String operationTime;

    public static SearchMyStoreResponse from(Store store) {
        return SearchMyStoreResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .imageUrl(store.getImageUrl())
                .address(store.getAddress())
                .businessNumber(store.getBusinessNumber())
                .category(store.getCategory())
                .storeNumber(store.getStoreNumber())
                .introduction(store.getIntroduction())
                .status(store.getStatus())
                .operationTime(store.getOperationTime())
                .build();
    }
}
