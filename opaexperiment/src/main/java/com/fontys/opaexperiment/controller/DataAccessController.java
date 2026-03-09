package com.fontys.opaexperiment.controller;
import com.fontys.opaexperiment.data.DataStorage;
import com.fontys.opaexperiment.entities.ResearchData;
import com.fontys.opaexperiment.entities.User;
import com.fontys.opaexperiment.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DataAccessController {
    @Value("${opa.url}")
    private String opaUrl;

    @Autowired
    private DataStorage dataStorage;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(@RequestHeader("Authorization") String authHeader){
        System.out.println("Starting Get All Users Now!");

        String token = authHeader.replace("Bearer ", "");

        String username = jwtService.extractUsername(token);
        String role = jwtService.extractRole(token);

        Map<String, Object> input = new HashMap<>();
        input.put("username", username);
        input.put("role", role);
        input.put("path", "/users");
        input.put("method", "GET");

        Map<String, Object> request = new HashMap<>();
        request.put("input", input);

        ResponseEntity<Map> response = restTemplate.postForEntity(opaUrl + "/v1/data/roleauth/allow", request, Map.class);

        System.out.println(response);

        Boolean allowed = (Boolean) response.getBody().get("result");

        System.out.println(allowed);

        if(allowed) {
            List<User> allUsers = dataStorage.getUsers();
            for (User user : allUsers) {
                System.out.println(user.printUserInfo());
            }
            System.out.println("\n");
            return ResponseEntity.ok(allUsers);
        }
        Map<String, String> error = new HashMap<>();
        error.put("error", "Access denied by OPA policy");

        return ResponseEntity.status(403).body(error);
    }

    @GetMapping("/researchdata")
    public List<ResearchData> getAllResearchData() {
        System.out.println("Starting Get All the ResearchData Now!");
        List<ResearchData> allData = dataStorage.getAvailableResearchData();

        for (ResearchData researchData : allData) {
            System.out.println(researchData.getCompiledResearchDataString());
        }
        System.out.println("\n");
        return allData;
    }

    @GetMapping("/users/{countryOfOrigin}")
    public List<User> getUsersFromCountry(@PathVariable String countryOfOrigin){
        List<User> allUsers = dataStorage.getUsers();
        return filterUsersOnCountry(allUsers, countryOfOrigin);
    }

    private List<User> filterUsersOnCountry(List<User> allUsers, String countryOfOrigin){
        List<User> filteredUserList = new ArrayList<User>();
        for (User user : allUsers){
            if(user.countryOfOrigin.equals(countryOfOrigin)){ filteredUserList.add(user); }
        }
        if(!filteredUserList.isEmpty()){
            for (User user : filteredUserList){
                System.out.println(user.printUserInfo());
            }
        }
        System.out.println("\n");
        return filteredUserList;
    }
}