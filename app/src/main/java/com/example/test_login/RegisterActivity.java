package com.example.test_login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int RESULT_CODE_REGISTER = 0;
    private Button btnRegister;
    private EditText etAccount, etPassword, etPasswordConfirm; //用户名，密码，确认密码
    private RadioButton rbAgree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        getSupportActionBar().setTitle("注册");

        btnRegister = findViewById(R.id.btn_register);
        etAccount = findViewById(R.id.et_account);
        etPassword = findViewById(R.id.et_password);
        etPasswordConfirm = findViewById(R.id.et_password_confirm);
        rbAgree = findViewById(R.id.rb_agree);

        btnRegister.setOnClickListener(this);

    }

    //点击注册Button
    @Override
    public void onClick(View v) {
        //控件初始化获取数据
        String account = etAccount.getText().toString();
        String password = etPassword.getText().toString();
        String passwordConfirm = etPasswordConfirm.getText().toString();

        if (TextUtils.isEmpty(account)) {
            Toast.makeText(this, "用户名为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "密码为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!TextUtils.equals(password, passwordConfirm)) {
            Toast.makeText(this, "两次密码不一致！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!rbAgree.isChecked()) {
            Toast.makeText(this, "请勾选同意协议！", Toast.LENGTH_SHORT).show();
            return;
        }

        //把注册的信息保存
        SharedPreferences spRecord = getSharedPreferences("spRecord", MODE_PRIVATE);
        SharedPreferences.Editor edit = spRecord.edit();
        edit.putString("account", account);
        edit.putString("password", password);

        //打包bundle传参，数据回传
        Intent intent = new Intent(this, LoginActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("account", account);
        bundle.putString("password", password);

        intent.putExtras(bundle);

        setResult(RESULT_CODE_REGISTER, intent);


        //注册成功，结束注册页面
        Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();
        this.finish();
    }


}