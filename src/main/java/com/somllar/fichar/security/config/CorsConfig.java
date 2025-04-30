package com.somllar.fichar.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Aplica a todos los endpoints
                .allowedOrigins("http://localhost:4200")  // Aquí pones la URL de tu cliente (si es Angular, por ejemplo)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")  // Métodos permitidos
                .allowedHeaders("*");  // Permitir todos los headers
    }
}
