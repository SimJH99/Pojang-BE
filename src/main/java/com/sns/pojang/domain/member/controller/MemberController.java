package com.sns.pojang.domain.member.controller;

import com.sns.pojang.domain.member.dto.request.CreateMemberRequest;
import com.sns.pojang.domain.member.dto.request.LoginMemberRequest;
import com.sns.pojang.domain.member.dto.response.CreateMemberResponse;
import com.sns.pojang.domain.member.dto.response.LoginMemberResponse;
import com.sns.pojang.domain.member.dto.response.MyInfoMemberResponse;
import com.sns.pojang.domain.member.entity.Role;
import com.sns.pojang.domain.member.service.MemberService;
import com.sns.pojang.global.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.net.URI;

import static com.sns.pojang.global.response.SuccessMessage.*;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

//    일반 유저 회원가입
    @PostMapping("/sign-up/user")
    public ResponseEntity<SuccessResponse<CreateMemberResponse>> createUser(
            @Valid @RequestBody CreateMemberRequest createMemberRequest){
        return ResponseEntity.created(URI.create("/sign-up/user"))
                .body(SuccessResponse.create(HttpStatus.CREATED.value(),
                        CREATE_MEMBER_SUCCESS.getMessage(),
                        memberService.createUser(createMemberRequest)));
    }

//    사장 계정 회원가입
    @PostMapping("/sign-up/owner")
    public ResponseEntity<SuccessResponse<CreateMemberResponse>> createOwner(
            @Valid @RequestBody CreateMemberRequest createMemberRequest){
        return ResponseEntity.created(URI.create("/sign-up/owner"))
                .body(SuccessResponse.create(HttpStatus.CREATED.value(),
                        CREATE_MEMBER_SUCCESS.getMessage(),
                        memberService.createOwner(createMemberRequest)));
    }

//    로그인
    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<LoginMemberResponse>> login(
            @Valid @RequestBody LoginMemberRequest loginMemberRequest){
        return ResponseEntity.ok(SuccessResponse.create(HttpStatus.OK.value(),
                LOGIN_MEMBER_SUCCESS.getMessage(), memberService.login(loginMemberRequest)));
    }

//    내 정보 조회
    @GetMapping("/member/my-info")
    public ResponseEntity<SuccessResponse<MyInfoMemberResponse>> myInfo() {
        return ResponseEntity.ok(SuccessResponse.create(HttpStatus.OK.value(),
                MY_INFO_MEMBER_SUCCESS.getMessage(), memberService.myInfo()));
    }

//    회원 탈퇴
    @DeleteMapping("/withdraw")
    public ResponseEntity<SuccessResponse> withdraw() {
        return ResponseEntity.ok(SuccessResponse.create(HttpStatus.OK.value(),
                DELETE_MEMBER_SUCCESS.getMessage(), memberService.withdraw()));
        // 회원탈퇴 시 Authentication 객체 제거해야하나? - 프론트에서 필요

        // ++ 회원탈퇴 후 기존 계정으로 회원가입 시 처리방안 필요
        // 1. 스케쥴러 사용 - 30일 지난 탈퇴 계정 삭제 여부 결정
        // 2. 탈퇴된 이메일로 회원가입 시 예외처리 - '30일동안 회원가입 불가'
    }

}

