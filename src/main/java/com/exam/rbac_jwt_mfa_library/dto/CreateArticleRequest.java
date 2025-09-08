package com.exam.rbac_jwt_mfa_library.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateArticleRequest(@NotBlank String title, @NotBlank String content, Boolean isPublic) {
    public CreateArticleRequest () {
        this(null, null, null);
    }

    public void authorId(long l) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'authorId'");
    }

    public void content(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'content'");
    }

    public void title(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'title'");
    }

}