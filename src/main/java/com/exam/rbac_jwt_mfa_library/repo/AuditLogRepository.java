package com.exam.rbac_jwt_mfa_library.repo;

import com.exam.rbac_jwt_mfa_library.domain.AuditLog;
import com.exam.rbac_jwt_mfa_library.domain.LoginAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> { }
