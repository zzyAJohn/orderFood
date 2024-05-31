package com.example.orderfood.adapter;


import android.content.Context;
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
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.orderfood.R;
import com.example.orderfood.bean.CommentBean;
import com.example.orderfood.bean.CommentBean;
import com.example.orderfood.bean.OrderDetail;
import com.example.orderfood.dao.OrderDao;
import com.example.orderfood.tools.Tools;

import java.util.List;

/**
 * 商家评论适配器
 */

public class CommentAdapter extends ArrayAdapter<CommentBean> {
    private List<CommentBean> list;
    private ImageView imageView;
    private TextView userName, time;

    public CommentAdapter(@NonNull Context context, List<CommentBean> list) {
        super(context, R.layout.comment_list, list);
        this.list = list;
    }


    public void remove(int position) {
        list.remove(position);
    }

    private void InitView(View convertView) {
        imageView = convertView.findViewById(R.id.comment_tx);
        userName = convertView.findViewById(R.id.comment_user_name);//昵称
        time = convertView.findViewById(R.id.comment_user_time);//时间
    }

    private void InitData(CommentBean cm) {
        userName.setText(cm.getS_comment_user_name());
        time.setText(cm.getS_comment_time());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.comment_list, parent, false);
        }

        CommentBean cm = list.get(position);

        // 初始化控件
        InitView(convertView);

        // 初始化数据
        InitData(cm);

        Tools.showImage(imageView, cm.getS_comment_user_img(), getContext());//用户的头像
        int id[] = {R.id.comment_one_a, R.id.comment_one_b, R.id.comment_one_c, R.id.comment_one_d, R.id.comment_one_e};//5个星星的ID

        int score = Integer.parseInt(cm.getS_comment_score());
        // 实心的星星
        for (int i = 0; i < score; i++) {
            RadioButton temp = convertView.findViewById(id[i]);
            Bitmap bitmap = BitmapFactory.decodeResource(convertView.getResources(), R.drawable.xx);
            BitmapDrawable bitmapDrawable = new BitmapDrawable(convertView.getResources(), bitmap);
            temp.setBackground(bitmapDrawable);
        }
        // 空心的星星
        for (int i = score; i < 5; i++) {
            RadioButton temp = convertView.findViewById(id[i]);
            Bitmap bitmap = BitmapFactory.decodeResource(convertView.getResources(), R.drawable.wxx);
            BitmapDrawable bitmapDrawable = new BitmapDrawable(convertView.getResources(), bitmap);
            temp.setBackground(bitmapDrawable);
        }

        //星代表的内容
        String nr[] = {"非常差", "差", "一般", "满意", "非常满意"};
        TextView nrT = convertView.findViewById(R.id.comment_one_f);
        nrT.setText(nr[score - 1]);

        //一个显示图片，一共显示内容
        TextView con = convertView.findViewById(R.id.comment_con);
        con.setText(cm.getS_comment_contenct());
        ImageView img = convertView.findViewById(R.id.comment_con_img);

        if (cm.getS_comment_img().equals("")) {
            img.setVisibility(View.GONE);
        } else {
            Tools.showImage(img, cm.getS_comment_img(), getContext());
        }
        return convertView;
    }
}
