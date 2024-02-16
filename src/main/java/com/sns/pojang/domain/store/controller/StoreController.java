package com.sns.pojang.domain.store.controller;

import com.sns.pojang.domain.store.dto.request.CreateStoreRequest;
import com.sns.pojang.domain.store.dto.request.UpdateStoreRequest;
import com.sns.pojang.domain.store.dto.response.CreateStoreResponse;
import com.sns.pojang.domain.store.dto.request.SearchStoreRequest;
import com.sns.pojang.domain.store.dto.response.SearchStoreResponse;
import com.sns.pojang.domain.store.dto.response.UpdateStoreResponse;
import com.sns.pojang.domain.store.service.StoreService;
import com.sns.pojang.global.response.SuccessResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.sns.pojang.global.response.SuccessMessage.CREATE_STORE_SUCCESS;
import static com.sns.pojang.global.response.SuccessMessage.UPDATE_MEMBER_SUCCESS;
import static com.sns.pojang.global.response.SuccessMessage.SEARCH_STORE_SUCCESS;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    //    매장 생성
    @PreAuthorize("hasRole('ROLE_OWNER')")
    @PostMapping("/create")
    public ResponseEntity<SuccessResponse<CreateStoreResponse>> createStore(
            @Valid CreateStoreRequest createStoreRequest) {
        return ResponseEntity.created(URI.create("/create"))
                .body(SuccessResponse.create(HttpStatus.CREATED.value(),
                        CREATE_STORE_SUCCESS.getMessage(),
                        storeService.createStore(createStoreRequest)));
    }

    // 매장 정보 수정
    @PreAuthorize("hasRole('ROLE_OWNER')")
    @PatchMapping("/{id}/update")
    public ResponseEntity<SuccessResponse<UpdateStoreResponse>> updateStore(
            @PathVariable Long id , @Valid UpdateStoreRequest updateStoreRequest){
        return ResponseEntity.ok(SuccessResponse.create(HttpStatus.OK.value(),
                UPDATE_MEMBER_SUCCESS.getMessage(), storeService.updateStore(id, updateStoreRequest)));
    }
    // 카테고리 별 매장조회
    @GetMapping("/categories")
    public ResponseEntity<SuccessResponse<List<SearchStoreResponse>>> findStores(SearchStoreRequest searchStoreRequest, Pageable pageable) {
        return ResponseEntity.ok(SuccessResponse.create(HttpStatus.OK.value(),
                SEARCH_STORE_SUCCESS.getMessage(), storeService.findStores(searchStoreRequest, pageable)));
    }

}
