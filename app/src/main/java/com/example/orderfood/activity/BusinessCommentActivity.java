package com.example.orderfood.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.orderfood.R;
import com.example.orderfood.adapter.CommentAdapter;
import com.example.orderfood.bean.CommentBean;
import com.example.orderfood.dao.CommentDao;

import java.util.List;

public class BusinessCommentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_comment);

        Toolbar toolbar=findViewById(R.id.comment_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        SharedPreferences sharedPreferences=getSharedPreferences("data", Context.MODE_PRIVATE);
        String account=sharedPreferences.getString("account","");

        //加载列表

       ListView view=findViewById(R.id.comment_list_view);

        List<CommentBean> list = CommentDao.getAllComment(account);
        if(list!=null&&list.size()!=0){
            CommentAdapter adapter = new CommentAdapter(this, list);
            view.setAdapter(adapter);
        }else{
            view.setAdapter(null);
        }


    }
}