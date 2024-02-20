package com.sns.pojang.domain.store.dto.response;

import com.sns.pojang.domain.review.dto.response.RatingResponse;
import com.sns.pojang.domain.store.entity.Address;
import com.sns.pojang.domain.store.entity.Store;
import lombok.Builder;
import lombok.Getter;

import java.text.DecimalFormat;

@Getter
@Builder
public class SearchStoreInfoResponse {
    private Long id;
    private String name;
    private int likes;
    private String avgRating;
    private String imageUrl;
    private String address;
    private String businessNumber;
    private String category;
    private String storeNumber;
    private String introduction;
    private String operationTime;

    public static SearchStoreInfoResponse from(Store store, int likes, double avgRating) {
        DecimalFormat df = new DecimalFormat("#.#");
        String sido = store.getAddress().getSido();
        String sigungu = store.getAddress().getSigungu();
        String query = store.getAddress().getQuery();
        String addressDetail = store.getAddress().getAddressDetail();
        return SearchStoreInfoResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .likes(likes)
                .avgRating(df.format(avgRating))
                .imageUrl(store.getImageUrl())
                .address(sido + " " + sigungu + " " + query + " " + addressDetail)
                .businessNumber(store.getBusinessNumber())
                .category(store.getCategory())
                .storeNumber(store.getStoreNumber())
                .introduction(store.getIntroduction())
                .operationTime(store.getOperationTime())
                .build();
    }
}
