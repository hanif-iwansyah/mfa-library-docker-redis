package com.exam.rbac_jwt_mfa_library.web;

import java.nio.file.AccessDeniedException;
import java.util.Set;

import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.exam.rbac_jwt_mfa_library.domain.Role;
import com.exam.rbac_jwt_mfa_library.dto.CreateArticleRequest;
import com.exam.rbac_jwt_mfa_library.dto.UpdateArticleRequest;
import com.exam.rbac_jwt_mfa_library.service.ArticleService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/articles")
@Tag(name = "Article Management", description = "Operations related to article management")
public class ArticleController {
    private final ArticleService articleSvc;

    public ArticleController(ArticleService svc) {
        this.articleSvc = svc;
    }

    @GetMapping("/public")
    public ResponseEntity<?> getAllPublicArticles() {
        return ResponseEntity.ok(articleSvc.listPublic());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateArticleRequest req, String auth) { 
    //public ResponseEntity<?> create(@Valid @RequestBody CreateArticleRequest req, Authentication auth) {
        return ResponseEntity.ok(articleSvc.create(req, "root"));
        //return ResponseEntity.ok(articleSvc.create(req, auth.g));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody UpdateArticleRequest req, String auth) throws AccessDeniedException {
    //public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody UpdateArticleRequest req, Authentication auth) throws AccessDeniedException {
        //Set<Role> roles = (Set<Role>) ((Map<?, ?>) auth.getDetails());
        Set<Role> roles = Set.of(Role.SUPER_ADMIN);
        return ResponseEntity.ok(articleSvc.update(id, req, "root", roles));
        //return ResponseEntity.ok(articleSvc.update(id, req, auth.getName(), roles));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, String username, String auth) throws AccessDeniedException {
    //public ResponseEntity<?> delete(@PathVariable Long id, String username, Authentication auth) throws AccessDeniedException {
        Set<Role> roles = Set.of(Role.SUPER_ADMIN);
        articleSvc.delete(id, "root", roles);
        //articleSvc.delete(id, auth.getName(), roles);
        return ResponseEntity.noContent().build();
    }

}
