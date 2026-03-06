package com.fontys.opaexperiment.controller;
import com.fontys.opaexperiment.data.DataStorage;
import com.fontys.opaexperiment.entities.ResearchData;
import com.fontys.opaexperiment.entities.User;
import com.fontys.opaexperiment.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;


@RestController
public class DataAccessController {
    @Autowired
    private DataStorage dataStorage;

    @Autowired
    private JWTService jwtService;

    @GetMapping("/users")
    public List<User> getAllUsers(@RequestHeader("Authorization") String authHeader){
        System.out.println("Starting Get All Users Now!");

        String token = authHeader.replace("Bearer ", "");

        String username = jwtService.extractUsername(token);
        String role = jwtService.extractRole(token);

        System.out.println("Username: " + username);
        System.out.println("Role: " + role);

        List<User> allUsers = dataStorage.getUsers();
        for (User user : allUsers){
            System.out.println(user.printUserInfo());
        }
        System.out.println("\n");
        return allUsers;
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