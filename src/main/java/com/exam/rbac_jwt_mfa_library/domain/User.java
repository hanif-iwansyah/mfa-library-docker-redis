package com.exam.rbac_jwt_mfa_library.domain;

import java.time.Instant;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullname;
    
    @Column(unique = true, nullable = false) 
    private String username;
    
    @Column(unique = true, nullable = false) 
    private String email;
    private String password;

    private boolean mfaenabled = true;
    private boolean blocked = false;
    private Instant blockeduntil;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isMfaenabled() {
        return mfaenabled;
    }

    public void setMfaenabled(boolean mfaenabled) {
        this.mfaenabled = mfaenabled;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public Instant getBlockeduntil() {
        return blockeduntil;
    }

    public void setBlockeduntil(Instant blockeduntil) {
        this.blockeduntil = blockeduntil;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}
