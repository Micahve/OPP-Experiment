package com.fontys.opaexperiment.data;

import com.fontys.opaexperiment.entities.ResearchData;
import com.fontys.opaexperiment.entities.Role;
import com.fontys.opaexperiment.entities.User;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataStorage {
    @Getter
    private final List<User> users;
    @Getter
    private final List<ResearchData> availableResearchData;

    public DataStorage(){
         users = new ArrayList<User>();
         availableResearchData = new ArrayList<ResearchData>();
    }

    public void AddUser(User user){
        users.add(user);
    }
    public void AddToAvailableResearchData(ResearchData researchData){
        availableResearchData.add(researchData);
    }

    @PostConstruct
    private void initUsers(){
        AddUser(new User(0, "Bob", "Builder", Role.Admin, "Netherlands"));
        AddUser(new User(1, "Ben", "Adler", Role.Researcher, "Germany"));
        AddUser(new User(2, "Thomas", "Miller", Role.Basic, "United States of America"));
        AddUser(new User(3,"Pieter", "Post", Role.Subject, "Netherlands"));
        AddUser(new User(4,"Jonathan", "Frakes", Role.Subject, "Unites States of America"));
        AddUser(new User(5,"Tommy", "Waiver", Role.Subject, "United Kingdom"));
        AddUser(new User(6,"William", "Spinal", Role.Subject, "Australia"));
        AddUser(new User(7,"Pjotr", "Alaxander", Role.Subject, "Russia"));

        for(User user : users){
            System.out.println(user.printUserInfo());
        }
        System.out.println("\n");
    }
    @PostConstruct
    private void initResearchData(){
        AddToAvailableResearchData(new ResearchData(3,"Pieter", "Post", 32, "Netherlands", "02-02-2026", 88));
        AddToAvailableResearchData(new ResearchData(4,"Jonathan", "Frakes", 58, "Unites States of America", "21-08-2025", 77));
        AddToAvailableResearchData(new ResearchData(5,"Tommy", "Waiver", 19, "United Kingdom", "16-05-2024", 101));
        AddToAvailableResearchData(new ResearchData(6,"William", "Spinal", 25, "Australia", "01-11-2025", 69));
        AddToAvailableResearchData(new ResearchData(7,"Pjotr", "Alaxander", 22, "Russia", "29-12-2025", 105));

        for(ResearchData researchData : availableResearchData){
            System.out.println(researchData.getCompiledResearchDataString());
        }
        System.out.println("\n");
    }
}