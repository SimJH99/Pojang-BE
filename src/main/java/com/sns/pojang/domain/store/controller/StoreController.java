package com.sns.pojang.domain.store.controller;

import com.sns.pojang.domain.store.dto.request.CreateStoreRequest;
import com.sns.pojang.domain.store.dto.request.RegisterBusinessNumberRequest;
import com.sns.pojang.domain.store.dto.request.UpdateStoreRequest;
import com.sns.pojang.domain.store.dto.response.CreateStoreResponse;
import com.sns.pojang.domain.store.dto.response.UpdateStoreResponse;
import com.sns.pojang.domain.store.entity.BusinessNumber;
import com.sns.pojang.domain.store.service.StoreService;
import com.sns.pojang.global.response.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static com.sns.pojang.global.response.SuccessMessage.*;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    //    매장 생성
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
        return ResponseEntity.ok(SuccessResponse.create(HttpStatus.OK.value(),
                UPDATE_STORE_SUCCESS.getMessage(), storeService.updateStore(id, updateStoreRequest)));
    }

    // 매장 삭제
    @PreAuthorize("hasRole('ROLE_OWNER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<Void>> deleteStore(@PathVariable Long id){
        storeService.deleteStore(id);
        return ResponseEntity.ok(SuccessResponse.create(HttpStatus.OK.value(),
                DELETE_STORE_SUCCESS.getMessage()));
    }
}
