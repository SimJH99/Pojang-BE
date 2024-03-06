package com.sns.pojang.domain.store.entity;

import com.sns.pojang.domain.member.entity.Member;
import com.sns.pojang.domain.favorite.entity.Favorite;
import com.sns.pojang.domain.member.entity.Member;
import com.sns.pojang.global.config.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @Enumerated(EnumType.STRING)
    private Status status = Status.CLOSED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    //삭제여부
    @Column(nullable = false)
    private String deleteYn = "N";

    @Builder
    public Store(String name, String imageUrl, String category, Address address, 
                 String storeNumber, String introduction, String operationTime, String businessNumber, Member member) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.category = category;
        this.address = address;
        this.storeNumber = storeNumber;
        this.introduction = introduction;
        this.operationTime = operationTime;
        this.businessNumber = businessNumber;
        this.member = member;
    }

    public void updateStore(String name, String sido, String sigungu, String bname, String roadAddress, String addressDetail, String category, String storeNumber, String introduction, String operationTime){
        Address fullAddress = Address.builder()
                .sido(sido)
                .sigungu(sigungu)
                .bname(bname)
                .roadAddress(roadAddress)
                .addressDetail(addressDetail)
                .build();
        this.name = name;
        this.category = category;
        this.storeNumber = storeNumber;
        this.introduction = introduction;
        this.operationTime = operationTime;
        this.address = fullAddress;
    }

    public void close() {
        this.status = Status.CLOSED;
    }

    public void open() {
        this.status = Status.OPEN;
    }

    public void isDelete() {
        this.deleteYn = "Y";
    }

    public void attachMember(Member member){
        this.member = member;
        member.getStores().add(this);
    }
}
