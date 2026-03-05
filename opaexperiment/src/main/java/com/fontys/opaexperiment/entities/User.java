package com.fontys.opaexperiment.entities;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.Getter;

@Data
public class User {
    public int ID;
    public String firstName;
    @Getter
    private String password;
    private String lastName;
    @Getter
    private String username;
    @Getter
    private Role role;              //so we can modify or block based on role
    public String countryOfOrigin; //so we can block requests between countries

    public User(int ID, String firstName, String lastName, String password, Role role, String countryOfOrigin) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
        this.countryOfOrigin = countryOfOrigin;
        this.username = firstName+lastName;

    }
    public String printUserInfo(){
        return  "\n"
                + "Name: " + firstName + " " + lastName + "\n"
                + "Username: " + username + "\n"
                + "Role: " + role + "\n"
                + "Country of origin: " + countryOfOrigin + "\n";
    }
}