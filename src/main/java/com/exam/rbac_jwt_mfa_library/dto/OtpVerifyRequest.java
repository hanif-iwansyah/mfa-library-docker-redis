package com.exam.rbac_jwt_mfa_library.dto;

import jakarta.validation.constraints.NotBlank;

public record OtpVerifyRequest(@NotBlank String usernameOrEmail, @NotBlank String otp) {
}