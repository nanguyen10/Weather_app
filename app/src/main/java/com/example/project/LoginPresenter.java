package com.example.project;

public class LoginPresenter {

    private com.example.project.LoginInterface loginInterface;

    public LoginPresenter(com.example.project.  LoginInterface loginInterface) {
        this.loginInterface = loginInterface;
    }

    public void login(UserLogin userLogin){
        if(userLogin.isValidPassword() && userLogin.isValidUsername()){
            loginInterface.LoginSuccess();
        }
        else{
            loginInterface.LoginError();
        }
    }
}
