package com.fontys.opaexperiment.controller;

import com.fontys.opaexperiment.data.DataStorage;
import com.fontys.opaexperiment.entities.User;
import com.fontys.opaexperiment.service.JWTService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {

    private final DataStorage dataStorage;
    private final JWTService jwtService;

    public LoginController(DataStorage dataStorage, JWTService jwtService) {
        this.dataStorage = dataStorage;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");   // or firstName, email, etc.
        String inputPassword = body.get("password");

        User user = dataStorage.getUsers().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);

        if (user == null) {
            return ResponseEntity.status(401).body("User not found");
        }

        // This is the CORRECT comparison
        if (dataStorage.passwordEncoder.matches(inputPassword, user.getPassword())) {
            // success → generate JWT
            String token = jwtService.generateToken(user.firstName, user.getRole().name());
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            return ResponseEntity.status(401).body("Wrong password");
        }
    }
}