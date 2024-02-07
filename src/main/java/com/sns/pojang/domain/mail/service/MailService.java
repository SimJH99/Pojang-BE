package com.sns.pojang.domain.mail.service;

import com.sns.pojang.domain.mail.dto.CertificateEmailResponse;
import com.sns.pojang.domain.mail.exception.InvalidCertificatedNumberException;
import com.sns.pojang.domain.mail.repository.CertificationNumberRepository;
import com.sns.pojang.domain.mail.utils.CertificationGenerator;
import com.sns.pojang.domain.member.exception.EmailNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    private final CertificationNumberRepository certificationNumberRepository;
    private final CertificationGenerator certificationGenerator;
    private static final String DOMAIN_NAME = "http://localhost:8080";
    private static final String MAIL_TITLE_CERTIFICATION = "포장의 민족 인증 메일";

    public CertificateEmailResponse sendEmailForCertification(String email) throws NoSuchAlgorithmException, MessagingException {
        String certificationNumber = certificationGenerator.createCertificationNumber();
        String content = String.format("%s/api/mail/verify?certificationNumber=%s&email=%s   링크를 3분 이내에 클릭해주세요.",
                DOMAIN_NAME, certificationNumber, email);
        certificationNumberRepository.saveCertificationNumber(email, certificationNumber);
        sendMail(email, content);
        return new CertificateEmailResponse(email, certificationNumber);
    }

    private void sendMail(String email, String content) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setTo(email);
        helper.setSubject(MAIL_TITLE_CERTIFICATION);
        helper.setText(content);
        mailSender.send(mimeMessage);
    }

    public void verifyEmail(String email, String certificationNumber) {
        if (!isVerify(email, certificationNumber)) {
            throw new InvalidCertificatedNumberException();
        }
        certificationNumberRepository.removeCertificationNumber(email);
    }

    private boolean isVerify(String email, String certificationNumber) {
        boolean validatedEmail = isEmailExists(email);
        if (!isEmailExists(email)) {
            throw new EmailNotExistException();
        }
        return (validatedEmail &&
                certificationNumberRepository.getCertificationNumber(email).equals(certificationNumber));
    }

    private boolean isEmailExists(String email) {
        return certificationNumberRepository.hasKey(email);
    }
}
