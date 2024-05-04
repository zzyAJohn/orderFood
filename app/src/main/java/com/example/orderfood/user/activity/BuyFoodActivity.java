package com.example.orderfood.user.activity;
import com.example.orderfood.adapter.AddressAdapter;
import com.example.orderfood.bean.AddressBean;
import com.example.orderfood.bean.CommentBean;
import com.example.orderfood.bean.UserBean;
import com.example.orderfood.dao.AddressDao;
import com.example.orderfood.dao.CommentDao;
import com.example.orderfood.dao.OrderDao;
import com.example.orderfood.user.adapter.BuyFoodAdapter;
import com.example.orderfood.user.adapter.CommentUserAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.orderfood.MainActivity;
import com.example.orderfood.R;
import com.example.orderfood.adapter.FoodBuyAdapter;
import com.example.orderfood.bean.BusinessBean;
import com.example.orderfood.bean.FoodBean;
import com.example.orderfood.dao.FoodDao;
import com.example.orderfood.dao.UserDao;
import com.example.orderfood.tools.Tools;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class BuyFoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_food);

        SharedPreferences sharedPreferences=getSharedPreferences("data", Context.MODE_PRIVATE);
        String account=sharedPreferences.getString("account","");



        Intent intent=getIntent();
       String business= intent.getStringExtra("business");
        BusinessBean user = UserDao.getBusinessUser(business);

        ImageView imageView =findViewById(R.id.user_food_business_tx_one);
        Tools.showImage(imageView ,user.getS_img(),this);


        TextView name =findViewById(R.id.user_food_business_name);
        name.setText(user.getS_name());//名字

        TextView con =findViewById(R.id.user_food_business_con);
        con.setText(user.getS_describe());//简介

        //返回功能
        Toolbar toolbar=findViewById(R.id.buy_toolbar_one);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });//上面实现一个返回的功能


        //实现选项卡功能

     TextView oneTap=   findViewById(R.id.buy_dc_one);
       View oneTapT=   findViewById(R.id.buy_t_one);





        TextView oneTap1=   findViewById(R.id.buy_dc_two);
        View oneTapT1=   findViewById(R.id.buy_t_two);

        ListView oneList = findViewById(R.id.buy_list_one);
        ListView  twoList = findViewById(R.id.buy_list_two);
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
                if(comment!=null&&comment.size()>0){
                    CommentUserAdapter commentUserAdapter=new CommentUserAdapter(BuyFoodActivity.this,comment);
                    twoList.setAdapter(commentUserAdapter);
                }else{
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
        ListView listView =findViewById(R.id.buy_list_one);//加载食物信息
    //查询该商家的信息

        ArrayList<FoodBean> list = UserDao.getAllFood(business);
        if(list!=null&&list.size()>0){
            FoodBuyAdapter adapter = new FoodBuyAdapter(this, list);
            listView.setAdapter(adapter);
        }else{
            listView.setAdapter(null);
        }
        //加载框



        TextView sum =findViewById(R.id.buy_food_sum_one);

        TextView two =findViewById(R.id.buy_food_sum_two);


        Button button=findViewById(R.id.buy_food_js);//结算
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numS=sum.getText().toString();

               double numSL=Double.parseDouble(numS);
               if(numSL==0){
                   Toast.makeText(BuyFoodActivity.this, "未选择商品无法结算", Toast.LENGTH_SHORT).show();
               }else{

                   //这个是从底部向上弹出来框
                   View bottomSheetLayout = getLayoutInflater().inflate(R.layout.bottom_sheet, null);//找到布局文件
                   BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(BuyFoodActivity.this);
                   bottomSheetDialog.setContentView(bottomSheetLayout);
                   bottomSheetDialog.show();


                    ListView listView1=bottomSheetLayout.findViewById(R.id.user_address);//先生收货地址

                   List<AddressBean> listAddrest = AddressDao.getAllAddress(account);
                   if(listAddrest!=null&&listAddrest.size()>0){
                       AddressAdapter adapter=new AddressAdapter(BuyFoodActivity.this,listAddrest,bottomSheetLayout);
                       listView1.setAdapter(adapter);
                   }else{
                       listView1.setAdapter(null);
                   }


                   //禁用这个收拾

                   BottomSheetBehavior<View> behavior = BottomSheetBehavior.from((View) bottomSheetLayout.getParent());
                   behavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                       @Override
                       public void onStateChanged(@NonNull View bottomSheet, int newState) {
                           // 在状态改变时检查是否需要拦截
                           if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                               // 禁用下滑手势
                               behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                           }
                       }

                       @Override
                       public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                       }
                   });


                   //加载第二个列表内容

                   try {
                       JSONObject jsonObject=new JSONObject(two.getText().toString());//得到食物ID和对应的数量
                       Iterator<String> keys = jsonObject.keys();
                       List foodList=new ArrayList();
                       while (keys.hasNext()) {
                           String foodId = keys.next();//获取到食物的ID
                           FoodBean foodBean = FoodDao.getFoodByID(foodId);

                           int  a=Integer.valueOf(jsonObject.getString(foodId));
                           if(a>0){
                               foodBean.setNum(jsonObject.getString(foodId));
                               foodList.add(foodBean);
                           }




                       }

                       ListView listView2=bottomSheetLayout.findViewById(R.id.user_food);//食物地址
                       if(foodList!=null&&foodList.size()>0){
                           BuyFoodAdapter adapter=new BuyFoodAdapter(BuyFoodActivity.this,foodList);
                           listView2.setAdapter(adapter);
                       }else{
                           listView2.setAdapter(null);
                       }

                       //先生头像，与昵称，和订单时间
                       //获取当前时间
                       String time=Tools.getCurrentTime();
                       ImageView img=bottomSheetLayout.findViewById(R.id.business_order_tx_list);
                       TextView name=bottomSheetLayout.findViewById(R.id.business_order_user_name);
                       TextView times=bottomSheetLayout.findViewById(R.id.business_order_user_time);

                       UserBean userD = UserDao.getUser(account);
                       Tools.showImage(img,userD.getS_img(),bottomSheetLayout.getContext());
                       name.setText(userD.getS_name());
                       times.setText(time);

                       //将总计的值进行添加过去

                       TextView pris=bottomSheetLayout.findViewById(R.id.order_sum_price);
                        pris.setText(numS);

                       TextView sta=bottomSheetLayout.findViewById(R.id.order_sta);
                       sta.setText("等待买家付款");


                   } catch (JSONException e) {
                       throw new RuntimeException(e);
                   }



                   //关闭当前界面
                   Button button1=bottomSheetLayout.findViewById(R.id.cancel_order_m);
                   button1.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           bottomSheetDialog.dismiss();
                       }
                   });

                   Button button2=bottomSheetLayout.findViewById(R.id.finish_order_m);
                   button2.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {

                           //价格呢订单信息添加到到里面
                           UserBean userDD = UserDao.getUser(account);


                           String s_order_id=Tools.getUUID();
                           String s_order_time=Tools.getCurrentTime();
                           String s_order_user_id=account;
                           String s_order_user_name=userDD.getS_name();
                           String s_order_user_img=userDD.getS_img();


                           String s_order_business=user.getS_id();
                           String s_order_business_name=user.getS_name();
                           String s_order_business_img=user.getS_img();

                           String s_order_business_id=Tools.getUUID();

                           String s_order_sta="1";

                           String s_order_adress="1";


                          TextView peo= bottomSheetLayout.findViewById(R.id.order_accept_text);
                           TextView address =bottomSheetLayout.findViewById(R.id.order_Address_text);
                           TextView phone =bottomSheetLayout.findViewById(R.id.order_phone_text);
                           if(peo.getText().toString().isEmpty()){

                               Snackbar.make(bottomSheetDialog.getWindow().getDecorView(), "请选择收货地址，没有请添加", Snackbar.LENGTH_SHORT).show();


                           }else{
                               s_order_adress=peo.getText().toString()+"-"+address.getText().toString()+"-"+phone.getText().toString();

                           }

                           if(!s_order_adress.equals("1")){


                              int a= OrderDao.addOrder(s_order_id,s_order_time,s_order_user_id,s_order_user_name,s_order_user_img,s_order_business,s_order_business_name,
                                       s_order_business_img,s_order_business_id,s_order_sta,s_order_adress);










                              if(a==1){

                                  try {
                                      JSONObject jsonObject=  new JSONObject(two.getText().toString());
                                      Iterator<String> keys = jsonObject.keys();
                                      while (keys.hasNext()) {
                                          String foodId = keys.next();//获取到食物的ID
                                          FoodBean foodBean = FoodDao.getFoodByID(foodId);
                                          foodBean.setNum(jsonObject.getString(foodId));

                                            String s_food_id=foodBean.getS_food_id();
                                            String s_food_name=foodBean.getS_food_name();
                                            String s_food_des=foodBean.getS_food_des();
                                          String s_food_price=foodBean.getS_food_price();
                                          String s_food_img=foodBean.getS_food_img();
                                          String s_food_time=s_order_time;
                                          String num=jsonObject.getString(foodId);

                                          OrderDao.addOrderDetail(s_order_business_id,s_food_id,s_food_name,s_food_des,s_food_price,s_food_img,s_food_time, num);

                                      }
                                  } catch (JSONException e) {
                                      throw new RuntimeException(e);
                                  }
                                  Toast.makeText(BuyFoodActivity.this, "购买成功", Toast.LENGTH_SHORT).show();

                                  bottomSheetDialog.dismiss();

                              }else{
                                  Toast.makeText(BuyFoodActivity.this, "购买失败", Toast.LENGTH_SHORT).show();
                                  bottomSheetDialog.dismiss();
                              }

                              }

                       }
                   });















//
//



               }


            }
        });






    }
}