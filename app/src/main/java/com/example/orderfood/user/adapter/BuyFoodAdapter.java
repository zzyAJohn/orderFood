package com.example.orderfood.user.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.orderfood.R;
import com.example.orderfood.bean.FoodBean;
import com.example.orderfood.tools.Tools;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BuyFoodAdapter extends ArrayAdapter<FoodBean> {


    private List<FoodBean> list;


    public BuyFoodAdapter(@NonNull Context context, List<FoodBean> list) {
        super(context, R.layout.address_list_food, list);
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
            convertView=inflater.inflate(R.layout.address_list_food,parent,false);
        }

        FoodBean temp=list.get(position);//和获取到食物信息
   // 食物图片
        TextView name= convertView.findViewById(R.id.food_name);
        name.setText(temp.getS_food_name());


        TextView num= convertView.findViewById(R.id.food_num);
        num.setText(temp.getNum()+" 份");
        ImageView imageView=convertView.findViewById(R.id.business_order_sw);
        Tools.showImage(imageView,temp.getS_food_img(),getContext());



        float numS=Float.parseFloat(temp.getNum());//数量

        float pri= Tools.decimalToTwo(Float.parseFloat(temp.getS_food_price()));//价格

        Float sumPrice=numS*pri;
        Float sumPriceT = Tools.decimalToTwo(sumPrice);

        TextView price=convertView.findViewById(R.id.food_sum);
        price.setText("￥ "+String.valueOf(sumPriceT));





        return convertView;
    }
}
