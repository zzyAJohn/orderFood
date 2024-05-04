package com.example.orderfood.user.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.orderfood.R;
import com.example.orderfood.bean.CommentBean;
import com.example.orderfood.tools.Tools;

import java.util.List;

public class CommentUserAdapter extends ArrayAdapter<CommentBean> {


    private List<CommentBean> list;


    public CommentUserAdapter(@NonNull Context context, List<CommentBean> list) {
        super(context, R.layout.comment_list, list);
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
            convertView=inflater.inflate(R.layout.comment_list,parent,false);
        }

        CommentBean cm=list.get(position);

        ImageView imageView=convertView.findViewById(R.id.comment_tx);
        Tools.showImage(imageView,cm.getS_comment_user_img(),getContext());//用户的头像

        TextView userName=convertView.findViewById(R.id.comment_user_name);//昵称
        userName.setText(cm.getS_comment_user_name());


        TextView time=convertView.findViewById(R.id.comment_user_time);//时间
        time.setText(cm.getS_comment_time());


        int id[]={R.id.comment_one_a,R.id.comment_one_b,R.id.comment_one_c,R.id.comment_one_d,R.id.comment_one_e};//5个星星的ID

        int score=Integer.parseInt(cm.getS_comment_score());
        for(int i=0;i<score;i++){//正向的凡是

           RadioButton temp= convertView.findViewById(id[i]);
           Bitmap bitmap= BitmapFactory.decodeResource(convertView.getResources(),R.drawable.xx);
            BitmapDrawable bitmapDrawable = new BitmapDrawable(convertView.getResources(), bitmap);
            temp.setBackground(bitmapDrawable);
        }
        for(int i=score;i<5;i++){
            RadioButton temp= convertView.findViewById(id[i]);
            Bitmap bitmap= BitmapFactory.decodeResource(convertView.getResources(),R.drawable.wxx);
            BitmapDrawable bitmapDrawable = new BitmapDrawable(convertView.getResources(), bitmap);
            temp.setBackground(bitmapDrawable);
        }

        //星代表的内容
        String nr[]={"非常差","差","一般","满意","非常满意"};
       TextView nrT= convertView.findViewById(R.id.comment_one_f);
        nrT.setText(nr[score-1]);

        //一个显示图片，一共显示内容
       TextView con= convertView.findViewById(R.id.comment_con);
        con.setText(cm.getS_comment_contenct());
       ImageView img= convertView.findViewById(R.id.comment_con_img);

       if(cm.getS_comment_img().equals("")){
           img.setVisibility(View.GONE);
       }else{
           Tools.showImage(img,cm.getS_comment_img(),getContext());
       }




        return convertView;
    }
}
