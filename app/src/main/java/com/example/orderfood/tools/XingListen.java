package com.example.orderfood.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.orderfood.R;
import com.example.orderfood.user.activity.comment.UserCommentActivity;

public class XingListen implements View.OnClickListener{


    int score=0;
    TextView t;
    UserCommentActivity context;
    public XingListen(TextView t, int score, UserCommentActivity context){
        this.score=score;
        this.t=t;
        this.context=context;

    }


    @Override
    public void onClick(View view) {

        int id[]={R.id.comment_one_a,R.id.comment_one_b,R.id.comment_one_c,R.id.comment_one_d,R.id.comment_one_e};//5个星星的ID

        for(int i=0;i<score;i++){//正向的凡是

            RadioButton temp= context.findViewById(id[i]);
            Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.xx);
            BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
            temp.setBackground(bitmapDrawable);
        }
        for(int i=score;i<5;i++){
            RadioButton temp= context.findViewById(id[i]);
            Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.wxx);
            BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
            temp.setBackground(bitmapDrawable);
        }

        //星代表的内容
        String nr[]={"非常差","差","一般","满意","非常满意"};
        TextView nrT= context.findViewById(R.id.comment_one_f);
        nrT.setText(nr[score-1]);
    }
}
