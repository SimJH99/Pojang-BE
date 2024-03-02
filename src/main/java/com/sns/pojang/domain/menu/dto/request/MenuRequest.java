package com.sns.pojang.domain.menu.dto.request;

import com.sns.pojang.domain.menu.entity.Menu;
import com.sns.pojang.domain.store.entity.Store;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

// 한 매장에서 한 개의 주문만 생성 가능
@Data
public class MenuRequest {
    @NotEmpty(message = "메뉴 이름을 입력하세요")
    private String name; // 메뉴 이름
    private String menuInfo; // 메뉴 정보
    @NotNull(message = "메뉴 가격을 입력하세요")
    private int price; // 메뉴 가격
    private MultipartFile image; // 메뉴 이미지

    public Menu toEntity(Store store, String imageUrl){

        return Menu.builder()
                .name(name)
                .menuInfo(menuInfo)
                .price(price)
                .imageUrl(imageUrl)
                .store(store)
                .build();
    }
}
