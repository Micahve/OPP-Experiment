package com.fontys.opaexperiment.controller;
import com.fontys.opaexperiment.data.DataStorage;
import com.fontys.opaexperiment.entities.ResearchData;
import com.fontys.opaexperiment.entities.User;
import com.fontys.opaexperiment.service.DataMaskingService;
import com.fontys.opaexperiment.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;

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

    @Autowired
    private DataMaskingService dataMaskingService;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(dataStorage.getUsers());
    }

    @GetMapping("/researchdata")
    public ResponseEntity<?> getAllResearchData(@RequestHeader("Authorization") String authHeader) {
        String role = jwtService.extractRole(authHeader.substring(7));

        Set<String> redactedFields = dataMaskingService.getRedactedFields(role, "/researchdata", "GET");

        List<ResearchData> masked = dataStorage.getAvailableResearchData().stream()
                .map(record -> dataMaskingService.applyMask(new ResearchData(record), redactedFields))
                .toList();

        return ResponseEntity.ok(masked);
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