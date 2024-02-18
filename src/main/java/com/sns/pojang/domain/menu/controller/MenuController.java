package com.sns.pojang.domain.menu.controller;

import com.sns.pojang.domain.menu.dto.request.CreateMenuOptionGroupRequest;
import com.sns.pojang.domain.menu.dto.request.MenuRequest;
import com.sns.pojang.domain.menu.dto.response.CreateMenuOptionGroupResponse;
import com.sns.pojang.domain.menu.dto.response.MenuResponse;
import com.sns.pojang.domain.menu.service.MenuService;
import com.sns.pojang.global.response.SuccessResponse;
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
public class MenuController {
    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/{storeId}/menus")
    public ResponseEntity<SuccessResponse<MenuResponse>> createMenu(
            @PathVariable Long storeId, @Valid MenuRequest menuRequest){
        return ResponseEntity.created(URI.create("/" + storeId + "/menus"))
                .body(SuccessResponse.create(HttpStatus.CREATED.value(),
                        CREATE_MENU_SUCCESS.getMessage(),
                        menuService.createMenu(storeId, menuRequest)));
    }

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/{storeId}/menus/{menuId}/option-groups")
    public ResponseEntity<SuccessResponse<List<CreateMenuOptionGroupResponse>>> createMenuOptionGroup(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @Valid @RequestBody List<CreateMenuOptionGroupRequest> createMenuOptionGroupRequests){
        return ResponseEntity.created(URI.create("/" + storeId + "/menus" + + menuId + "/optionGroups"))
                .body(SuccessResponse.create(HttpStatus.CREATED.value(),
                        CREATE_MENU_OPTION_GROUP_SUCCESS.getMessage(),
                        menuService.createMenuOptionGroup(storeId, menuId, createMenuOptionGroupRequests)));
    }

    @PreAuthorize("hasRole('OWNER')")
    @PatchMapping("/{storeId}/menus/{menuId}")
    public ResponseEntity<SuccessResponse<MenuResponse>> updateMenu(
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
    public ResponseEntity<SuccessResponse<List<MenuResponse>>> findItems(@PathVariable Long storeId, Pageable pageable){
        return ResponseEntity.ok(SuccessResponse.read(HttpStatus.OK.value(),
                GET_MENUS_SUCCESS.getMessage(),
                menuService.findMenus(storeId, pageable)));
    }
}
