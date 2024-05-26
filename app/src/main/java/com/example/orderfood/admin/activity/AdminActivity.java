package com.example.orderfood.admin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.orderfood.R;
import com.example.orderfood.admin.fragment.AdminFoodFragment;
import com.example.orderfood.admin.fragment.AdminMyFragment;
import com.example.orderfood.admin.fragment.AdminOrderFragment;
import com.example.orderfood.user.activity.fragment.HomeFragment;
import com.example.orderfood.user.activity.fragment.HomeNoFinishFragment;
import com.example.orderfood.user.activity.fragment.UserMyFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Intent intent=getIntent();
        String sta_admin= intent.getStringExtra("sta_admin");
        if(sta_admin!=null){
            FragmentTransaction transaction1 = fragmentManager.beginTransaction();
            transaction1.replace(R.id.fragment_container_admin, new AdminMyFragment());
            transaction1.commit();
        }else{
            //加载新或者frament

            //处理下拉菜单
            FragmentTransaction transaction1 = fragmentManager.beginTransaction();
            transaction1.replace(R.id.fragment_container_admin, new AdminFoodFragment());
            transaction1.commit();
        }



        //查找底部菜单
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_view_admin);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                int id=item.getItemId();
                if(id==R.id.navigation_food_admin){
                    transaction.replace(R.id.fragment_container_admin, new AdminFoodFragment());
                }
                if(id==R.id.navigation_order_admin){
                    transaction.replace(R.id.fragment_container_admin, new AdminOrderFragment());
                }
                if(id==R.id.navigation_notifications_admin){
                    transaction.replace(R.id.fragment_container_admin, new AdminMyFragment());
                }

                transaction.commitNow();
                return true;


            }
        });
    }



}