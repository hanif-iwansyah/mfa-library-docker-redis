package com.exam.rbac_jwt_mfa_library.domain;

import java.time.Instant;

import jakarta.persistence.*;

@Entity
@Table(name = "login_attempt")
public class LoginAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String usernameOrEmail;
    private boolean success;
    private Instant at = Instant.now();
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }
    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public Instant getAt() {
        return at;
    }
    public void setAt(Instant at) {
        this.at = at;
    }

    
}
