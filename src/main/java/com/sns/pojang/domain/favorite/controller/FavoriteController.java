package com.sns.pojang.domain.favorite.controller;

import com.sns.pojang.domain.favorite.dto.response.CountFavoriteResponse;
import com.sns.pojang.domain.favorite.dto.response.CreateFavoriteResponse;
import com.sns.pojang.domain.favorite.service.FavoriteService;
import com.sns.pojang.global.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.sns.pojang.global.response.SuccessMessage.*;


@RestController
@RequestMapping("/api/stores")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Autowired
    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    // 찜 등록
    @PostMapping("/{storeId}/favorites")
    public ResponseEntity<SuccessResponse<CreateFavoriteResponse>> createFavorite(
            @PathVariable Long storeId) {
        return ResponseEntity.created(URI.create("/" + storeId + "/favorites"))
                .body(SuccessResponse.create(HttpStatus.CREATED.value(),
                        CREATE_FAVORITE_SUCCESS.getMessage(),
                        favoriteService.createFavorite(storeId)));
    }

    // 찜 취소
    @DeleteMapping("/{storeId}/favorites")
    public ResponseEntity<SuccessResponse<Void>> cancelFavorite(
            @PathVariable Long storeId) {
        favoriteService.cancelFavorite(storeId);
        return ResponseEntity.ok(SuccessResponse.create(HttpStatus.OK.value(),
                        DELETE_FAVORITE_SUCCESS.getMessage()));
    }

    // 찜 여부 조회
    @GetMapping("/{storeId}/favorite")
    public ResponseEntity<Boolean> checkFavorite(
            @PathVariable Long storeId) {
        Boolean checkFavorite = favoriteService.checkFavorite(storeId);
        return new ResponseEntity<>(checkFavorite,HttpStatus.OK);
    }

    // 찜 수 조회
    @GetMapping("/{storeId}/favorites")
    public ResponseEntity<SuccessResponse<CountFavoriteResponse>> countFavorite(
            @PathVariable Long storeId) {
        return ResponseEntity.ok(SuccessResponse.create(HttpStatus.OK.value(),
                        COUNT_FAVORITE_SUCCESS.getMessage(),
                        favoriteService.countFavorite(storeId)));
    }
}
