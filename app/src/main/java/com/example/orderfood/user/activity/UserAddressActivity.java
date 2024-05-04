package com.example.orderfood.user.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;


import com.example.orderfood.R;
import com.example.orderfood.adapter.AddressUserAdapter;
import com.example.orderfood.bean.AddressBean;
import com.example.orderfood.dao.AddressDao;
import com.example.orderfood.user.activity.comment.UpdateAddressActivity;
import com.example.orderfood.user.activity.fragment.UserMyFragment;

import java.util.List;

public class UserAddressActivity extends AppCompatActivity {

    /**
     * 这个显示收货 地址的内容
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_address);


        //实现返回功能

        SharedPreferences sharedPreferences=getSharedPreferences("data", Context.MODE_PRIVATE);
        String account=sharedPreferences.getString("account","");


        ListView listView = findViewById(R.id.address_list);
        List<AddressBean> list = AddressDao.getAllAddress(account);
        if(list!=null&&list.size()>0){
            AddressUserAdapter adapter=new AddressUserAdapter(this,list);
            listView.setAdapter(adapter);
        }else{
            listView.setAdapter(null);
        }



        Toolbar toolbar = findViewById(R.id.address_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserAddressActivity.this,UserManageActivity.class);
                intent.putExtra("sta","a");
                startActivity(intent);
                //finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_address, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.add_address_menu){

            Intent intent=new Intent(UserAddressActivity.this, UpdateAddressActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);


    }
}