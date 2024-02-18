package com.sns.pojang.domain.store.dto.request;

import com.sns.pojang.domain.store.entity.Address;
import com.sns.pojang.domain.store.entity.BusinessNumber;
import com.sns.pojang.domain.store.entity.Store;
import lombok.Data;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Data
public class RegisterBusinessNumberRequest {

    @NotEmpty(message = "사업자번호는 비어있으면 안됩니다.")
    private String businessNumber;
}
