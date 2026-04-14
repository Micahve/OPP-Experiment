package com.fontys.opaexperiment.controller;
import com.fontys.opaexperiment.data.DataStorage;
import com.fontys.opaexperiment.dto.DynamicRequestFixedDataRequestTypeDTO;
import com.fontys.opaexperiment.entities.ResearchData;
import com.fontys.opaexperiment.entities.User;
import com.fontys.opaexperiment.service.DataMaskingService;
import com.fontys.opaexperiment.service.JWTService;
import com.fontys.opaexperiment.service.OpaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class DataAccessController {
    @Value("${opa.url}")
    private String opaUrl;
    private final DataStorage dataStorage;
    private final JWTService jwtService;
    private final DataMaskingService dataMaskingService;
    private final OpaService opaService;

    public DataAccessController(DataStorage dataStorage, JWTService jwtService, DataMaskingService dataMaskingService, OpaService opaService){
        this.dataStorage = dataStorage;
        this.jwtService = jwtService;
        this.dataMaskingService = dataMaskingService;
        this.opaService = opaService;
    }

    private String getRoleFromJWT(String token){
        return jwtService.extractRole(token.substring(7));
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(dataStorage.getUsers());
    }

    @GetMapping("/researchdata")
    public ResponseEntity<?> getAllResearchData(@RequestHeader("Authorization") String authHeader) {
        Set<String> redactedFields = dataMaskingService.getRedactedFields(getRoleFromJWT(authHeader), "/researchdata", "GET");

        List<ResearchData> masked = dataStorage.getAvailableResearchData().stream()
                .map(record -> dataMaskingService.applyMask(new ResearchData(record), redactedFields))
                .toList();

        return ResponseEntity.ok(masked);
    }

    @GetMapping("/test1")
    public ResponseEntity<?> getAllResearchDataTest1(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody DynamicRequestFixedDataRequestTypeDTO body)  {

        Set<String> redactedFields = opaService.getRedactedFields(
                body.getDataType(),
                body.getRequestType(),
                body.getExtraFields(),
                getRoleFromJWT(authHeader)
        );

        List<ResearchData> masked = dataStorage.getAvailableResearchData().stream()
                .map(record -> dataMaskingService.applyMask(new ResearchData(record), redactedFields))
                .toList();

        return ResponseEntity.ok(masked);
    }

    @GetMapping("/{dataType}/{requestType}")
    public ResponseEntity<?> getAllResearchDataTest1point5(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String dataType,
            @PathVariable String requestType,
            @RequestBody Map<String, Object> requestedFields) {

        Set<String> redactedFields = opaService.getRedactedFields(
                dataType,
                requestType,
                requestedFields,
                getRoleFromJWT(authHeader)
        );

        List<ResearchData> masked = dataStorage.getAvailableResearchData().stream()
                .map(record -> dataMaskingService.applyMask(new ResearchData(record), redactedFields))
                .toList();

        return ResponseEntity.ok(masked);
    }

    @GetMapping("/test3")
    public ResponseEntity<?> getAllResearchDataTest3(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Map<String, Object> requestedFields) {


        Set<String> redactedFields = dataMaskingService.getRedactedFields(getRoleFromJWT(authHeader), "/researchdata", "GET");

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