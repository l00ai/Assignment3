package com.example.assignment3.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment3.Network.ApiConfig;
import com.example.assignment3.Model.ApiResponse;
import com.example.assignment3.Helper.ApiData;
import com.example.assignment3.R;

public class SignUpActivity extends AppCompatActivity {

    private EditText etFName;
    private EditText etLName;
    private EditText etEmail;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etFName = findViewById(R.id.et_fName);
        etLName = findViewById(R.id.et_lName);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        Button btnLogin = findViewById(R.id.btn_login);
        TextView txtAccount = findViewById(R.id.txt_account);


        btnLogin.setOnClickListener(v -> {
            String fName = etFName.getText().toString();
            String lName = etLName.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            if (fName.isEmpty()) {
                ApiData.Toast(SignUpActivity.this, "ادخل الاسم الاول");
                return;
            }
            if (lName.isEmpty()) {
              ApiData.Toast(SignUpActivity.this, "ادخل الاسم الاخير");
                return;
            }
            if (email.isEmpty()) {
                ApiData.Toast(SignUpActivity.this, "ادخل البريد الالكتروني");
                return;
            }
            if (password.isEmpty()) {
                ApiData.Toast(SignUpActivity.this, "ادخل كلمة المرور");
                return;
            }

             ApiData.progressDialogKH( SignUpActivity.this, true);
            new ApiConfig( SignUpActivity.this, false, (obj, fun, IsSuccess) -> {
                 ApiData.progressDialogKH( SignUpActivity.this, false);
                if (IsSuccess) {
                    ApiResponse model = (ApiResponse) obj;
                     ApiData.Toast( SignUpActivity.this, model.msg);
                    Intent intent = new Intent( SignUpActivity.this,SignInActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    String msg = (String) obj;
                     ApiData.Toast( SignUpActivity.this, msg);
                }

            }).addNewUserApi(fName, lName, email, password);

        });

        txtAccount.setOnClickListener(v -> finish());

    }
}