package com.sns.pojang.domain.store.entity;

import com.sns.pojang.global.config.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "stores")
public class Store extends BaseTimeEntity {
    //PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //가게이름
    @Column(nullable = false)
    private String name;

    //가게사진
    private String imageUrl;

    //음식카테고리
    @Column(nullable = false)
    private String category;

    //주소
    @Embedded
    @Column(nullable = false)
    private Address address;

    //가게전화번호
    @Column(nullable = false)
    private String storeNumber;

    //가게소개글
    private String introduction;

    //운영시간
    @Column(nullable = false)
    private String operationTime;

    //사업자 번호
    @Column(nullable = false)
    private String businessNumber;

    //오픈여부
    @Column(nullable = false)
    private Status status = Status.CLOSED;

    //삭제여부
    @Column(nullable = false)
    private String deleteYn = "N";

    @Builder
    public Store(String name, String imageUrl, String category, Address address, String storeNumber, String introduction, String operationTime, String businessNumber) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.category = category;
        this.address = address;
        this.storeNumber = storeNumber;
        this.introduction = introduction;
        this.operationTime = operationTime;
        this.businessNumber = businessNumber;
    }


    public void updateStore(String name, String category, String sido, String sigungu, String query, String addressDetail, String storeNumber, String introduction, String operationTime, String imageUrl){
        this.name = name;
        this.imageUrl = imageUrl;
        this.category = category;
        this.address = Address.builder()
                .sido(sido)
                .sigungu(sigungu)
                .query(query)
                .addressDetail(addressDetail)
                .build();
        this.storeNumber = storeNumber;
        this.introduction = introduction;
        this.operationTime = operationTime;
        this.imageUrl = imageUrl;
    }

    public void isDelete() {
        this.deleteYn = "Y";
    }
}