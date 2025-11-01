package com.exam.rbac_jwt_mfa_library.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.exam.rbac_jwt_mfa_library.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByAuthorId(Long authorId);
    List<Product> findByIsActiveTrue();

}