package com.sns.pojang.domain.menu.dto.response;

import com.sns.pojang.domain.menu.entity.Menu;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MenuResponse {
    private Long id;
    private String storeName; // 가게명
    private String menuName; // 메뉴명
    private int price; // 메뉴 가격
    private String imageUrl; // 메뉴 이미지 url

    public static MenuResponse from(Menu menu){

        return MenuResponse.builder()
                .id(menu.getId())
                .storeName(menu.getStore().getName())
                .menuName(menu.getName())
                .price(menu.getPrice())
                .imageUrl(menu.getImageUrl())
                .build();
    }
}
