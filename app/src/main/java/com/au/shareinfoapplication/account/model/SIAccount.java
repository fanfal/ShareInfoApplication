package com.au.shareinfoapplication.account.model;

public class SIAccount {
    private String password;
    private String phoneNum;
    private String token;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class Builder {
        private SIAccount siAccount;

        public Builder() {
            siAccount = new SIAccount();
        }

        public Builder setPassWord(String passWord) {
            siAccount.setPassword(passWord);
            return this;
        }

        public Builder setPhoneNum(String phoneNum) {
            siAccount.setPhoneNum(phoneNum);
            return this;
        }

        public Builder setToken(String token) {
            siAccount.setToken(token);
            return this;
        }

        public SIAccount build() {
            return siAccount;
        }
    }

}
