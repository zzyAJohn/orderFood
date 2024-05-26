package com.example.orderfood.admin.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.example.orderfood.R;
import com.example.orderfood.adapter.AddressAdapter;
import com.example.orderfood.adapter.FoodBuyAdapter;
import com.example.orderfood.adapter.FoodDeleteAdapter;
import com.example.orderfood.bean.AddressBean;
import com.example.orderfood.bean.BusinessBean;
import com.example.orderfood.bean.CommentBean;
import com.example.orderfood.bean.FoodBean;
import com.example.orderfood.bean.UserBean;
import com.example.orderfood.dao.AddressDao;
import com.example.orderfood.dao.CommentDao;
import com.example.orderfood.dao.FoodDao;
import com.example.orderfood.dao.OrderDao;
import com.example.orderfood.dao.UserDao;
import com.example.orderfood.tools.Tools;
import com.example.orderfood.user.activity.BuyFoodActivity;
import com.example.orderfood.user.adapter.BuyFoodAdapter;
import com.example.orderfood.user.adapter.CommentUserAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AdminFoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_food);

        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        String account = sharedPreferences.getString("account", "");


        Intent intent = getIntent();
        String business = intent.getStringExtra("business");
        BusinessBean user = UserDao.getBusinessUser(business);

        ImageView imageView = findViewById(R.id.user_food_business_tx_one);
        Tools.showImage(imageView, user.getS_img(), this);


        TextView name = findViewById(R.id.user_food_business_name);
        name.setText(user.getS_name());//名字

        TextView con = findViewById(R.id.user_food_business_con);
        con.setText(user.getS_describe());//简介

        //返回功能
        Toolbar toolbar = findViewById(R.id.buy_toolbar_one);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });//上面实现一个返回的功能


        //实现选项卡功能
        //点餐文字
        TextView oneTap = findViewById(R.id.buy_dc_one);
        //点餐下的光标
        View oneTapT = findViewById(R.id.buy_t_one);

        //评论文字
        TextView oneTap1 = findViewById(R.id.buy_dc_two);
        //评论下的光标
        View oneTapT1 = findViewById(R.id.buy_t_two);

        ListView oneList = findViewById(R.id.buy_list_one);
        ListView twoList = findViewById(R.id.buy_list_two);
        oneTap1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //让自己的颜色加粗
                oneTap1.setTypeface(null, Typeface.BOLD);
                oneTap.setTypeface(null, Typeface.NORMAL);
                oneTapT1.setVisibility(View.VISIBLE);
                oneTapT.setVisibility(View.INVISIBLE);


                oneList.setVisibility(View.GONE);
                twoList.setVisibility(View.VISIBLE);

                //点到评论才加载
                //twoList
                List<CommentBean> comment = CommentDao.getAllComment(business);
                if (comment != null && comment.size() > 0) {
                    CommentUserAdapter commentUserAdapter = new CommentUserAdapter(AdminFoodActivity.this, comment);
                    twoList.setAdapter(commentUserAdapter);
                } else {
                    twoList.setAdapter(null);
                }


            }
        });
        ;

        //点击食物
        oneTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //让自己的颜色加粗
                oneTap.setTypeface(null, Typeface.BOLD);
                oneTap1.setTypeface(null, Typeface.NORMAL);

                oneTapT.setVisibility(View.VISIBLE);
                oneTapT1.setVisibility(View.INVISIBLE);


                oneList.setVisibility(View.VISIBLE);
                twoList.setVisibility(View.GONE);

            }
        });


        //加载食物的信息
        ListView listView = findViewById(R.id.buy_list_one);//加载食物信息

        //查询该商家的信息
        ArrayList<FoodBean> list = UserDao.getAllFood(business);
        if (list != null && list.size() > 0) {
            FoodDeleteAdapter adapter = new FoodDeleteAdapter(this, list);
            listView.setAdapter(adapter);
        } else {
            listView.setAdapter(null);
        }

    }
}