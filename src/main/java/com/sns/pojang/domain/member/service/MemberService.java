package com.sns.pojang.domain.member.service;

import com.sns.pojang.domain.member.dto.request.CreateMemberRequest;
import com.sns.pojang.domain.member.dto.request.LoginMemberRequest;
import com.sns.pojang.domain.member.dto.request.SendCertificationRequest;
import com.sns.pojang.domain.member.dto.response.CreateMemberResponse;
import com.sns.pojang.domain.member.dto.response.LoginMemberResponse;
import com.sns.pojang.domain.member.dto.response.MyInfoMemberResponse;
import com.sns.pojang.domain.member.dto.response.SmsCertificationResponse;
import com.sns.pojang.domain.member.entity.Member;
import com.sns.pojang.domain.member.entity.Role;
import com.sns.pojang.domain.member.exception.EmailDuplicateException;
import com.sns.pojang.global.error.exception.KeyNotExistException;
import com.sns.pojang.domain.member.exception.MemberNotFoundException;
import com.sns.pojang.domain.member.exception.PasswordNotMatchException;
import com.sns.pojang.domain.member.repository.MemberRepository;
import com.sns.pojang.domain.member.utils.SmsCertificationUtil;
import com.sns.pojang.global.config.security.jwt.JwtProvider;
import com.sns.pojang.global.utils.CertificationGenerator;
import com.sns.pojang.global.utils.CertificationNumberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;

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

    public CreateMemberResponse createUser(CreateMemberRequest createMemberRequest) throws EmailDuplicateException{
        if (memberRepository.findByEmail(createMemberRequest.getEmail()).isPresent()){
            throw new EmailDuplicateException();
        }
        Member newMember = createMemberRequest.toEntity(passwordEncoder, Role.ROLE_USER);

        return CreateMemberResponse.from(memberRepository.save(newMember));
    }

    public CreateMemberResponse createOwner(CreateMemberRequest createMemberRequest) throws EmailDuplicateException{
        if (memberRepository.findByEmail(createMemberRequest.getEmail()).isPresent()){
            throw new EmailDuplicateException();
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

    public void withdraw() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        member.withdraw();
    }

    public SmsCertificationResponse sendSms(SendCertificationRequest sendCertificationRequest) throws NoSuchAlgorithmException {
        String to = sendCertificationRequest.getPhoneNumber();
        String certificationNumber = certificationGenerator.createCertificationNumber();
        smsCertificationUtil.sendSms(to, certificationNumber);
        certificationNumberRepository.saveCertificationNumber(to,certificationNumber);

        return new SmsCertificationResponse(to, certificationNumber);
    }
}
