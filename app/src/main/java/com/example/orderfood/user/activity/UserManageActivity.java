package com.example.orderfood.user.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.orderfood.R;
import com.example.orderfood.user.activity.fragment.HomeFragment;
import com.example.orderfood.user.activity.fragment.HomeNoFinishFragment;
import com.example.orderfood.user.activity.fragment.UserMyFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

/**
 * 用户管理界面
 */
public class UserManageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manage);




        FragmentManager fragmentManager = getSupportFragmentManager();
        Intent intent=getIntent();
       String sta= intent.getStringExtra("sta");
       if(sta!=null){
           FragmentTransaction transaction1 = fragmentManager.beginTransaction();
           transaction1.replace(R.id.fragment_container, new UserMyFragment());
           transaction1.commit();
       }else{
           //加载新或者frament

           //处理下拉菜单
           FragmentTransaction transaction1 = fragmentManager.beginTransaction();
           transaction1.replace(R.id.fragment_container, new HomeFragment());
           transaction1.commit();
       }


        //查找底部菜单
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_view);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                int id=item.getItemId();
                if(id==R.id.navigation_home){
                    transaction.replace(R.id.fragment_container, new HomeFragment());
                }
                if(id==R.id.navigation_dashboard){
                    transaction.replace(R.id.fragment_container, new HomeNoFinishFragment());
                }
                if(id==R.id.navigation_notifications){
                    transaction.replace(R.id.fragment_container, new UserMyFragment());
                }

                transaction.commitNow();
                return true;


            }
        });












    }

    public void exit(){
        finishAffinity();
    }
}