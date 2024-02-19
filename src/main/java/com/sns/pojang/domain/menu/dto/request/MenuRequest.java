package com.sns.pojang.domain.menu.dto.request;

import com.sns.pojang.domain.menu.entity.Menu;
import com.sns.pojang.domain.store.entity.Store;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

// 한 매장에서 한 개의 주문만 생성 가능
@Data
public class MenuRequest {
    private String name; // 메뉴 이름
    private String menuInfo; // 메뉴 정보
    private int price; // 메뉴 가격
    private MultipartFile menuImage; // 메뉴 이미지

    public Menu toEntity(Store store, Path path){
        String imageUrl = path != null ? path.toString() : null;

        return Menu.builder()
                .name(name)
                .menuInfo(menuInfo)
                .price(price)
                .imageUrl(imageUrl)
                .store(store)
                .build();
    }
}
