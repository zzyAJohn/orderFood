package com.example.orderfood.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderfood.MainActivity;
import com.example.orderfood.R;
import com.example.orderfood.adapter.FoodAdapter;
import com.example.orderfood.bean.BusinessBean;
import com.example.orderfood.bean.FoodBean;
import com.example.orderfood.dao.FoodDao;
import com.example.orderfood.dao.UserDao;
import com.example.orderfood.listener.ButtonColorListener;

import java.util.ArrayList;






public class BusinessMyActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_my);



        //获取当前账号,进行查询
        SharedPreferences sharedPreferences=getSharedPreferences("data", Context.MODE_PRIVATE);
        String account=sharedPreferences.getString("account","");


        //加载头像功能
       ImageView tx= findViewById(R.id.business_my_tx);
       //根据账号进行查看账号信息
        BusinessBean user = UserDao.getBusinessUser(account);

        //实现头像的显示
        Bitmap bitmap = BitmapFactory.decodeFile(user .getS_img());
        // 将加载的图像设置到ImageView中
        tx.setImageBitmap(bitmap);

        //设置ID
       TextView id=findViewById(R.id.business_my_id);//iD
       id.setText(user.getS_id());
        TextView name=findViewById(R.id.business_my_name);//名字
        name.setText(user.getS_name());
        TextView des=findViewById(R.id.business_my_des);//名字
        des.setText("店铺简介："+user.getS_describe());


        //实现退出功能
       TextView exit= findViewById(R.id.business_my_exit);//实现退出功能
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });

        //实现注销功能
        TextView zx= findViewById(R.id.business_my_zx);//实现退出功能
        zx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BusinessMyActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //实现修改密码的功能
        TextView xgmm= findViewById(R.id.business_my_xgmm);//实现退出功能
        xgmm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BusinessMyActivity.this, UpdatePwdActivity.class);
                startActivity(intent);
            }
        });
        //修改店铺信息

        TextView xgdpxx= findViewById(R.id.business_my_xgdpxx);//实现退出功能
        xgdpxx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BusinessMyActivity.this, UpdateBusinessMesActivity.class);
                startActivity(intent);
            }
        });




        //实现点击按钮的时候设置一下选中的颜色
        Button home= findViewById(R.id.business_home);
        Button add =findViewById(R.id.business_add);
        Button my= findViewById(R.id.business_my);





        home.setOnClickListener(new ButtonColorListener(home,add,my,1));
        add.setOnClickListener(new  ButtonColorListener(home,add,my,1));
        my.setOnClickListener(new  ButtonColorListener(home,add,my,1));
        my.callOnClick();//模拟点击home

        //点击加号进行跳转界面
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BusinessMyActivity.this,AddGoodActivity.class);
                startActivity(intent);


            }
        });


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BusinessMyActivity.this,BusinessActivity.class);
                startActivity(intent);


            }
        });


        RadioButton radioButton =findViewById(R.id.order_manage_m);
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BusinessMyActivity.this,OrderActivity.class);
                startActivity(intent);
            }
        });
        RadioButton order_manage_comment =findViewById(R.id.order_manage_comment);
        order_manage_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BusinessMyActivity.this,BusinessCommentActivity.class);
                startActivity(intent);
            }
        });


        //完成订单的按钮
        RadioButton order_manage_finish =findViewById(R.id.order_manage_finish);
        order_manage_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BusinessMyActivity.this,OrderFinishActivity.class);
                startActivity(intent);
            }
        });








    }




}