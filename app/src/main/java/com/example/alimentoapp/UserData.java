package com.example.alimentoapp;

public class UserData {

    String username, phoneno, uaddress;

    public UserData(){

    }

    public UserData(String username, String phoneno, String uaddress) {
        this.username = username;
        this.phoneno = phoneno;
        this.uaddress = uaddress;
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

    public String getUaddress() {
        return uaddress;
    }

    public void setUaddress(String uaddress) {
        this.uaddress = uaddress;
    }
}
