package com.sns.pojang.domain.menu.controller;

import com.sns.pojang.domain.menu.dto.request.MenuRequest;
import com.sns.pojang.domain.menu.dto.response.MenuResponse;
import com.sns.pojang.domain.menu.service.MenuService;
import com.sns.pojang.global.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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
            @PathVariable Long storeId, MenuRequest menuRequest){
        return ResponseEntity.created(URI.create("/" + storeId + "/menus"))
                .body(SuccessResponse.create(HttpStatus.CREATED.value(),
                        CREATE_MENU_SUCCESS.getMessage(),
                        menuService.createMenu(storeId, menuRequest)));
    }

    @PreAuthorize("hasRole('OWNER')")
    @PatchMapping("/{storeId}/menus/{menuId}")
    public ResponseEntity<SuccessResponse<MenuResponse>> updateMenu(
            @PathVariable Long storeId, @PathVariable Long menuId, MenuRequest menuRequest){
        return ResponseEntity.ok(SuccessResponse.create(HttpStatus.OK.value(),
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
}
