package com.sns.pojang.global.utils;

import com.sns.pojang.global.error.exception.KeyNotExistException;
import com.sns.pojang.global.error.exception.InvalidCertificatedNumberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CertificationService {
    private final CertificationNumberRepository certificationNumberRepository;
    @Autowired
    public CertificationService(CertificationNumberRepository certificationNumberRepository) {
        this.certificationNumberRepository = certificationNumberRepository;
    }

    public void verifyKey(String key, String certificationNumber) {
        if (!isVerify(key, certificationNumber)) {
            throw new InvalidCertificatedNumberException();
        }
        certificationNumberRepository.removeCertificationNumber(key);
    }

    private boolean isVerify(String key, String certificationNumber) {
        boolean validatedKey = isKeyExists(key);
        if (!isKeyExists(key)) {
            throw new KeyNotExistException();
        }
        return (validatedKey &&
                certificationNumberRepository.getCertificationNumber(key).equals(certificationNumber));
    }

    private boolean isKeyExists(String key) {
        return certificationNumberRepository.hasKey(key);
    }
}
