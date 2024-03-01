package com.sns.pojang.domain.member.dto.response;

import com.sns.pojang.domain.favorite.entity.Favorite;
import com.sns.pojang.domain.store.entity.Status;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindFavoritesResponse {
    private Long id;
    private Long storeId;
    private String nickname;
    private String storeName;
    private String category;
    private Status storeStatus;
    private String imageUrl;

    public static FindFavoritesResponse from(Favorite favorite, String s3Url) {
        return FindFavoritesResponse.builder()
                .id(favorite.getId())
                .storeId(favorite.getStore().getId())
                .nickname(favorite.getMember().getNickname())
                .storeName(favorite.getStore().getName())
                .category(favorite.getStore().getCategory())
                .storeStatus(favorite.getStore().getStatus())
                .imageUrl(s3Url)
                .build();
    }
}
