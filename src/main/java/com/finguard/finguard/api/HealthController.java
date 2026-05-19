package com.finguard.finguard.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/api/health")
    public Map<String, Object> health() {
        return Map.of(
            "status", "UP",
            "service", "FinGuard",
            "timestamp", System.currentTimeMillis()
        );
    }

    @GetMapping("/api/info")
    public Map<String, Object> info() {
        return Map.of(
            "name", "FinGuard",
            "version", "1.0.0"
        );
    }
}
