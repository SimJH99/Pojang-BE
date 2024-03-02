package com.sns.pojang.domain.menu.controller;

import com.sns.pojang.domain.menu.dto.request.FindMenuOptionsRequest;
import com.sns.pojang.domain.menu.dto.request.MenuRequest;
import com.sns.pojang.domain.menu.dto.request.OptionGroupRequest;
import com.sns.pojang.domain.menu.dto.response.CreateMenuResponse;
import com.sns.pojang.domain.menu.dto.response.MenuOptionGroupResponse;
import com.sns.pojang.domain.menu.dto.response.MenuOptionInfoResponse;
import com.sns.pojang.domain.menu.dto.response.MenuResponse;
import com.sns.pojang.domain.menu.service.MenuService;
import com.sns.pojang.global.response.SuccessResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.sns.pojang.global.response.SuccessMessage.*;

@RestController
@RequestMapping("/api/stores")
@Slf4j
public class MenuController {
    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

        @PreAuthorize("hasRole('OWNER')")
        @PostMapping("/{storeId}/menus")
        public ResponseEntity<SuccessResponse<CreateMenuResponse>> createMenu(
                @PathVariable Long storeId, @Valid MenuRequest menuRequest){
            log.info("메뉴 이미지 정보: " + menuRequest.getImage().getOriginalFilename());
            return ResponseEntity.created(URI.create("/" + storeId + "/menus"))
                    .body(SuccessResponse.create(HttpStatus.CREATED.value(),
                            CREATE_MENU_SUCCESS.getMessage(),
                            menuService.createMenu(storeId, menuRequest)));
        }

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/{storeId}/menus/{menuId}/options")
    public ResponseEntity<SuccessResponse<MenuOptionGroupResponse>> createOptions(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @Valid @RequestBody OptionGroupRequest optionGroupRequest){
        return ResponseEntity.created(URI.create("/" + storeId + "/menus" + + menuId + "/option-groups"))
                .body(SuccessResponse.create(HttpStatus.CREATED.value(),
                        CREATE_MENU_OPTION_SUCCESS.getMessage(),
                        menuService.createOptions(storeId, menuId, optionGroupRequest)));
    }

    @PreAuthorize("hasRole('OWNER')")
    @PatchMapping("/{storeId}/menus/{menuId}")
    public ResponseEntity<SuccessResponse<CreateMenuResponse>> updateMenu(
            @PathVariable Long storeId, @PathVariable Long menuId, @Valid MenuRequest menuRequest){
        return ResponseEntity.ok(SuccessResponse.update(HttpStatus.OK.value(),
                        UPDATE_MENU_SUCCESS.getMessage(),
                        menuService.updateMenu(storeId, menuId, menuRequest)));
    }

    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("/{storeId}/menus/{menuId}")
    public ResponseEntity<SuccessResponse<Void>> deleteMenu(
            @PathVariable Long storeId, @PathVariable Long menuId){
        menuService.deleteMenu(storeId, menuId);
        return ResponseEntity.ok(SuccessResponse.delete(HttpStatus.OK.value(),
                DELETE_MENU_SUCCESS.getMessage()));
    }

    @GetMapping("/{storeId}/menus/{menuId}/image")
    public ResponseEntity<Resource> findImage(@PathVariable Long storeId, @PathVariable Long menuId){
        Resource resource = menuService.findImage(storeId, menuId);
        HttpHeaders headers = new HttpHeaders(); // 파일의 타입을 스프링에 알려주기 위함
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @GetMapping("/{storeId}/menus")
    // Page size default: 20, 0번 페이지부터 시작
    public ResponseEntity<SuccessResponse<List<MenuResponse>>> findMenus(@PathVariable Long storeId, Pageable pageable){
        return ResponseEntity.ok(SuccessResponse.read(HttpStatus.OK.value(),
                GET_MENUS_SUCCESS.getMessage(),
                menuService.getMenus(storeId, pageable)));
    }

    @GetMapping("/{storeId}/menus/{menuId}")
    public ResponseEntity<SuccessResponse<MenuResponse>> getMenuDetail(@PathVariable Long storeId,
                                                                             @PathVariable Long menuId){
        return ResponseEntity.ok(SuccessResponse.read(HttpStatus.OK.value(),
                GET_MENU_DETAIL_SUCCESS.getMessage(),
                menuService.getMenuDetail(storeId, menuId)));
    }

    // 메뉴 옵션 객체 리스트 받아오기
    @PostMapping("/{storeId}/options")
    public ResponseEntity<SuccessResponse<List<MenuOptionInfoResponse>>> findOptionObjects(
            @PathVariable Long storeId,
            @RequestBody FindMenuOptionsRequest findMenuOptionsRequest){
        return ResponseEntity.ok(SuccessResponse.read(HttpStatus.OK.value(),
                GET_MENU_OPTION_SUCCESS.getMessage(),
                menuService.findOptionObjects(storeId, findMenuOptionsRequest)));
    }
}
