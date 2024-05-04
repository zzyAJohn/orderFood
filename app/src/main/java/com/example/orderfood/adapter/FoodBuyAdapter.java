package com.example.orderfood.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.orderfood.R;
import com.example.orderfood.activity.UpdateGoodActivity;
import com.example.orderfood.bean.FoodBean;
import com.example.orderfood.dao.UserDao;
import com.example.orderfood.user.activity.BuyFoodActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FoodBuyAdapter extends ArrayAdapter<FoodBean> {


    private ArrayList<FoodBean> list;
    private  Context context;

    public FoodBuyAdapter(@NonNull Context context, ArrayList<FoodBean> list) {
        super(context, R.layout.buy_food_list, list);
        this.list=list;
        this.context=context;

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Log.d("FoodAdapter", "getView called for position: " + position);
        if(convertView==null){

            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.buy_food_list,parent,false);
        }

        TextView name= convertView.findViewById(R.id.food_lis_sale_name);//价格
        TextView sale= convertView.findViewById(R.id.food_lis_sale_num);//销售数量
        TextView price= convertView.findViewById(R.id.food_list_price);//销售数量
        TextView des= convertView.findViewById(R.id.food_list_des);//描述
        ImageView img= convertView.findViewById(R.id.food_list_img);//图片


        FoodBean foodBean=list.get(position);
        name.setText(foodBean.getS_food_name());

       String mouthNum=UserDao.getMonthlySales(foodBean.getS_food_id());

        sale.setText("月售："+mouthNum+" 份");
        price.setText("价格："+foodBean.getS_food_price()+" 元");
        des.setText("描述："+foodBean.getS_food_des());

///storage/emulated/0/image/mlt.png
        // 使用BitmapFactory从文件加载图片
        Bitmap bitmap = BitmapFactory.decodeFile(foodBean.getS_food_img());
        // 将加载的图像设置到ImageView中
        img.setImageBitmap(bitmap);


        ImageView add= convertView.findViewById(R.id.food_add);//减少
        ImageView no= convertView.findViewById(R.id.food_no_add);//减少
        TextView num= convertView.findViewById(R.id.food_num_add);//减少


        //
        //

      //  TextView sum= convertView.findViewById(R.id.buy_food_sum_one);//减少
        View view = ((Activity) context).findViewById(R.id.buy_food_sum_one);
        TextView sum = (TextView) view;

        TextView data = ((Activity) context).findViewById(R.id.buy_food_sum_two);



        JSONObject jsonObject=new JSONObject();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num1=Integer.parseInt(num.getText().toString());
                num1=num1+1;
                num.setText(String.valueOf(num1));

                //时刻更新价格

                double price=Double.valueOf(foodBean.getS_food_price());
                double cum=Double.parseDouble(sum.getText().toString())+price;//当前价格
                double roundedSu = Math.round(cum * 100.0) / 100.0;
                sum.setText(String.valueOf(roundedSu));


                String temp=data.getText().toString();//获取到这个文本的内容
                if(temp.isEmpty()){
                    try {
                        jsonObject.put(foodBean.getS_food_id(),String.valueOf(num1));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    data.setText(jsonObject.toString());

                }else{

                    try {
                        JSONObject jsonObject1=new JSONObject(temp);
                        jsonObject1.put(foodBean.getS_food_id(),String.valueOf(num1));
                        data.setText(jsonObject1.toString());

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }







            }
        });


        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int num1=Integer.parseInt(num.getText().toString());
                if(num1!=0){
                    num1=num1-1;
                    num.setText(String.valueOf(num1));
                    double price=Double.valueOf(foodBean.getS_food_price());
                    double cum=Double.parseDouble(sum.getText().toString())-price;//当前价格
                    double roundedSu = Math.round(cum * 100.0) / 100.0;
                    sum.setText(String.valueOf(roundedSu));


                    String temp=data.getText().toString();//获取到这个文本的内容
                    if(temp.isEmpty()){
                        try {
                            jsonObject.put(foodBean.getS_food_id(),String.valueOf(num1));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        data.setText(jsonObject.toString());

                    }else{

                        try {
                            JSONObject jsonObject1=new JSONObject(temp);
                            jsonObject1.put(foodBean.getS_food_id(),String.valueOf(num1));
                            data.setText(jsonObject1.toString());

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }



            }
        });
        //text     1 10- 2,10





        return convertView;
    }
}
