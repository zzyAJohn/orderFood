package com.example.orderfood.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.orderfood.R;
import com.example.orderfood.activity.BusinessActivity;
import com.example.orderfood.activity.UpdateGoodActivity;
import com.example.orderfood.bean.FoodBean;
import com.example.orderfood.dao.UserDao;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends ArrayAdapter<FoodBean> {


    private ArrayList<FoodBean> list;


    public FoodAdapter(@NonNull Context context, ArrayList<FoodBean> list) {
        super(context, R.layout.food_list, list);
        this.list=list;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Log.d("FoodAdapter", "getView called for position: " + position);
        if(convertView==null){

            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.food_list,parent,false);
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





        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //需要将ID传过去
                Intent intent=new Intent(getContext(), UpdateGoodActivity.class);
                intent.putExtra("id",foodBean.getS_food_id());
                getContext().startActivity(intent);//打开目标界面

            }
        });


        return convertView;
    }
}
