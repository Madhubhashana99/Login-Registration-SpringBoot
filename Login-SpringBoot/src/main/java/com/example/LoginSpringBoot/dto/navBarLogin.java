package com.example.LoginSpringBoot.dto;

public class navBarLogin {
    private String firstName;

    public navBarLogin(String firstName){
        this.firstName=firstName;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName=firstName;
    }
}
