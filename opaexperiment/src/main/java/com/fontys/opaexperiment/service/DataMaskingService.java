package com.fontys.opaexperiment.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class DataMaskingService {

    private final RestTemplate restTemplate;

    @Value("${opa.url}")
    private String opaUrl;

    public DataMaskingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Set<String> getRedactedFields(String role, String path, String method) {
        Map<String, Object> input = Map.of(
                "subject",  Map.of("role", role),
                "resource", Map.of("type", "endpoint", "id", path),
                "action",   Map.of("name", method)
        );

        var response = restTemplate.postForEntity(
                opaUrl + "/v1/data/roleauth/redacted_fields",
                Map.of("input", input),
                Map.class
        );

        List<String> fields = (List<String>) response.getBody().get("result");
        return fields != null ? new HashSet<>(fields) : Set.of();
    }

    public <T> T applyMask(T data, Set<String> redactedFields) {
        if (redactedFields.isEmpty()) return data;

        for (String fieldName : redactedFields) {
            try {
                Field field = data.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(data, null);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // Unknown field — safely ignore
            }
        }
        return data;
    }
}