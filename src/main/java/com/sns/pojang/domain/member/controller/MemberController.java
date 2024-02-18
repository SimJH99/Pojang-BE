package com.sns.pojang.domain.member.controller;

import com.sns.pojang.domain.member.dto.request.*;
import com.sns.pojang.domain.member.dto.response.*;
import com.sns.pojang.domain.member.service.MemberService;
import com.sns.pojang.global.response.SuccessResponse;
import com.sns.pojang.global.utils.CertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.net.URI;
import java.util.List;

import static com.sns.pojang.global.response.SuccessMessage.*;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;
    private final CertificationService certificationService;

    @Autowired
    public MemberController(MemberService memberService, CertificationService certificationService) {
        this.memberService = memberService;
        this.certificationService = certificationService;
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
    @GetMapping("/my-info")
    public ResponseEntity<SuccessResponse<FindMyInfoResponse>> findMyInfo() {
        return ResponseEntity.ok(SuccessResponse.create(HttpStatus.OK.value(),
                FIND_MY_INFO_SUCCESS.getMessage(), memberService.findMyInfo()));
    }

//    마이페이지 수정
    @PatchMapping("/my-info")
    public ResponseEntity<SuccessResponse<FindMyInfoResponse>> updateMyInfo(
            @Valid @RequestBody UpdateMyInfoRequest updateMyInfoRequest) {
        return ResponseEntity.ok(SuccessResponse.create(HttpStatus.OK.value(),
                UPDATE_MY_INFO_SUCCESS.getMessage(), memberService.updateMyInfo(updateMyInfoRequest)));
    }

//    내 주소 조회
    @GetMapping("/address")
    public ResponseEntity<SuccessResponse<FindAddressResponse>> findMyAddress() {
        return ResponseEntity.ok(SuccessResponse.create(HttpStatus.OK.value(),
                FIND_ADDRESS_SUCCESS.getMessage(), memberService.findMyAddress()));
    }

//    내 주소 수정
    @PatchMapping("/address")
    public ResponseEntity<SuccessResponse<FindAddressResponse>> updateMyAddress(
            @Valid @RequestBody UpdateAddressRequest updateAddressRequest) {
        return ResponseEntity.ok(SuccessResponse.create(HttpStatus.OK.value(),
                UPDATE_ADDRESS_SUCCESS.getMessage(), memberService.updateMyAddress(updateAddressRequest)));
    }


//    회원 탈퇴
    @DeleteMapping("/withdraw")
    public ResponseEntity<SuccessResponse<Void>> withdraw() {
        memberService.withdraw();
        return ResponseEntity.ok(SuccessResponse.create(HttpStatus.OK.value(),
                DELETE_MEMBER_SUCCESS.getMessage()));
        // 회원탈퇴 시 Authentication 객체 제거해야하나? - 프론트에서 필요

        // ++ 회원탈퇴 후 기존 계정으로 회원가입 시 처리방안 필요
        // 1. 스케쥴러 사용 - 30일 지난 탈퇴 계정 삭제 여부 결정
        // 2. 탈퇴된 이메일로 회원가입 시 예외처리 - '30일동안 회원가입 불가'
    }

    // 인증 번호 발송
    @PostMapping("/send-sms")
    public ResponseEntity<SuccessResponse<SmsCertificationResponse>> sendSms(
            @RequestBody SendCertificationRequest sendCertificationRequest) throws Exception {
        return ResponseEntity.ok(SuccessResponse.create(HttpStatus.OK.value(),
                SEND_CERTIFICATION_SUCCESS.getMessage(), memberService.sendSms(sendCertificationRequest)));
    }

    //인증 번호 확인
    @PostMapping("/confirm-sms")
    public ResponseEntity<SuccessResponse<Void>> SmsVerification(
            @RequestBody VerifyCertificationRequest verifyCertificationRequest) throws Exception{
        certificationService.verifyKey(verifyCertificationRequest.getPhoneNumber(),
                verifyCertificationRequest.getCertificationNumber());
        return ResponseEntity.ok(SuccessResponse.create(HttpStatus.OK.value(),
                VERIFY_CERTIFICATION_SUCCESS.getMessage()));
    }

    // 내 찜 목록 조회
    @GetMapping("/favorite")
    public ResponseEntity<SuccessResponse<List<FindFavoritesResponse>>> findFavorites() {
        return ResponseEntity.ok(SuccessResponse.create(HttpStatus.OK.value(),
                FIND_FAVORITE_SUCCESS.getMessage(), memberService.findFavorites()));
    }
}

