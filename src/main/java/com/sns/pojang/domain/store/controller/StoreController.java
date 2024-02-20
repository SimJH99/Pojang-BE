package com.sns.pojang.domain.store.controller;

import com.sns.pojang.domain.favorite.dto.response.CountFavoriteResponse;
import com.sns.pojang.domain.review.dto.response.RatingResponse;
import com.sns.pojang.domain.review.dto.response.ReviewResponse;
import com.sns.pojang.domain.store.dto.request.CreateStoreRequest;
import com.sns.pojang.domain.store.dto.request.RegisterBusinessNumberRequest;
import com.sns.pojang.domain.store.dto.request.UpdateStoreRequest;
import com.sns.pojang.domain.store.dto.response.*;
import com.sns.pojang.domain.store.dto.request.SearchStoreRequest;
import com.sns.pojang.domain.store.entity.BusinessNumber;
import com.sns.pojang.domain.store.service.StoreService;
import com.sns.pojang.global.response.SuccessResponse;
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
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    // 매장 생성
    @PreAuthorize("hasRole('ROLE_OWNER')")
    @PostMapping
    public ResponseEntity<SuccessResponse<CreateStoreResponse>> createStore(
            @Valid CreateStoreRequest createStoreRequest) {
        return ResponseEntity.created(URI.create(""))
                .body(SuccessResponse.create(HttpStatus.CREATED.value(),
                        CREATE_STORE_SUCCESS.getMessage(),
                        storeService.createStore(createStoreRequest)));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/register/business-number")
    public ResponseEntity<SuccessResponse<BusinessNumber>> registerBusinessNumber(
            @Valid @RequestBody RegisterBusinessNumberRequest registerBusinessNumberRequest) {
        return ResponseEntity.created(URI.create("/register/business-number"))
                .body(SuccessResponse.create(HttpStatus.CREATED.value(), REGISTER_BUSINESS_NUMBER_SUCCESS.getMessage(),
                        storeService.registerBusinessNumber(registerBusinessNumberRequest)));
    }

    // 매장 정보 수정
    @PreAuthorize("hasRole('ROLE_OWNER')")
    @PatchMapping("/{id}")
    public ResponseEntity<SuccessResponse<UpdateStoreResponse>> updateStore(
            @PathVariable Long id , @Valid UpdateStoreRequest updateStoreRequest){
        return ResponseEntity.ok(SuccessResponse.update(HttpStatus.OK.value(),
                UPDATE_STORE_SUCCESS.getMessage(), storeService.updateStore(id, updateStoreRequest)));
    }

    // 매장 삭제
    @PreAuthorize("hasRole('ROLE_OWNER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<Void>> deleteStore(@PathVariable Long id){
        storeService.deleteStore(id);
        return ResponseEntity.ok(SuccessResponse.delete(HttpStatus.OK.value(),
                DELETE_STORE_SUCCESS.getMessage()));
    }

    //내 매장 정보 조회
    @PreAuthorize("hasRole('ROLE_OWNER')")
    @GetMapping("/{memberId}/my-store")
    public ResponseEntity<SuccessResponse<List<SearchMyStoreResponse>>> getMyStore(@PathVariable Long memberId){
        return ResponseEntity.ok(SuccessResponse.create(HttpStatus.OK.value(), SEARCH_MY_STORE_SUCCESS.getMessage(),
                storeService.getMyStore(memberId)));
    }

    // 매장 상세 조회
    @GetMapping("/{storeId}/details")
    public ResponseEntity<SuccessResponse<SearchStoreInfoResponse>> getStoreDetail(@PathVariable Long storeId){
        return ResponseEntity.ok(SuccessResponse.create(HttpStatus.OK.value(), SEARCH_MY_STORE_SUCCESS.getMessage(),
                storeService.getStoreDetail(storeId)));
    }
  
    // 매장 목록 조회
    @GetMapping
    public ResponseEntity<SuccessResponse<List<SearchStoreResponse>>> findStores(SearchStoreRequest searchStoreRequest, Pageable pageable) {
        return ResponseEntity.ok(SuccessResponse.create(HttpStatus.OK.value(),
                SEARCH_STORE_SUCCESS.getMessage(), storeService.findStores(searchStoreRequest, pageable)));
    }

    // 매장 이미지 조회
    @GetMapping("/{id}/image")
    public ResponseEntity<Resource> findImage(@PathVariable Long id){
        Resource resource = storeService.findImage(id);
        HttpHeaders headers = new HttpHeaders(); // 파일의 타입을 스프링에 알려주기 위함
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    // 매장 리뷰 조회
    @GetMapping("/{storeId}/reviews")
    public ResponseEntity<SuccessResponse<List<ReviewResponse>>> findReviews(
            @PathVariable Long storeId) {
        return ResponseEntity.ok(SuccessResponse.create(HttpStatus.OK.value(),
                FIND_REVIEW_SUCCESS.getMessage(),
                storeService.findReviews(storeId)));
    }

    // 별점 조회
    @GetMapping("/{storeId}/rating")
    public ResponseEntity<SuccessResponse<RatingResponse>> findRating(
            @PathVariable Long storeId) {
        return ResponseEntity.ok(SuccessResponse.create(HttpStatus.OK.value(),
                FIND_RATING_SUCCESS.getMessage(),
                storeService.findRating(storeId)));
    }
}
