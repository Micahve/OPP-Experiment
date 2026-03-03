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
    public void init(){
        AddUser(new User(0, "Bob", "Builder", Role.Admin, "Netherlands"));
        AddUser(new User(1, "Ben", "Adler", Role.Researcher, "Germany"));
        AddUser(new User(2, "Thomas", "Miller", Role.Basic, "United States of America"));

        for(User user : users){
            System.out.println(user.printUserInfo());
        }
    }
}