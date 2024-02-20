package com.sns.pojang.domain.member.dto.response;

import com.sns.pojang.domain.favorite.entity.Favorite;
import com.sns.pojang.domain.store.entity.Status;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindFavoritesResponse {
    private Long id;
    private String nickname;
    private String storeName;
    private String category;
    private Status storeStatus;

    public static FindFavoritesResponse from(Favorite favorite) {
        return FindFavoritesResponse.builder()
                .id(favorite.getId())
                .nickname(favorite.getMember().getNickname())
                .storeName(favorite.getStore().getName())
                .category(favorite.getStore().getCategory())
                .storeStatus(favorite.getStore().getStatus())
                .build();
    }
}
