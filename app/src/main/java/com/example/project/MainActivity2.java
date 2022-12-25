package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity implements LoginInterface {
    private Button btnLogin;
    private EditText edtUser, edtPassword;
    private TextView tvMessage;

    private LoginPresenter loginPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btnLogin = (Button) findViewById(R.id.btn_login);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        edtUser = (EditText) findViewById(R.id.edt_username);
        tvMessage = (TextView) findViewById(R.id.tv_message);


        loginPresenter = new LoginPresenter(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickLogin();
            }
        });
    }

    private void clickLogin() {
        String strUsername = edtUser.getText().toString().trim();
        String strPassword = edtPassword.getText().toString().trim();

        UserLogin userLogin = new UserLogin(strUsername,strPassword);
        loginPresenter.login(userLogin);

    }

    @Override
    public void LoginSuccess() {

        Intent intent = new Intent(MainActivity2.this,SplashActivity.class);
        startActivity(intent);
    }

    @Override
    public void LoginError() {
        tvMessage.setVisibility(View.VISIBLE);
        tvMessage.setText("Username or password invalid");
        tvMessage.setTextColor(getResources().getColor(R.color.red));
    }
}