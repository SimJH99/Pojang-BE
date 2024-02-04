package com.sns.pojang.domain.member.controller;

import com.sns.pojang.domain.member.dto.request.CreateMemberRequest;
import com.sns.pojang.domain.member.dto.request.LoginMemberRequest;
import com.sns.pojang.domain.member.dto.response.CreateMemberResponse;
import com.sns.pojang.domain.member.dto.response.LoginMemberResponse;
import com.sns.pojang.domain.member.service.MemberService;
import com.sns.pojang.global.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.net.URI;

import static com.sns.pojang.global.response.SuccessMessage.CREATE_MEMBER_SUCCESS;
import static com.sns.pojang.global.response.SuccessMessage.LOGIN_MEMBER_SUCCESS;

@RestController
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<SuccessResponse<CreateMemberResponse>> create(
            @Valid @RequestBody CreateMemberRequest createMemberRequest){
        return ResponseEntity.created(URI.create("/sign-up"))
                .body(SuccessResponse.create(HttpStatus.CREATED.value(),
                        CREATE_MEMBER_SUCCESS.getMessage(),
                        memberService.create(createMemberRequest)));
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<LoginMemberResponse>> login(
            @Valid @RequestBody LoginMemberRequest loginMemberRequest){
        return ResponseEntity.ok(SuccessResponse.create(HttpStatus.OK.value(),
                LOGIN_MEMBER_SUCCESS.getMessage(), memberService.login(loginMemberRequest)));
    }
}
