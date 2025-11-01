package com.exam.rbac_jwt_mfa_library.controller;

import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.exam.rbac_jwt_mfa_library.domain.Role;
import com.exam.rbac_jwt_mfa_library.dto.CreateProductRequest;
import com.exam.rbac_jwt_mfa_library.dto.UpdateProductRequest;
import com.exam.rbac_jwt_mfa_library.service.ProductService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/product")
@Tag(name = "Product Management", description = "Operations related to product management")
public class ProductController {
    private final ProductService articleSvc;

    public ProductController(ProductService svc) {
        this.articleSvc = svc;
    }

    @GetMapping("/active")
    public ResponseEntity<?> listActive() {
        return ResponseEntity.ok(articleSvc.listPublic());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateProductRequest req, Authentication auth) {
        return ResponseEntity.ok(articleSvc.create(req, auth.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody UpdateProductRequest req, Authentication auth) throws AccessDeniedException {
        Set<Role> roles = (Set<Role>) ((Map<?,?>)auth.getDetails());
        return ResponseEntity.ok(articleSvc.update(id, req, auth.getName(), roles));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Authentication auth) throws AccessDeniedException {
        Set<Role> roles = Set.of(Role.SUPER_ADMIN);
        articleSvc.delete(id, auth.getName(), roles);
        return ResponseEntity.noContent().build();
    }

}
