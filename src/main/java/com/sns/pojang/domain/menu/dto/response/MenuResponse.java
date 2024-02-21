package com.sns.pojang.domain.menu.dto.response;

import com.sns.pojang.domain.menu.entity.Menu;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MenuResponse {
    private Long id;
    private String store; // 가게명
    private String info; // 메뉴 정보
    private String name; // 메뉴명
    private int price; // 메뉴 가격
    private String imageUrl; // 메뉴 이미지 url

    public static MenuResponse from(Menu menu){

        return MenuResponse.builder()
                .id(menu.getId())
                .store(menu.getStore().getName())
                .name(menu.getName())
                .info(menu.getMenuInfo())
                .price(menu.getPrice())
                .imageUrl(menu.getImageUrl())
                .build();
    }
}
