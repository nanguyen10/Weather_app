package com.example.project;

public class UserLogin {
    private String Username;
    private String Password;
    public UserLogin(String username, String password) {
        Username = username;
        Password = password;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Boolean isValidUsername(){
        return Username.equals("user1");
    }
    public Boolean isValidPassword(){
        return Password.equals("Abc@@123");
    }
}
