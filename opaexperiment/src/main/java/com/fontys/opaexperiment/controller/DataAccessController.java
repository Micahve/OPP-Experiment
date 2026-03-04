package com.fontys.opaexperiment.controller;
import com.fontys.opaexperiment.data.DataStorage;
import com.fontys.opaexperiment.entities.ResearchData;
import com.fontys.opaexperiment.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class DataAccessController {
    @Autowired
    private DataStorage dataStorage;

    @GetMapping("/users")
    public List<User> getAllUsers(){
        System.out.println("Starting Get All Users Now!");
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
}