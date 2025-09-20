package com.exam.security;

import io.github.bucket4j.*;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimiterFilter implements Filter {
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    private Bucket bucketFor(String key) {
        return cache.computeIfAbsent(key, k -> Bucket.builder()
                .addLimit(Bandwidth.classic(120, Refill.intervally(120, Duration.ofMinutes(1))))
                .build());
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String key = req.getRemoteAddr() + ":" + req.getRequestURI();
        if (!bucketFor(key).tryConsume(1)) { res.setStatus(429); return;}
        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
