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

import com.example.orderfood.MainActivity;
import com.example.orderfood.R;
import com.example.orderfood.activity.UpdateGoodActivity;
import com.example.orderfood.bean.BusinessBean;
import com.example.orderfood.bean.CommentBean;
import com.example.orderfood.bean.FoodBean;
import com.example.orderfood.dao.CommentDao;
import com.example.orderfood.dao.UserDao;
import com.example.orderfood.tools.Tools;
import com.example.orderfood.user.activity.BuyFoodActivity;
import com.example.orderfood.user.activity.UserManageActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class UserFoodAdapter extends ArrayAdapter<FoodBean> {


    private ArrayList<FoodBean> list;


    public UserFoodAdapter(@NonNull Context context, ArrayList<FoodBean> list) {
        super(context, R.layout.food_list_user, list);
        this.list=list;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Log.d("FoodAdapter", "getView called for position: " + position);
        if(convertView==null){

            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.food_list_user,parent,false);
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




        ImageView tx= convertView.findViewById(R.id.user_food_business_tx);//图片
        String sjid=foodBean.getS_business_id();//商家ID，根据商家ID查询商家信息
        BusinessBean sj = UserDao.getBusinessUser(sjid);


        Tools.showImage(tx,sj.getS_img(),getContext());

        TextView na= convertView.findViewById(R.id.user_food_business_name);
        na.setText(sj.getS_name());//设置商家名称

        List<CommentBean> come = CommentDao.getAllComment(sjid);//获取所有评论

        float pjf=0;
        if(come!=null&&come.size()>0){
            int sum=0;
            for (CommentBean commentBean : come) {
                sum=sum+Integer.parseInt(commentBean.getS_comment_score());
            }

            pjf=sum/come.size();

            DecimalFormat df = new DecimalFormat("#.0");
            String pjfStr = df.format(pjf); //格式化pjf
            pjf = Float.parseFloat(pjfStr); //转换回float类型
        }
        TextView pf= convertView.findViewById(R.id.user_food_business_pf);
        pf.setText(String.valueOf(pjf)+" 分");//设置商家名称





        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //需要将ID传过去

                Intent intent=new Intent(getContext(), BuyFoodActivity.class);

                intent.putExtra("business",foodBean.getS_business_id());
                getContext().startActivity(intent);//打开目标界面

            }
        });


        return convertView;
    }
}
