package com.fontys.opaexperiment.config;

import com.fontys.opaexperiment.service.JWTService;
import io.github.open_policy_agent.opa.springboot.input.OPAInputSubjectCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import java.util.HashMap;

@Configuration
public class OPAConfig {

    private final JWTService jwtService;

    public OPAConfig(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Bean
    public OPAInputSubjectCustomizer opaInputSubjectCustomizer() {
        return (authentication, requestAuthorizationContext, subject) -> {
            var customSubject = new HashMap<>(subject);

            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                customSubject.put("username", authentication.getName());

                String authHeader = requestAuthorizationContext.getRequest().getHeader("Authorization");
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    String token = authHeader.substring(7);
                    customSubject.put("role", jwtService.extractRole(token));
                }
            }
            return customSubject;
        };
    }
}