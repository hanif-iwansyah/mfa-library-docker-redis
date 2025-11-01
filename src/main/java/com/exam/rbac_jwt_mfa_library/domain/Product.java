package com.exam.rbac_jwt_mfa_library.domain;

import java.io.Serializable;
import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;

    @Lob
    private String content;



    @Column(nullable = false)
    private Long authorId;
    private Instant createdAt;
    private Instant updateAt;
    private boolean isActive = true;
    
    @PreUpdate
    void onUpdate() { this.updateAt = Instant.now(); }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Instant updateAt) {
        this.updateAt = updateAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Product(Long id, String name, String content, Long authorId, Instant createdAt, Instant updateAt,
                   boolean isActive) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.authorId = authorId;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.isActive = isActive;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Product() {
    }
    
}
