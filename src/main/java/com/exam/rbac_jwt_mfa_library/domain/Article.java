package com.exam.rbac_jwt_mfa_library.domain;

import java.io.Serializable;
import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "articles")
public class Article implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;

    @Lob
    private String content;

    @Column(nullable = false)
    private Long authorId;
    private Instant createdAt;
    private Instant updateAt;
    private boolean isPublic = true;
    
    @PreUpdate
    void onUpdate() { this.updateAt = Instant.now(); }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Article(Long id, String title, String content, Long authorId, Instant createdAt, Instant updateAt,
            boolean isPublic) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.isPublic = isPublic;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Article() {
    }
    
}
