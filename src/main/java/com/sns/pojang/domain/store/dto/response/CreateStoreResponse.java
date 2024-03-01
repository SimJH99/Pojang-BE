package com.sns.pojang.domain.store.dto.response;

import com.sns.pojang.domain.store.entity.Address;
import com.sns.pojang.domain.store.entity.Store;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@Builder
public class CreateStoreResponse {
    private String name;
    private String category;
    private String storeNumber;
    private String businessNumber;
    private String imageUrl;
    private Address address;

    public static CreateStoreResponse from(Store store) {
        return CreateStoreResponse.builder()
                .name(store.getName())
                .category(store.getCategory())
                .storeNumber(store.getStoreNumber())
                .businessNumber(store.getBusinessNumber())
                .imageUrl(store.getImageUrl())
                .address(store.getAddress())
                .build();
    }
}
