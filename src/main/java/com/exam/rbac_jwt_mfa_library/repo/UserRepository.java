package com.exam.rbac_jwt_mfa_library.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.exam.rbac_jwt_mfa_library.domain.User;

public interface UserRepository extends JpaRepository<User, Long> { 
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
