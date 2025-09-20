package com.exam.rbac_jwt_mfa_library.service;

import com.exam.rbac_jwt_mfa_library.domain.User;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;

@Service
public class OtpService {
    private final StringRedisTemplate redisTemplate;
    private final MailService mailService;
    private OtpService(StringRedisTemplate redisTemplate, MailService mailService) {
        this.redisTemplate = redisTemplate;
        this.mailService = mailService;
    };

    public String generateAndSend(User user) {
        String otp = String.valueOf(100000 + new Random().nextInt(900000));
        String key = "otp:" + user.getUsername();
        redisTemplate.opsForValue().set(key, otp, Duration.ofMinutes(5));
        mailService.send(user.getEmail(), "Your OTP ", "Your OTP is : " + otp + " (valid 5 minutes)");
        return otp;
    }

    public boolean verify(User user, String provided) {
        String key = "otp:" + user.getUsername();
        String val = redisTemplate.opsForValue().get(key);
        return provided != null && provided.equals(val);
    }

}
