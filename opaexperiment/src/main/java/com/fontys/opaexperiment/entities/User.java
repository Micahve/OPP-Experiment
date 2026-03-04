package com.fontys.opaexperiment.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User {
    public int ID;
    public String firstName;
    private String lastName;
    private Role role;              //so we can modify or block based on role
    private String countryOfOrigin; //so we can block requests between countries

    public User(int ID, String firstName, String lastName, Role role, String countryOfOrigin) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.countryOfOrigin = countryOfOrigin;

    }

    @JsonIgnore
    public String printUserInfo(){
        return  "\n"
                + "Name: " + firstName + " " + lastName + "\n"
                + "Role: " + role + "\n"
                + "Country of origin: " + countryOfOrigin;
    }
}