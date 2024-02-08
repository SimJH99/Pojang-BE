package com.sns.pojang.domain.member.entity;

import com.sns.pojang.global.config.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Embedded
    private Address address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "delete_YN")
    private String delYn;

    @Builder
    public Member(String email, String password, String nickname, Address address, Role role, String delYn){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.address = address;
        this.role = role;
        this.delYn = delYn;
    }

//    회원 탈퇴
    public void withdraw() {
        this.delYn = "Y";
    }
}
