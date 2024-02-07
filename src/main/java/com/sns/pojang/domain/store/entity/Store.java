package com.sns.pojang.domain.store.entity;

import com.sns.pojang.global.config.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
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
    private String storeUrl;

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
}