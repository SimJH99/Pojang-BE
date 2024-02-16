package com.sns.pojang.domain.store.dto.response;

import com.sns.pojang.domain.store.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SearchStoreResponse {
    private String name;
    private String imageUrl;
    private String category;
    private Status status;
}
