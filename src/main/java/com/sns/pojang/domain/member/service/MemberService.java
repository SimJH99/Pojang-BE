package com.sns.pojang.domain.member.service;

import com.sns.pojang.domain.member.dto.request.*;
import com.sns.pojang.domain.member.dto.response.*;
import com.sns.pojang.domain.member.entity.Member;
import com.sns.pojang.domain.member.entity.Role;
import com.sns.pojang.domain.member.exception.EmailDuplicateException;
import com.sns.pojang.domain.member.exception.NicknameDuplicateException;
import com.sns.pojang.global.error.exception.KeyNotExistException;
import com.sns.pojang.domain.member.exception.MemberNotFoundException;
import com.sns.pojang.domain.member.exception.PasswordNotMatchException;
import com.sns.pojang.domain.member.repository.MemberRepository;
import com.sns.pojang.domain.member.utils.SmsCertificationUtil;
import com.sns.pojang.global.config.security.jwt.JwtProvider;
import com.sns.pojang.global.utils.CertificationGenerator;
import com.sns.pojang.global.utils.CertificationNumberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CertificationNumberRepository certificationNumberRepository;
    private final CertificationGenerator certificationGenerator;
    private final SmsCertificationUtil smsCertificationUtil;

    public CreateMemberResponse createUser(CreateMemberRequest createMemberRequest) throws EmailDuplicateException, NicknameDuplicateException{
        if (memberRepository.findByEmail(createMemberRequest.getEmail()).isPresent()){
            throw new EmailDuplicateException();
        }
        if(memberRepository.findByNickname(createMemberRequest.getNickname()).isPresent()) {
            throw new NicknameDuplicateException();
        }
        Member newMember = createMemberRequest.toEntity(passwordEncoder, Role.ROLE_USER);

        return CreateMemberResponse.from(memberRepository.save(newMember));
    }

    public CreateMemberResponse createOwner(CreateMemberRequest createMemberRequest) throws EmailDuplicateException, NicknameDuplicateException {
        if (memberRepository.findByEmail(createMemberRequest.getEmail()).isPresent()){
            throw new EmailDuplicateException();
        }
        if(memberRepository.findByNickname(createMemberRequest.getNickname()).isPresent()) {
            throw new NicknameDuplicateException();
        }
        Member newMember = createMemberRequest.toEntity(passwordEncoder, Role.ROLE_OWNER);

        return CreateMemberResponse.from(memberRepository.save(newMember));
    }

    public LoginMemberResponse login(LoginMemberRequest loginMemberRequest) {
        // Email 존재 여부 Check
        Member findMember = memberRepository.findByEmail(loginMemberRequest.getEmail())
                .orElseThrow(KeyNotExistException::new);

        // 계정 삭제 여부 Check
        if(findMember.getDeleteYn().equals("Y")) {
            throw new MemberNotFoundException();
        }

        // Password 일치 여부 Check
        if (!passwordEncoder.matches(loginMemberRequest.getPassword(), findMember.getPassword())){
            throw new PasswordNotMatchException();
        }

        String token = jwtProvider.createToken(findMember.getEmail(),
                findMember.getRole().toString());

        return LoginMemberResponse.builder()
                .id(findMember.getId())
                .token(token)
                .build();
    }

    public MyInfoMemberResponse myInfo() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        return MyInfoMemberResponse.from(member);
    }

    public MyInfoMemberResponse myInfoUpdate(MyInfoMemberRequest myInfoMemberRequest) throws NicknameDuplicateException{
        if(memberRepository.findByNickname(myInfoMemberRequest.getNickname()).isPresent()) {
            throw new NicknameDuplicateException();
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        member.myInfoUpdate(myInfoMemberRequest.getNickname(), myInfoMemberRequest.getPassword(), myInfoMemberRequest.getPhoneNumber());
        return MyInfoMemberResponse.from(member);
    }

    public AddressMemberResponse myAddress() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        return AddressMemberResponse.from(member);
    }

    public AddressMemberResponse addressUpdate(AddressMemberRequest addressMemberRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        member.addressUpdate(addressMemberRequest.getSido(), addressMemberRequest.getSigungu(), addressMemberRequest.getQuery());
        return AddressMemberResponse.from(member);
    }


    public void withdraw() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        member.withdraw();
    }

    // 회원탈퇴 30일 이후 회원정보 삭제 - 재가입 방지
    @Scheduled(cron = "0 0/1 * * * *") // 매분마다 실행
    public void memberSchedule() {
        List<Member> members = memberRepository.findByDeleteYn("Y");
        for(Member m : members) {
            // 회원탈퇴 후 3분 뒤 삭제
            if(m.getUpdatedTime().plusMinutes(3).isBefore(LocalDateTime.now())) {
                memberRepository.delete(m);
            }
        }
    }

    public SmsCertificationResponse sendSms(SendCertificationRequest sendCertificationRequest) throws NoSuchAlgorithmException {
        String to = sendCertificationRequest.getPhoneNumber();
        String certificationNumber = certificationGenerator.createCertificationNumber();
        smsCertificationUtil.sendSms(to, certificationNumber);
        certificationNumberRepository.saveCertificationNumber(to,certificationNumber);

        return new SmsCertificationResponse(to, certificationNumber);
    }
}
