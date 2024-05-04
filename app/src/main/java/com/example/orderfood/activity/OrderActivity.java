package com.example.orderfood.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import com.example.orderfood.R;
import com.example.orderfood.adapter.OrderBusinessAdapter;
import com.example.orderfood.bean.OrderBean;
import com.example.orderfood.dao.OrderDao;

import java.util.List;

/**
 * 这个是商家的订单管理
 */
public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //这个是商家的订单界面 必须先知道这个商家谁家

        //通过按钮的点击时间实现修改密码update_business_pwd_button
        SharedPreferences sharedPreferences=getSharedPreferences("data", Context.MODE_PRIVATE);
        String account=sharedPreferences.getString("account","");//这里面的account是代表这个商家的用户


        ListView listView=findViewById(R.id.business_list_view_order);//加载动态列表
        List<OrderBean> list = OrderDao.getAllOrderByBusiness(account);
       if(list!=null&&list.size()>0){
           OrderBusinessAdapter adapter = new OrderBusinessAdapter(this, list);
           listView.setAdapter(adapter);
       }else{
           listView.setAdapter(null);
       }
        //接下来实现查询
        //import androidx.appcompat.widget.SearchView;
       SearchView search=findViewById(R.id.business_search_search);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                List<OrderBean> list = OrderDao.getAllOrderByBusiness(account,s);
                if(list!=null&&list.size()>0){
                    OrderBusinessAdapter adapter = new OrderBusinessAdapter(OrderActivity.this, list);
                    listView.setAdapter(adapter);
                }else{
                    listView.setAdapter(null);
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<OrderBean> list = OrderDao.getAllOrderByBusiness(account,s);
                if(list!=null&&list.size()>0){
                    OrderBusinessAdapter adapter = new OrderBusinessAdapter(OrderActivity.this, list);
                    listView.setAdapter(adapter);
                }else{
                    listView.setAdapter(null);
                }
                return true;
            }
        });


        Toolbar toolbar =findViewById(R.id.business_toolbar);//加载动态列表

        setSupportActionBar(toolbar);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });





        //通过这个商家的账号查询这个商家有多少订单ID以及状态  我想让第一个的订单，最新的订单显示在最上面


        //接下了我们来实现，订单管理







    }
}