package com.exam.rbac_jwt_mfa_library.dto;

public record ProductDto(Long id, String title, String content, Long authorId, boolean isActive) {
}