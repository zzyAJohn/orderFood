package com.example.orderfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.orderfood.activity.BusinessActivity;
import com.example.orderfood.activity.RegisteredBusiness;
import com.example.orderfood.activity.RegisteredUsers;
import com.example.orderfood.dao.UserDao;
import com.example.orderfood.db.DBUtil;
import com.example.orderfood.user.activity.UserManageActivity;

public class MainActivity extends AppCompatActivity {

    EditText etId, etPwd;
    RadioButton rbBusiness, rbUser;
    Button btLogin, btRegisterBusiness, btRegisterUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化控件
        initView();

        //初始化数据
        initData();
    }

    private void initData() {
        //调用连接数据库
        DBUtil dbUtil = new DBUtil(MainActivity.this);
        SQLiteDatabase db = dbUtil.getWritableDatabase();//获取到得数据库连接
        DBUtil.db = db;
    }

    //初始化控件
    private void initView() {
        etId = this.findViewById(R.id.et_id);
        etPwd = this.findViewById(R.id.et_pwd);

        rbBusiness = this.findViewById(R.id.rb_business);
        //让默认选择的是商家
        rbBusiness.setChecked(true);

        btLogin = findViewById(R.id.bt_login);
        btRegisterBusiness = findViewById(R.id.bt_register_business);
        btRegisterUser = findViewById(R.id.bt_register_user);
    }

    //登录
    public void login(View view) {
        String account = etId.getText().toString();
        String password = etPwd.getText().toString();

        //如果账号为空
        if (account.isEmpty()) {
            Toast.makeText(MainActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) { //如果密码为空
            Toast.makeText(MainActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
        } else {
            //都填写了
            //是商家
            if (rbBusiness.isChecked()) {
                int a = UserDao.loginUser(account, password, "1");
                if (a == 0) { //密码错误
                    Toast.makeText(MainActivity.this, "账号或密码错误，登录失败", Toast.LENGTH_SHORT).show();
                } else {
                    //密码正确
                    //进行数据共享
                    SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString("account", account);
                    edit.apply();

                    //跳转商家页面
                    Intent intent = new Intent(MainActivity.this, BusinessActivity.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                }
            } else { //是用户
                int a = UserDao.loginUser(account, password, "2");
                if (a == 0) { //密码错误
                    Toast.makeText(MainActivity.this, "账号或密码错误，登录失败", Toast.LENGTH_SHORT).show();
                } else {
                    //密码正确
                    //进行数据共享
                    SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString("account", account);
                    edit.apply();

                    //跳转用户页面
                    Intent intent = new Intent(MainActivity.this, UserManageActivity.class);
                    intent.putExtra("business", account);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    //跳转到注册商家页面
    public void registerBusiness(View view) {
        Intent intent = new Intent(MainActivity.this, RegisteredBusiness.class);
        startActivity(intent);
    }

    //跳转到注册用户页面
    public void registerUser(View view) {
        Intent intent = new Intent(MainActivity.this, RegisteredUsers.class);
        startActivity(intent);
    }
}