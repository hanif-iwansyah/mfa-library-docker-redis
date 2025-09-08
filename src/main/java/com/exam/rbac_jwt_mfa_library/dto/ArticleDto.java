package com.exam.rbac_jwt_mfa_library.dto;

public record ArticleDto(Long id, String title, String content, Long authorId, boolean isPublic) {
}