package com.sns.pojang.domain.member.service;

import com.sns.pojang.domain.member.dto.request.CreateMemberRequest;
import com.sns.pojang.domain.member.dto.request.LoginMemberRequest;
import com.sns.pojang.domain.member.dto.response.CreateMemberResponse;
import com.sns.pojang.domain.member.dto.response.LoginMemberResponse;
import com.sns.pojang.domain.member.entity.Member;
import com.sns.pojang.domain.member.exception.EmailDuplicateException;
import com.sns.pojang.domain.member.exception.EmailNotExistException;
import com.sns.pojang.domain.member.exception.PasswordNotMatchException;
import com.sns.pojang.domain.member.repository.MemberRepository;
import com.sns.pojang.global.config.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Autowired
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    public CreateMemberResponse create(CreateMemberRequest createMemberRequest) throws EmailDuplicateException{
        if (memberRepository.findByEmail(createMemberRequest.getEmail()).isPresent()){
            throw new EmailDuplicateException();
        }
        Member newMember = createMemberRequest.toEntity(passwordEncoder);

        return CreateMemberResponse.from(memberRepository.save(newMember));
    }

    public LoginMemberResponse login(LoginMemberRequest loginMemberRequest) {
        // Email 존재 여부 Check
        Member findMember = memberRepository.findByEmail(loginMemberRequest.getEmail())
                .orElseThrow(EmailNotExistException::new);

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
}
