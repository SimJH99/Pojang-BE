package com.sns.pojang.domain.favorite.dto.response;

import com.sns.pojang.domain.favorite.entity.Favorite;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateFavoriteResponse {
    private String nickname;
    private String storeName;

    public static CreateFavoriteResponse from(Favorite favorite) {
        return CreateFavoriteResponse.builder()
                .nickname(favorite.getMember().getNickname())
                .storeName(favorite.getStore().getName())
                .build();
    }
}
