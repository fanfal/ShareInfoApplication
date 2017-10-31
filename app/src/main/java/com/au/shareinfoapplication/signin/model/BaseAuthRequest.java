package com.au.shareinfoapplication.signin.model;


public class BaseAuthRequest {
    private String phoneNum;
    private String password;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
