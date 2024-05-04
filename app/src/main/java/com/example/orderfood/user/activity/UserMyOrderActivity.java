package com.example.orderfood.user.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;


import com.example.orderfood.R;

import com.example.orderfood.bean.OrderBean;
import com.example.orderfood.dao.OrderDao;
import com.example.orderfood.user.adapter.UserFinishAdapter;

import java.util.List;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

public class UserMyOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_my_order);

        SharedPreferences sharedPreferences=getSharedPreferences("data", Context.MODE_PRIVATE);
        String account=sharedPreferences.getString("account","");

       Toolbar toolbar = findViewById(R.id.add_business_toolbar_one);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


       ListView listView= findViewById(R.id.user_list_view);
        List<OrderBean> list = OrderDao.getAllOrderUserFinish(account);
        if(list!=null&&list.size()>0){
            UserFinishAdapter adapter=new UserFinishAdapter(this,list);
            listView.setAdapter(adapter);
        }else{
            listView.setAdapter(null);
        }


        SearchView search=findViewById(R.id.user_search_search);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                if(s.equals("")){
                    List<OrderBean> list = OrderDao.getAllOrderUserFinish(account);
                    if(list!=null&&list.size()>0){
                        UserFinishAdapter adapter=new UserFinishAdapter(UserMyOrderActivity.this,list);
                        listView.setAdapter(adapter);
                    }else{
                        listView.setAdapter(null);
                    }
                }else{
                    List<OrderBean> list = OrderDao.getAllOrderUserFinish(account,s);
                    if(list!=null&&list.size()>0){
                        UserFinishAdapter adapter=new UserFinishAdapter(UserMyOrderActivity.this,list);
                        listView.setAdapter(adapter);
                    }else{
                        listView.setAdapter(null);
                    }

                }



                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                if(s.equals("")){
                    List<OrderBean> list = OrderDao.getAllOrderUserFinish(account);
                    if(list!=null&&list.size()>0){
                        UserFinishAdapter adapter=new UserFinishAdapter(UserMyOrderActivity.this,list);
                        listView.setAdapter(adapter);
                    }else{
                        listView.setAdapter(null);
                    }
                }else{
                    List<OrderBean> list = OrderDao.getAllOrderUserFinish(account,s);
                    if(list!=null&&list.size()>0){
                        UserFinishAdapter adapter=new UserFinishAdapter(UserMyOrderActivity.this,list);
                        listView.setAdapter(adapter);
                    }else{
                        listView.setAdapter(null);
                    }

                }
                return true;
            }
        });

    }
}