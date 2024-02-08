package com.sns.pojang.domain.member.service;

import com.sns.pojang.domain.member.dto.request.CreateMemberRequest;
import com.sns.pojang.domain.member.dto.request.LoginMemberRequest;
import com.sns.pojang.domain.member.dto.response.CreateMemberResponse;
import com.sns.pojang.domain.member.dto.response.LoginMemberResponse;
import com.sns.pojang.domain.member.dto.response.MyInfoMemberResponse;
import com.sns.pojang.domain.member.entity.Member;
import com.sns.pojang.domain.member.entity.Role;
import com.sns.pojang.domain.member.exception.EmailDuplicateException;
import com.sns.pojang.domain.member.exception.EmailNotExistException;
import com.sns.pojang.domain.member.exception.MemberNotFoundException;
import com.sns.pojang.domain.member.exception.PasswordNotMatchException;
import com.sns.pojang.domain.member.repository.MemberRepository;
import com.sns.pojang.global.config.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@Transactional
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
                .orElseThrow(EmailNotExistException::new);

        // 계정 삭제 여부 Check
        if(findMember.getDelYn().equals("Y")) {
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

    public Object withdraw() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        member.withdraw();
        return null;
    }
}
