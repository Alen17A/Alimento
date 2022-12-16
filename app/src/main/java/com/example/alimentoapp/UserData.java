package com.example.alimentoapp;

public class UserData {

    String username, phoneno;

    public UserData(){

    }

    public UserData(String username, String phoneno) {
        this.username = username;
        this.phoneno = phoneno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }
}
