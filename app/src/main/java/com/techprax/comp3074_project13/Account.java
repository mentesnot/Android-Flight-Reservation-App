package com.techprax.comp3074_project13;


public class Account {
    private int accountID;
    private String email;
    private String password;

    public Account(){

    }

    public Account(String email){
        this.email = email;
    }
    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
