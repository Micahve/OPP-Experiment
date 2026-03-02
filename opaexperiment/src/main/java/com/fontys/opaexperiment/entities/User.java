package com.fontys.opaexperiment.entities;

public class User {
    public String firstName;
    private String lastName;
    private Role role;              //so we can modify or block based on role
    private String countryOfOrigin; //so we can block requests between countries
}