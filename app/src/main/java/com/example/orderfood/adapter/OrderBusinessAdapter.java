package com.example.orderfood.adapter;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.orderfood.R;
import com.example.orderfood.activity.OrderActivity;
import com.example.orderfood.activity.UpdateGoodActivity;
import com.example.orderfood.bean.OrderBean;
import com.example.orderfood.bean.OrderDetail;
import com.example.orderfood.dao.OrderDao;
import com.example.orderfood.dao.UserDao;
import com.example.orderfood.tools.Tools;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderBusinessAdapter extends ArrayAdapter<OrderBean> {


    private List<OrderBean> list;


    public OrderBusinessAdapter(@NonNull Context context, List<OrderBean> list) {
        super(context, R.layout.business_order_list, list);
        this.list=list;
    }


    public void remove(int position) {
        list.remove(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Log.d("FoodAdapter", "getView called for position: " + position);
        if(convertView==null){

            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.business_order_list,parent,false);
        }

        OrderBean orderBean=list.get(position);

        ImageView tx=convertView.findViewById(R.id.business_order_tx_list);//获取头像
        TextView userName=convertView.findViewById(R.id.business_order_user_name);//获取头像
        TextView time=convertView.findViewById(R.id.business_order_user_time);//
        TextView ad=convertView.findViewById(R.id.order_Address_text);//收获地址
        TextView phone=convertView.findViewById(R.id. order_phone_text);//联系方式

        TextView peo=convertView.findViewById(R.id.order_accept_text);//联系方式



        String address=orderBean.getS_order_address();//"姓名-地址-联系凡是"
        String[] addressS = address.split("-");//定义一个存放地址数组

        //实现显示图片
        Tools.showImage(tx,orderBean.getS_order_user_img(),getContext());
        userName.setText(orderBean.getS_order_user_name());
        time.setText(orderBean.getS_order_time());
        peo.setText(addressS[0]);
        ad.setText(addressS[1]);
        phone.setText(addressS[2]);

        //加载子布局动态添加到新的布局当中

         List<OrderDetail>detailList = orderBean.getDetailList();
        LinearLayout sco=convertView.findViewById(R.id.order_detail_scroll);//要添加的地方
        sco.removeAllViews();
        float sum= 0.0F;//总价格
         for(int i=0;i<detailList.size();i++){
             OrderDetail temp = detailList.get(i);
             View newLayout = LayoutInflater.from(getContext()).inflate(R.layout.business_order_list_chuild, null);
             ImageView imageView=newLayout.findViewById(R.id.business_order_sw);
             Tools.showImage(imageView,temp.getS_food_img(),getContext());

             //食物图片
                TextView name= newLayout.findViewById(R.id.business_order_food_name);
             name.setText(temp.getS_food_name());

             //食物数量
             TextView num= newLayout.findViewById(R.id. business_order_food_num);
            num.setText(temp.getS_food_num()+" 份");



            float numS=Float.parseFloat(temp.getS_food_num());//数量

            float pri= Tools.decimalToTwo(Float.parseFloat(temp.getS_food_price()));//价格

            Float sumPrice=numS*pri;
             Float sumPriceT = Tools.decimalToTwo(sumPrice);
             sum=sum+sumPriceT;
            //价格
             TextView price= newLayout.findViewById(R.id. business_order_food_price);
             price.setText("￥ "+String.valueOf(sumPriceT));

             sco.addView(newLayout);
         }

         //总价格
        TextView su=convertView.findViewById(R.id.order_sum_price);//联系方式

        Float sumF=Tools.decimalToTwo(sum);
        su.setText("￥ "+String.valueOf(sumF));

        SharedPreferences sharedPreferences=getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        String account=sharedPreferences.getString("account","");



        Button button =convertView.findViewById(R.id.cancel_order_m);//取消顶订单
        Button buttonOne =convertView.findViewById(R.id.finish_order_m);//完成订单
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderDao.updateOrder( orderBean.getS_order_id(),"2");

               // list = OrderDao.getAllOrderByBusiness(account);
                remove(position);
                notifyDataSetChanged();



            }
        });

        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderDao.updateOrder( orderBean.getS_order_id(),"3");
                remove(position);
                notifyDataSetChanged();
            }
        });



        TextView sta=convertView.findViewById(R.id.order_sta);
        if(orderBean.getS_order_sta().equals("1")){
            sta.setText("订单处理中");
        }
        if(orderBean.getS_order_sta().equals("2")){
            sta.setText("订单已取消");
        }
        if(orderBean.getS_order_sta().equals("3")){
            sta.setText("订单已完成");
        }

//        UUID id=UUID.randomUUID();//"aaa-aaaa"
//        String uuid= id.toString().replace("-","");
//        Bitmap bitmap1=((BitmapDrawable)tx.getDrawable()).getBitmap();//获取图片的map
//        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();  // 将Bitmap转换为字节数组
//        bitmap1.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
//        byte[] imageByteArray = byteArrayOutputStream.toByteArray();// 执行插入操作
//        String klmPath= Tools.getImagePath()+"/"+uuid+"A.png";//先将图片保存到磁盘，然后只保留路径
//        Tools.saveByteArrayAsPng(imageByteArray,klmPath,getContext());


        return convertView;
    }
}
