package com.example.orderfood.admin.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.orderfood.R;
import com.example.orderfood.activity.BusinessActivity;
import com.example.orderfood.activity.UpdateBusinessMesActivity;
import com.example.orderfood.activity.UpdateFoodActivity;
import com.example.orderfood.admin.activity.AdminFoodActivity;
import com.example.orderfood.bean.FoodBean;
import com.example.orderfood.dao.FoodDao;
import com.example.orderfood.dao.UserDao;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FoodDeleteAdapter extends ArrayAdapter<FoodBean> {


    private ArrayList<FoodBean> list;
    private Context context;

    public FoodDeleteAdapter(@NonNull Context context, ArrayList<FoodBean> list) {
        super(context, R.layout.delete_food_list, list);
        this.list=list;
        this.context=context;

    }

    public void remove(int position) {
        list.remove(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Log.d("FoodAdapter", "getView called for position: " + position);
        if(convertView==null){

            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.delete_food_list,parent,false);
        }

        TextView name= convertView.findViewById(R.id.food_lis_sale_name);//价格
        TextView sale= convertView.findViewById(R.id.food_lis_sale_num);//销售数量
        TextView price= convertView.findViewById(R.id.food_list_price);//销售数量
        TextView des= convertView.findViewById(R.id.food_list_des);//描述
        ImageView img= convertView.findViewById(R.id.food_list_img);//图片


        FoodBean foodBean=list.get(position);
        name.setText(foodBean.getS_food_name());

        String mouthNum= UserDao.getMonthlySales(foodBean.getS_food_id());

        sale.setText("月售："+mouthNum+" 份");
        price.setText("价格："+foodBean.getS_food_price()+" 元");
        des.setText("描述："+foodBean.getS_food_des());

///storage/emulated/0/image/mlt.png
        // 使用BitmapFactory从文件加载图片
        Bitmap bitmap = BitmapFactory.decodeFile(foodBean.getS_food_img());
        // 将加载的图像设置到ImageView中
        img.setImageBitmap(bitmap);

//


        Button btDeleteFood= convertView.findViewById(R.id.bt_delete_food);//下架食物

        JSONObject jsonObject=new JSONObject();



        // 管理员下架商品
        btDeleteFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("确认要删除吗？");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 用户点击确认按钮后执行删除操作
                        FoodDao.delFood(foodBean.getS_food_id());
                        remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(context, "下架成功！", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 用户点击取消按钮，不执行任何操作
                    }
                });
                builder.show();
            }
        });

        return convertView;
    }
}
