package com.fontys.opaexperiment.entities;

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
    public String printUserInfo(){
        return "Name = " + firstName + " " + lastName + " Role = " + role + " country = " + countryOfOrigin;
    }
}