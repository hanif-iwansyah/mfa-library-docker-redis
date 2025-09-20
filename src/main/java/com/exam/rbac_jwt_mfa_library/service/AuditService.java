package com.exam.rbac_jwt_mfa_library.service;

import com.exam.rbac_jwt_mfa_library.domain.AuditLog;
import com.exam.rbac_jwt_mfa_library.repo.AuditLogRepository;
import org.springframework.stereotype.Service;

@Service
public class AuditService {
    private final AuditLogRepository auditLogRepository;
    public AuditService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    };

    public void log(Long userId, String action, String detail, String ip, String userAgent) {
        AuditLog auditLog = new AuditLog();
        auditLog.setUserId(userId);
        auditLog.setAction(action);
        auditLog.setDetail(detail);
        auditLog.setIp(ip);
        auditLog.setUserAgent(userAgent);
        auditLogRepository.save(auditLog);
    }

}
