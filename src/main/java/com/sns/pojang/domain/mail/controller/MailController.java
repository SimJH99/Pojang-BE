package com.sns.pojang.domain.mail.controller;

import com.sns.pojang.domain.mail.dto.CertificateEmailRequest;
import com.sns.pojang.domain.mail.dto.CertificateEmailResponse;
import com.sns.pojang.domain.mail.service.MailService;
import com.sns.pojang.global.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

import static com.sns.pojang.global.response.SuccessMessage.SEND_CERTIFICATION_SUCCESS;
import static com.sns.pojang.global.response.SuccessMessage.VERIFY_CERTIFICATION_SUCCESS;

@RestController
@RequestMapping("/api/mail")
public class MailController {
    private final MailService mailService;

    @Autowired
    public MailController(MailService mailService){
        this.mailService = mailService;
    }

    @PostMapping("/send-certification")
    public ResponseEntity<SuccessResponse<CertificateEmailResponse>> sendEmailForCertification (
            @Valid @RequestBody CertificateEmailRequest certificateEmailRequest) throws MessagingException, NoSuchAlgorithmException {
        return ResponseEntity.ok(SuccessResponse.create(HttpStatus.OK.value(),
                SEND_CERTIFICATION_SUCCESS.getMessage(), mailService.sendEmailForCertification(certificateEmailRequest.getEmail())));
    }

    @GetMapping("/verify")
    public ResponseEntity<SuccessResponse<Void>> createUser(@RequestParam(name = "email") String email,
                                                            @RequestParam(name = "certificationNumber") String certificationNumber) {
        mailService.verifyEmail(email, certificationNumber);
        return ResponseEntity.ok(SuccessResponse.create(HttpStatus.OK.value(),
                VERIFY_CERTIFICATION_SUCCESS.getMessage()));
    }




}
