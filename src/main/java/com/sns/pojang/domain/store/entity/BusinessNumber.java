package com.sns.pojang.domain.store.entity;

import com.sns.pojang.global.config.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "BusinessNumbers")
public class BusinessNumber extends BaseTimeEntity {
    //PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String bussnissNumber;

    //삭제여부
    @Column(nullable = false)
    private String deleteYn = "N";
}
