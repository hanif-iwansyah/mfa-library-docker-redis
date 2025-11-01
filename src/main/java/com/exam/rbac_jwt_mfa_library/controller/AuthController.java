package com.exam.rbac_jwt_mfa_library.controller;

import com.exam.rbac_jwt_mfa_library.dto.LoginRequest;
import com.exam.rbac_jwt_mfa_library.dto.LoginResponse;
import com.exam.rbac_jwt_mfa_library.dto.OtpVerifyRequest;
import com.exam.rbac_jwt_mfa_library.dto.RegisterRequest;
import com.exam.rbac_jwt_mfa_library.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication Management", description = "Operations related to authentication management")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        //return ResponseEntity.accepted().build();
        return ResponseEntity.ok(authService.beginLogin(request));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verify(@Valid @RequestBody OtpVerifyRequest request) {
        return ResponseEntity.ok(new LoginResponse(authService.verifyOtpAndIssueToken(request)));
    }

}
