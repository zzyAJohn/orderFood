package com.example.orderfood.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.orderfood.R;
import com.example.orderfood.adapter.FoodAdapter;
import com.example.orderfood.bean.FoodBean;
import com.example.orderfood.dao.FoodDao;
import com.example.orderfood.dao.UserDao;
import com.example.orderfood.listener.ButtonColorListener;

import java.util.ArrayList;

public class BusinessActivity extends AppCompatActivity {

    ArrayList<FoodBean> originalItems;
    ListView foodList;

    FoodAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);


        //实现加载食物列表
        foodList = this.findViewById(R.id.business_list_view);//创建一个视图

        //获取当前账号,进行查询
        SharedPreferences sharedPreferences=getSharedPreferences("data", Context.MODE_PRIVATE);
        String account=sharedPreferences.getString("account","");

        originalItems=UserDao.getAllFood(account);//查找到所有数据

        if(originalItems==null||originalItems.size()==0){
            foodList.setAdapter(null);
        }else{
           adapter = new FoodAdapter(this, originalItems);
           foodList.setAdapter(adapter);
        }





        //实现点击按钮的时候设置一下选中的颜色
        Button home= findViewById(R.id.business_home);
        Button add =findViewById(R.id.business_add);
        Button my= findViewById(R.id.business_my);

        home.setOnClickListener(new  ButtonColorListener(home,add,my,1));
        add.setOnClickListener(new  ButtonColorListener(home,add,my,1));
        my.setOnClickListener(new  ButtonColorListener(home,add,my,1));
        home.callOnClick();//模拟点击home

        //点击加号进行跳转界面
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BusinessActivity.this,AddGoodActivity.class);
                startActivity(intent);


            }
        });

        my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(BusinessActivity.this,BusinessMyActivity.class);
                overridePendingTransition(0, 0);
                startActivity(intent);

            }
        });




        EditText search= findViewById(R.id.business_search);//这个是搜索的内容
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //执行搜索
                String name=search.getText().toString();

                originalItems=UserDao.getAllFoodByName(account,name);//查找到所有数据

                if(originalItems==null||originalItems.size()==0){
                    foodList.setAdapter(null);
                }else{
                    adapter = new FoodAdapter(BusinessActivity.this, originalItems);
                    foodList.setAdapter(adapter);
                }
            }
        });






        //实现注册上下文
        registerForContextMenu(foodList);
        //实现上下文菜单，实现删除的功能
        foodList.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                openContextMenu(view);
                return true;
            }
        });









    }



   // onCreateContextMenu

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        //设置要弹出的菜单内容

        // 设置弹出菜单的标题和选项
        getMenuInflater().inflate(R.menu.business_home_menu, menu);
    }

    //设置点击的事件内容

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {


        //获取到菜单的上下文内容
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        //获取点击到内容是什么
        int position = info.position;

        if(item.getItemId()==R.id.business_home_list_menu_delete){
            deleteItem(position);
            return true;
        }else if(item.getItemId()==R.id.business_home_list_menu_update){
            updateItem(position);
            return true;
        }else{
            return super.onContextItemSelected(item);
        }



    }

    /**
     * 删除功能
     * @param position
     */
    private void deleteItem(int position){

        //执行删除的第一部，要从界面上面删掉内容，同时还要丛数据库内容的方面删掉内容
        // 从数据源中删除项
        //实现在数据库层面上进行删除功能
        FoodBean temp = adapter.getItem(position);
      int a=  FoodDao.delFood(temp.getS_food_id());
      if(a==1){
          Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
          adapter.remove(adapter.getItem(position));
          // 通知适配器数据已更改
          adapter.notifyDataSetChanged();
      }else{
          Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
      }

    }

    /**
     * 这个是更改项目
     * @param position
     */

    private void updateItem(int position){

        //执行删除的第一部，要从界面上面删掉内容，同时还要丛数据库内容的方面删掉内容
        // 从数据源中删除项
        //实现在数据库层面上进行删除功能
        FoodBean temp = adapter.getItem(position);
        //需要将ID传过去
        Intent intent=new Intent(BusinessActivity.this,UpdateGoodActivity.class);
        intent.putExtra("id",temp.getS_food_id());
        startActivity(intent);

        //打开更改界面，并且绘制更改界面

//        int a=  FoodDao.delFood(temp.getS_food_id());
//        if(a==1){
//            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
//            adapter.remove(adapter.getItem(position));
//            // 通知适配器数据已更改
//            adapter.notifyDataSetChanged();
//        }else{
//            Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
//        }





    }



}