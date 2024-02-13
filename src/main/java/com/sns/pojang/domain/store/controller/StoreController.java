package com.sns.pojang.domain.store.controller;

import com.sns.pojang.domain.store.dto.request.CreateStoreRequest;
import com.sns.pojang.domain.store.dto.response.CreateStoreResponse;
import com.sns.pojang.domain.store.service.StoreService;
import com.sns.pojang.global.response.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

import static com.sns.pojang.global.response.SuccessMessage.CREATE_STORE_SUCCESS;

@RestController
@PreAuthorize("hasRole('ROLE_OWNER')")
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    //    매장 생성
    @PostMapping("/create")
    public ResponseEntity<SuccessResponse<CreateStoreResponse>> createStore(
            @Valid CreateStoreRequest createStoreRequest) {
        return ResponseEntity.created(URI.create("/create"))
                .body(SuccessResponse.create(HttpStatus.CREATED.value(),
                        CREATE_STORE_SUCCESS.getMessage(),
                        storeService.createStore(createStoreRequest)));
    }
}
