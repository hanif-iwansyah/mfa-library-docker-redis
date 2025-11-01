package com.exam.rbac_jwt_mfa_library.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateProductRequest(@NotBlank String name, @NotBlank String content, Boolean isActive) {}