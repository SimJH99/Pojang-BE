package com.sns.pojang.domain.store.dto.request;

import lombok.Data;

@Data
public class SearchStoreRequest {
    private String name;
    private String category;
    private String sido;
    private String sigungu;
    private String bname;
}
