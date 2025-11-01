package com.exam.rbac_jwt_mfa_library.controller;

import com.exam.rbac_jwt_mfa_library.repo.AuditLogRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/audit")
@Tag(name = "Audit Management", description = "Operations related to audit management")
public class AuditController {
    private final AuditLogRepository auditLogRepository;
    public AuditController(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }
    @GetMapping
    public Object list() {
        return auditLogRepository.findAll();
    }
}
