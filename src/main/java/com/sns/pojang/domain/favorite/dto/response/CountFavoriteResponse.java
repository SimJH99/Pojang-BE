package com.sns.pojang.domain.favorite.dto.response;

import com.sns.pojang.domain.store.entity.Store;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CountFavoriteResponse {
    private String storeName;
    private int count;

    public static CountFavoriteResponse from(int count, Store store) {
        return CountFavoriteResponse.builder()
                .storeName(store.getName())
                .count(count)
                .build();
    }
}
