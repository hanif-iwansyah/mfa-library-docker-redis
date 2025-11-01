package com.exam.rbac_jwt_mfa_library.service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Set;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.exam.rbac_jwt_mfa_library.domain.Product;
import com.exam.rbac_jwt_mfa_library.domain.Role;
import com.exam.rbac_jwt_mfa_library.domain.User;
import com.exam.rbac_jwt_mfa_library.dto.CreateProductRequest;
import com.exam.rbac_jwt_mfa_library.dto.UpdateProductRequest;
import com.exam.rbac_jwt_mfa_library.repo.ProductRepository;
import com.exam.rbac_jwt_mfa_library.repo.UserRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository articleRepo;
    private final UserRepository userRepo;

    public ProductService(ProductRepository a, UserRepository u) {
        this.articleRepo = a;
        this.userRepo = u;
    }

    @Cacheable("activeProduct")
    public List<Product> listPublic() {
        return articleRepo.findByIsActiveTrue();
    }

    @Transactional(rollbackFor = IllegalArgumentException.class)
    public Product create(CreateProductRequest req, String username) {
        User usr = userRepo.findByUsername(username).orElseThrow();
        Product product = new Product(null, username, username, null, null, null, false);
        product.setName(req.name());
        product.setContent(req.content());
        product.setAuthorId(usr.getId());
        product.setActive(req.isActive() == null || req.isActive());
        return articleRepo.save(product);
    }

    public Product update(Long id, UpdateProductRequest req, String username, Set<Role> roles) throws AccessDeniedException {
        Product product = articleRepo.findById(id).orElseThrow();
        boolean owner = isOwner(product, username);
        if (roles.contains(Role.SUPER_ADMIN) || (roles.contains(Role.EDITOR) && owner) || (roles.contains(Role.CONTRIBUTOR) && owner)) {
            product.setName(req.name());
            product.setContent(req.content());
            if (req.isActive() != null) product.setActive(req.isActive());
            return articleRepo.save(product);
        }
        throw new AccessDeniedException("Not Allowed");
    }

    public void delete(Long id, String username, Set<Role> roles) throws AccessDeniedException {
        Product product = articleRepo.findById(id).orElseThrow();
        boolean owner = isOwner(product, username);
        if (roles.contains(Role.SUPER_ADMIN) || (roles.contains(Role.EDITOR) && owner)) {
            articleRepo.delete(product);
            return;
        }
        throw new AccessDeniedException("Not Allowed");
    }

    private boolean isOwner(Product product, String username) {
        return userRepo.findById(product.getAuthorId()).map(userRepo -> userRepo.getUsername().equals(username)).orElse(false);
    }

}
