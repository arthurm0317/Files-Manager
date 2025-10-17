package com.arthur.filesorgs.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;

public interface CorsConfiguration {
    void addCorsMapping(CorsRegistry registry);
}
