package com.exam.rbac_jwt_mfa_library.service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Set;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.exam.rbac_jwt_mfa_library.domain.Article;
import com.exam.rbac_jwt_mfa_library.domain.Role;
import com.exam.rbac_jwt_mfa_library.domain.User;
import com.exam.rbac_jwt_mfa_library.dto.CreateArticleRequest;
import com.exam.rbac_jwt_mfa_library.dto.UpdateArticleRequest;
import com.exam.rbac_jwt_mfa_library.repo.ArticleRepository;
import com.exam.rbac_jwt_mfa_library.repo.UserRepository;

@Service
public class ArticleService {

    private final ArticleRepository articleRepo;
    private final UserRepository userRepo;

    public ArticleService(ArticleRepository a, UserRepository u) {
        this.articleRepo = a;
        this.userRepo = u;
    }

    @Cacheable("publicArticles")
    public List<Article> listPublic() {
        return articleRepo.findByIsPublicTrue();
    }

    public Article create(CreateArticleRequest req, String username) {
        User usr = userRepo.findByUsername(username).orElseThrow();
        Article article = new Article(null, username, username, null, null, null, false);
        article.setTitle(req.title());
        article.setContent(req.content());
        article.setAuthorId(usr.getId());
        article.setPublic(req.isPublic() == null || req.isPublic());
        return articleRepo.save(article);
    }

    public Article update(Long id, UpdateArticleRequest req, String username, Set<Role> roles) throws AccessDeniedException {
        Article article = articleRepo.findById(id).orElseThrow();
        boolean owner = isOwner(article, username);
        if (roles.contains(Role.SUPER_ADMIN) || (roles.contains(Role.EDITOR) && owner)
                || (roles.contains(Role.CONTRIBUTOR) && owner)) {
                    article.setTitle(req.title());
                    article.setContent(req.content());
                    if (req.isPublic() != null) article.setPublic(req.isPublic());
                    return articleRepo.save(article);
        }
        throw new AccessDeniedException("Not Allowed");
    }

    public void delete(Long id, String username, Set<Role> roles) throws AccessDeniedException {
        Article article = articleRepo.findById(id).orElseThrow();
        boolean owner = isOwner(article, username);
        if (roles.contains(Role.SUPER_ADMIN) || (roles.contains(Role.EDITOR) && owner)) {
            articleRepo.delete(article);
            return;
        }
        throw new AccessDeniedException("Not Allowed");
    }

    private boolean isOwner(Article article, String username) {
        return userRepo.findById(article.getAuthorId()).map(userRepo -> userRepo.getUsername().equals(username))
                .orElse(false);
    }

}
