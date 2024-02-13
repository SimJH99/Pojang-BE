package com.sns.pojang.global.config;

import com.sns.pojang.domain.member.entity.Member;
import com.sns.pojang.domain.member.entity.Role;
import com.sns.pojang.domain.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitialDataLoader implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public InitialDataLoader(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // CommandLineRunner를 통해 스프링 빈으로 등록되는 시점에 run 메서드 실행
    @Override
    public void run(String... args) throws Exception {
        // 서버 실행 시 관리자 권한 계정 자동 생성
        if (memberRepository.findByEmail("admin@test.com").isEmpty()){
            Member adminMember = Member.builder()
                    .nickname("admin")
                    .email("admin@test.com")
                    .password(passwordEncoder.encode("1234"))
                    .phoneNumber("010-1234-5678")
                    .role(Role.ROLE_ADMIN)
                    .build();
            memberRepository.save(adminMember);
        }
    }
}
