package com.example.assignment3.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment3.Network.ApiConfig;
import com.example.assignment3.Model.ApiResponse;
import com.example.assignment3.Helper.ApiData;
import com.example.assignment3.R;
import com.google.firebase.messaging.FirebaseMessaging;


public class SignInActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    String token = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        getToken();
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        Button btnLogin = findViewById(R.id.btn_login);
        TextView txtNoAccount = findViewById(R.id.txt_donot_account);



        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            if (email.isEmpty()){
                ApiData.Toast(SignInActivity.this, "قم بادخال البريد الالكتروني");
                return;
            }
            if (password.isEmpty()){
                ApiData.Toast(SignInActivity.this, "قم بادخال كلمة المرور");
                return;
            }

            ApiData.progressDialogKH(SignInActivity.this, true);
            new ApiConfig(SignInActivity.this, false, (obj, fun, IsSuccess) -> {
                if (IsSuccess) {
                    ApiResponse model = (ApiResponse) obj;
                   ApiData.Toast(SignInActivity.this, model.msg);
                    new ApiConfig(SignInActivity.this, false, (obj1, fun1, IsSuccess1) -> {
                        ApiData.progressDialogKH(SignInActivity.this, false);
                        if (IsSuccess1) {
                            ApiResponse model1 = (ApiResponse) obj1;
                           ApiData.Toast(SignInActivity.this, model1.msg);
                            startActivity(new Intent(SignInActivity.this,MainActivity.class));

                        } else {
                            String msg = (String) obj1;
                        ApiData.Toast(SignInActivity.this, msg);
                        }

                    }).addRegTokenApi(email, password,token);


                } else {
                   ApiData.progressDialogKH(SignInActivity.this, false);
                    String msg = (String) obj;
                  ApiData.Toast(SignInActivity.this, msg);
                }

            }).loginApi(email, password);

        });

        txtNoAccount.setOnClickListener(v -> {
            Intent intent = new Intent(SignInActivity.this,SignUpActivity.class);
            startActivity(intent);
        });

    }


    private void getToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if(!task.isSuccessful()){
                Log.e("tokenF","Failed Token");
                return;
            }

             token = task.getResult();
            Log.e("token","Token : " + token );
        });
    }
}