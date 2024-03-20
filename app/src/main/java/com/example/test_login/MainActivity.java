package com.example.test_login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化控件
        tvContent = findViewById(R.id.tv_content);

        //intent接收数据
        Intent intent = getIntent();
        if (intent != null) {
            String account = intent.getStringExtra("account");
            tvContent.setText("欢迎你：" + account);
        }

    }

    //退出登录
    public void logout(View view) {
        //取消自动登录
        SharedPreferences spRecord = getSharedPreferences("spRecord", MODE_PRIVATE);
        SharedPreferences.Editor edit = spRecord.edit();
        edit.putBoolean("isAutoLogin", false);
        edit.apply();

        Intent intent = new Intent(this, LoginActivity.class);
        //销毁首页
        MainActivity.this.finish();
        startActivity(intent);
    }
}