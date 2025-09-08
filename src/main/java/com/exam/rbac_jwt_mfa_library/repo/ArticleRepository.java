package com.exam.rbac_jwt_mfa_library.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.exam.rbac_jwt_mfa_library.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByAuthorId(Long authorId);
    List<Article> findByIsPublicTrue();
}