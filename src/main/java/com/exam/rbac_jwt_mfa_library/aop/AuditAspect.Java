package com.exam.rbac_jwt_mfa_library.aop;

import com.exam.rbac_jwt_mfa_library.service.AuditService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class AuditAspect {
    private final AuditService auditService;
    private final HttpServletRequest request;

    public AuditAspect(AuditService auditService, HttpServletRequest request) {
        this.auditService = auditService;
        this.request = request;
    }

    @After("execution(* com.example.lib.web.*.*(..))")
    public void afterController(JoinPoint joinPoint) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long uid = null;
        if (authentication != null && authentication.getName() != null) {  }
        String action = joinPoint.getSignature().toShortString();
        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        auditService.log(uid, action, "{}", ip, userAgent);
    }

}
