package com.example.test_login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class UserProfileActivity extends AppCompatActivity {

    private TextView tvNickName, tvGender, tvAge;
    private TextView tvAccount, tvBirthTime, tvCity, tvSchool, tvSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //初始化控件
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();

        //初始化数据,便于修改后返回个人页面刷新信息
        initData();
    }

    //初始化控件
    private void initView() {
        tvNickName = findViewById(R.id.tv_nick_name);
        tvGender = findViewById(R.id.tv_gender);
        tvAge = findViewById(R.id.tv_age);

        tvAccount = findViewById(R.id.tv_account_text);
        tvBirthTime = findViewById(R.id.tv_birth_time_text);
        tvCity = findViewById(R.id.tv_city_text);
        tvSchool = findViewById(R.id.tv_school_text);
        tvSign = findViewById(R.id.tv_sign_text);
    }

    //初始化数据
    private void initData() {
        //从文件取数据
        getDataFromSpf();

    }

    //从文件取数据
    private void getDataFromSpf() {
        SharedPreferences spRecord = getSharedPreferences("spRecord", MODE_PRIVATE);

        String nickName = spRecord.getString("nickName", "");
        String gender = spRecord.getString("gender", "");
        String age = spRecord.getString("age", "");

        String account = spRecord.getString("account", "");
        String birthTime = spRecord.getString("birthTime", "");
        String city = spRecord.getString("city", "");
        String school = spRecord.getString("school", "");
        String sign = spRecord.getString("sign", "");

        tvNickName.setText(nickName);
        tvGender.setText(gender);
        tvAge.setText(age);

        tvAccount.setText(account);
        tvBirthTime.setText(birthTime);
        tvCity.setText(city);
        tvSchool.setText(school);
        tvSign.setText(sign);
    }

    //点击修改资料跳转到编辑页面
    public void toEdit(View view) {
        Intent intent = new Intent(this, EditProfileActivity.class);


        startActivity(intent);
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
        UserProfileActivity.this.finish();
        startActivity(intent);
    }


}