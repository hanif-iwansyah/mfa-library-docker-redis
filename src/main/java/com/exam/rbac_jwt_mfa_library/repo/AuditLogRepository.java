package com.exam.rbac_jwt_mfa_library.repo;

import com.exam.rbac_jwt_mfa_library.domain.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> { }
