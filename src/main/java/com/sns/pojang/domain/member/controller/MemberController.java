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

    @PostMapping("/sign-up/user")
    public ResponseEntity<SuccessResponse<CreateMemberResponse>> createUser(
            @Valid @RequestBody CreateMemberRequest createMemberRequest){
        return ResponseEntity.created(URI.create("/sign-up/user"))
                .body(SuccessResponse.create(HttpStatus.CREATED.value(),
                        CREATE_MEMBER_SUCCESS.getMessage(),
                        memberService.createUser(createMemberRequest)));
    }

    @PostMapping("/sign-up/owner")
    public ResponseEntity<SuccessResponse<CreateMemberResponse>> createOwner(
            @Valid @RequestBody CreateMemberRequest createMemberRequest){
        return ResponseEntity.created(URI.create("/sign-up/owner"))
                .body(SuccessResponse.create(HttpStatus.CREATED.value(),
                        CREATE_MEMBER_SUCCESS.getMessage(),
                        memberService.createOwner(createMemberRequest)));
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<LoginMemberResponse>> login(
            @Valid @RequestBody LoginMemberRequest loginMemberRequest){
        return ResponseEntity.ok(SuccessResponse.create(HttpStatus.OK.value(),
                LOGIN_MEMBER_SUCCESS.getMessage(), memberService.login(loginMemberRequest)));
    }

    @GetMapping("/member/my-info")
    public ResponseEntity<SuccessResponse<MyInfoMemberResponse>> myInfo() {
        return ResponseEntity.ok(SuccessResponse.create(HttpStatus.OK.value(),
                MYINFO_MEMBER_SUCCESS.getMessage(), memberService.myInfo()));
    }

}
