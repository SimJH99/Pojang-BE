package com.sns.pojang.domain.store.dto.response;

import com.sns.pojang.domain.member.dto.response.FindAddressResponse;
import com.sns.pojang.domain.member.entity.Member;
import com.sns.pojang.domain.store.entity.Status;
import com.sns.pojang.domain.store.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.text.DecimalFormat;

@Getter
@Builder
@AllArgsConstructor
public class SearchStoreResponse {
    private Long id;
    private String name;
    private String imageUrl;
    private String category;
    private Status status;
    private String avgRating;
    private int countOrder;
    private int likes;

    public static SearchStoreResponse from(Store store, int countOrder, double avgRating, int likes, String s3Url){
        DecimalFormat df = new DecimalFormat("#.#");
        return SearchStoreResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .category(store.getCategory())
                .imageUrl(s3Url)
                .status(store.getStatus())
                .avgRating(df.format(avgRating))
                .countOrder(countOrder)
                .likes(likes)
                .build();
    }
}
