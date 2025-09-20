package com.exam.rbac_jwt_mfa_library.repo;

import com.exam.rbac_jwt_mfa_library.domain.LoginAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, Long> {
    @Query("select l from LoginAttempt l where l.usernameOrEmail = :id and l.at > :since")
    List<LoginAttempt> recent(String id, Instant since);
}
