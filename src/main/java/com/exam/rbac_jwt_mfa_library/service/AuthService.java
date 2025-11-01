package com.exam.rbac_jwt_mfa_library.service;

import com.exam.rbac_jwt_mfa_library.domain.LoginAttempt;
import com.exam.rbac_jwt_mfa_library.domain.Role;
import com.exam.rbac_jwt_mfa_library.domain.User;
import com.exam.rbac_jwt_mfa_library.dto.LoginRequest;
import com.exam.rbac_jwt_mfa_library.dto.OtpVerifyRequest;
import com.exam.rbac_jwt_mfa_library.dto.RegisterRequest;
import com.exam.rbac_jwt_mfa_library.repo.LoginAttemptRepository;
import com.exam.rbac_jwt_mfa_library.repo.UserRepository;
import com.exam.rbac_jwt_mfa_library.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final LoginAttemptRepository attemptRepository;
    private final JwtTokenProvider jwt;
    private final OtpService otpService;
    private final AuditService auditService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository, LoginAttemptRepository attemptRepository, JwtTokenProvider jwt, OtpService otpService, AuditService audit, AuditService auditService) {
        this.userRepository = userRepository;
        this.attemptRepository = attemptRepository;
        this.jwt = jwt;
        this.otpService = otpService;
        this.auditService = auditService;
    }

    public ResponseEntity<?> register(RegisterRequest request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            auditService.log(null, "REGISTER", "Username already exists", null, null);
            return ResponseEntity.badRequest().body("Username already exists");
        }

        if (userRepository.findByEmail(request.email()).isPresent()) {
            auditService.log(null, "REGISTER", "Email already exists", null, null);
            return ResponseEntity.badRequest().body("Email already exists");
        }

        User user = new User();
        user.setFullname(request.fullname());
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(encoder.encode(request.password()));
        user.setRoles(Set.of(Role.VIEWER));
        User saved = userRepository.save(user);
        auditService.log(saved.getId(), "REGISTER", "{}", null, null);
        return ResponseEntity.ok("User registered succesfully");

    }

    public ResponseEntity<?> beginLogin(LoginRequest request) {
/*        Optional<User> user = userRepository.findByUsername(request.usernameOrEmail());

        if (user.isEmpty() || !encoder.matches(request.password(), request.password())) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        return ResponseEntity.ok("");*/

        Optional<User> optionalUser = userRepository.findByUsername(request.usernameOrEmail());
        if (optionalUser.isEmpty()) optionalUser = userRepository.findByEmail(request.usernameOrEmail());
        if (optionalUser.isEmpty()) {
            attemptRepository.save(failed(request.usernameOrEmail()));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        User user = optionalUser.get();
        if (isBlocked(user)) {

        }
        if (!new BCryptPasswordEncoder().matches(request.password(), user.getPassword())) {
            attemptRepository.save(failed(request.usernameOrEmail()));
            blockIfExceeded(user, request.usernameOrEmail());
            throw new RuntimeException("Invalid credentials");
        }
        attemptRepository.save(success(request.usernameOrEmail()));
        String otp = otpService.generateAndSend(user);
        auditService.log(user.getId(), "LOGIN_OTP_SENT " + otp, "{}", null, null);
        return ResponseEntity.ok("Sukses");
    }

    public String verifyOtpAndIssueToken(OtpVerifyRequest request) {
        User user = userRepository.findByUsername(request.usernameOrEmail()).orElseGet(
                () -> userRepository.findByEmail(request.usernameOrEmail()).orElseThrow());
        if (!otpService.verify(user, request.otp())) throw new RuntimeException("Invalid OTP");
        String token = jwt.createToken(user.getUsername(), Map.of("roles", user.getRoles()));
        auditService.log(user.getId(), "LOGIN_SUCCESS", "{}", null, null);
        return token;
    }

    private boolean isBlocked(User user) {
        return user.isBlocked() && user.getBlockeduntil() != null && Instant.now().isBefore(user.getBlockeduntil());
    }

    private void blockIfExceeded(User user, String key) {
        Instant since = Instant.now().minus(Duration.ofMinutes(10));
        long fails = attemptRepository.recent(key, since).stream().filter(a -> !a.isSuccess()).count();

        if (fails >= 5) {
            user.setBlocked(true);
            user.setBlockeduntil(Instant.now().plus(Duration.ofMinutes(5)));
            userRepository.save(user);
        }
    }

    private LoginAttempt success(String id) {
        LoginAttempt l = new LoginAttempt();
        l.setUsernameOrEmail(id);
        l.setSuccess(true);
        return l;
    }

    private LoginAttempt failed(String id) {
        LoginAttempt l = new LoginAttempt();
        l.setUsernameOrEmail(id);
        l.setSuccess(false);
        return l;
    }

}
