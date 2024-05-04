package com.example.orderfood.user.activity.comment;

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

import com.example.orderfood.MainActivity;
import com.example.orderfood.R;
import com.example.orderfood.bean.AddressBean;
import com.example.orderfood.dao.AddressDao;
import com.example.orderfood.tools.Tools;
import com.example.orderfood.user.activity.UserAddressActivity;

public class UpdateAddressActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address);


        /**
         * 更改和添加我都想用这一个界面
         */
        Intent intent=getIntent();
        String sta=intent.getStringExtra("sta");

        EditText peo= findViewById(R.id.update_peo_up);
        EditText address= findViewById(R.id.update_address_up);
        EditText phone= findViewById(R.id.update_phone_up);

        Toolbar toolbar = findViewById(R.id.update_address_toolbar);
        Button del= findViewById(R.id.del_button);
        del.setVisibility(View.GONE);


        //实现更改内容
        Button up = findViewById(R.id.update_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(UpdateAddressActivity.this, UserAddressActivity.class);
                startActivity(intent1);
            }
        });

        SharedPreferences sharedPreferences=getSharedPreferences("data", Context.MODE_PRIVATE);
        String account=sharedPreferences.getString("account","");

        if(sta!=null&&sta.equals("1")){//代表更改
            String id=intent.getStringExtra("id");
            AddressBean adds = AddressDao.getAllAddressById(id);
            peo.setText(adds.getS_user_name());
            address.setText(adds.getS_user_address());
            phone.setText(adds.getS_phone());
            up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String peoT=peo.getText().toString();
                    String addressT=address.getText().toString();
                    String phoneT=phone.getText().toString();

                    if(peoT.isEmpty()){
                        peo.setError("请输入收货人");
                    }else if(addressT.isEmpty()){
                        address.setError("请输入收货地址");
                    }else if(phoneT.isEmpty()){
                        phone.setError("请输入联系方式");
                    }else{

                        int a=AddressDao.uplAddress(id,peoT,addressT,phoneT);
                        if(a==1){
                            Toast.makeText(UpdateAddressActivity.this, "更改成功", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(UpdateAddressActivity.this, "更改失败", Toast.LENGTH_SHORT).show();
                        }



                    }

                }
            });

            del.setVisibility(View.VISIBLE);
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AddressDao.delAddress(id);
                    Intent intent1=new Intent(UpdateAddressActivity.this, UserAddressActivity.class);
                    startActivity(intent1);
                }
            });


        }else{


            toolbar.setTitle("添加我的地址");
            up.setText("添加收货地址");

            up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String peoT=peo.getText().toString();
                    String addressT=address.getText().toString();
                    String phoneT=phone.getText().toString();

                    if(peoT.isEmpty()){
                        peo.setError("请输入收货人");
                    }else if(addressT.isEmpty()){
                        address.setError("请输入收货地址");
                    }else if(phoneT.isEmpty()){
                        phone.setError("请输入联系方式");
                    }else{

                        String idT=Tools.getUUID().substring(0,28);
                        int a=AddressDao.addAddress(idT, account,peoT,addressT,phoneT);
                        if(a==1){
                            Toast.makeText(UpdateAddressActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(UpdateAddressActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                        }

                    }

                }
            });



        }

















    }
}