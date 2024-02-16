package com.sns.pojang.domain.favorite.controller;

import com.sns.pojang.domain.favorite.dto.response.CreateFavoriteResponse;
import com.sns.pojang.domain.favorite.service.FavoriteService;
import com.sns.pojang.global.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.sns.pojang.global.response.SuccessMessage.CREATE_FAVORITE_SUCCESS;
import static com.sns.pojang.global.response.SuccessMessage.DELETE_FAVORITE_SUCCESS;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Autowired
    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    // 찜 등록
    @PostMapping("/{storeId}")
    public ResponseEntity<SuccessResponse<CreateFavoriteResponse>> createFavorite(
            @PathVariable Long storeId) {
        return ResponseEntity.created(URI.create("/" + storeId))
                .body(SuccessResponse.create(HttpStatus.CREATED.value(),
                        CREATE_FAVORITE_SUCCESS.getMessage(),
                        favoriteService.createFavorite(storeId)));
    }

    // 찜 취소
    @DeleteMapping("/{storeId}")
    public ResponseEntity<SuccessResponse<Void>> deleteFavorite(
            @PathVariable Long storeId) {
        favoriteService.deleteFavorite(storeId);
        return ResponseEntity.ok(SuccessResponse.create(HttpStatus.OK.value(),
                        DELETE_FAVORITE_SUCCESS.getMessage()));
    }
}
