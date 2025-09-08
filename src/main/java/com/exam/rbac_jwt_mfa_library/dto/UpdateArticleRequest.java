package com.exam.rbac_jwt_mfa_library.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateArticleRequest(@NotBlank String title,  @NotBlank String content, Boolean isPublic) {}