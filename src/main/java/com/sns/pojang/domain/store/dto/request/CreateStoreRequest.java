package com.sns.pojang.domain.store.dto.request;

import com.sns.pojang.domain.store.entity.Address;
import com.sns.pojang.domain.store.entity.Store;
import lombok.Data;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateStoreRequest {

    @NotEmpty(message = "가게상호명을 입력하시오.")
    private String name;
    @NotEmpty(message = "카테고리를 선택하시오.")
    private String category;
    @NotEmpty(message = "매장전화번호는 비어있으면 안됩니다.")
    private String storeNumber;
    @NotEmpty(message = "운영시간은 비어있으면 안됩니다.")
    private String operationTime;
    @NotEmpty(message = "사업자번호는 비어있으면 안됩니다.")
    private String businessNumber;
    @Nullable
    private MultipartFile storeImage;
    private String sido;
    private String sigungu;
    private String query;
    private String addressDetail;
    private String introduction;

    public Store toEntity(String path) {
        Address fullAddress = Address.builder()
                .sido(sido)
                .sigungu(sigungu)
                .query(query)
                .addressDetail(addressDetail)
                .build();
        return Store.builder()
                .name(name)
                .imageUrl(path)
                .category(category)
                .address(fullAddress)
                .storeNumber(storeNumber)
                .introduction(introduction)
                .operationTime(operationTime)
                .businessNumber(businessNumber)
                .build();
    }
}
