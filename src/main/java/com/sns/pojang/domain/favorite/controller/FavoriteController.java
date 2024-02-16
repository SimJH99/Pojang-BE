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

    // 찜 수 조회
    // 매장 조회 시 찜 개수도 같이 조회하도록 하는 게 나을까
    // store에 mappedBy로 넣어서 바로 세는 게 나을까, count를 따로 관리해서 +(create), -(delete) 해주는 게 나을까
    @GetMapping("/{storeId}")
    public ResponseEntity<SuccessResponse<CountFavoriteResponse>> countFavorite(
            @PathVariable Long storeId) {
        return ResponseEntity.ok(SuccessResponse.create(HttpStatus.OK.value(),
                        COUNT_FAVORITE_SUCCESS.getMessage(),
                        favoriteService.countFavorite(storeId)));
    }
}
