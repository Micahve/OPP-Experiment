package com.fontys.opaexperiment.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class OpaService {
    private final RestTemplate restTemplate;

    @Value("${opa.url}")
    private String opaUrl;

    public OpaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Set<String> getRedactedFields(String dataType, String requestType,
                                         Map<String, Object> fields, String role) {

        // Step 1 — ask the router which policy to use
        String policyPath = resolvePolicyPath(dataType, requestType);

        if (policyPath.equals("policies/unknown")) {
            throw new IllegalArgumentException(
                    "No policy found for dataType=" + dataType + ", requestType=" + requestType
            );
        }

        // Step 2 — call the resolved policy with the actual fields
        return queryRedactionPolicy(policyPath, role);
    }

    private String resolvePolicyPath(String dataType, String requestType) {
        Map<String, Object> input = Map.of(
                "dataType",    dataType,
                "requestType", requestType
        );

        ResponseEntity<Map> response = restTemplate.postForEntity(
                opaUrl + "/v1/data/policies/router",
                Map.of("input", input),
                Map.class
        );

        Map<String, Object> result = (Map<String, Object>) response.getBody().get("result");
        return (String) result.get("policy_path");
    }

    private Set<String> queryRedactionPolicy(String policyPath, String role) {
        Map<String, Object> input = Map.of("role", role);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                opaUrl + "/v1/data/" + policyPath,
                Map.of("input", input),
                Map.class
        );

        List<String> redacted = (List<String>) response.getBody().get("result");
        return new HashSet<>(redacted);
    }
}