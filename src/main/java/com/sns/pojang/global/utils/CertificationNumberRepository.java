package com.sns.pojang.global.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class CertificationNumberRepository {
    private final StringRedisTemplate redisTemplate;
    public static final int VERIFICATION_LIMIT_IN_SECONDS = 180; // 3분 유효시간

    public void saveCertificationNumber(String key, String certificationNumber) {
        redisTemplate.opsForValue()
                .set(key, certificationNumber,
                        Duration.ofSeconds(VERIFICATION_LIMIT_IN_SECONDS));
    }

    public String getCertificationNumber(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void removeCertificationNumber(String key) {
        redisTemplate.delete(key);
    }

    public boolean hasKey(String key) {
        Boolean keyExists = redisTemplate.hasKey(key);
        return keyExists != null && keyExists;
    }
}
