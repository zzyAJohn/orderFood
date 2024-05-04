package com.example.orderfood.activity;
/**
 * 这个是修改密码
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.orderfood.R;
import com.example.orderfood.dao.UserDao;

public class UpdatePwdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pwd);


        //通过按钮的点击时间实现修改密码update_business_pwd_button
        SharedPreferences sharedPreferences=getSharedPreferences("data", Context.MODE_PRIVATE);
        String account=sharedPreferences.getString("account","");
        EditText one= findViewById(R.id.update_business_pwd_one);
        EditText two= findViewById(R.id.update_business_pwd_two);


      Button button= findViewById(R.id.update_business_pwd_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String oneT=one.getText().toString();
                String twoT=two.getText().toString();

                if(oneT.isEmpty()){
                    Toast.makeText(UpdatePwdActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                }else if(twoT.isEmpty()){
                    Toast.makeText(UpdatePwdActivity.this, "请输入请确认密码", Toast.LENGTH_SHORT).show();
                }else if(!oneT.equals(twoT)){
                    Toast.makeText(UpdatePwdActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                }else{
                    int a=UserDao.updateBusinessUserPwd(account,oneT);
                    if(a==1){
                        Toast.makeText(UpdatePwdActivity.this, "修改密码成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(UpdatePwdActivity.this, "修改密码失败", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });


        //实现返回功能
        Toolbar toolbar=findViewById(R.id.update_business_pwd);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UpdatePwdActivity.this,BusinessMyActivity.class);
                startActivity(intent);
            }
        });


    }
}