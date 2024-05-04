package com.example.orderfood.listener;


import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;

/**
 * 设置按钮颜色的加内特
 */
public class ButtonColorListener implements View.OnClickListener {

    int sta;
    Button home,add,my;

    public ButtonColorListener(Button home,Button add,Button my,int sta){
        this.sta=sta;
        this.home=home;
        this.my=my;
        this.add=add;

    }



    @Override
    public void onClick(View view) {




        //设置完颜色后应该立刻取消其他的颜色

        Button [] buttons={home,add,my};
        for(int i=1;i<=3;i++){//循环三次

            Button temp=buttons[i-1];
            // 获取RadioButton的CompoundDrawableTop
            Drawable drawableTop=temp.getCompoundDrawablesRelative()[1];
            //设置主题颜色
            // 创建一个颜色过滤器，设置你想要的颜色
            int color = Color.parseColor("#A2A2A2"); // 例如，这里设置为红色

            drawableTop.setColorFilter(new PorterDuffColorFilter(color,  PorterDuff.Mode.SRC_IN));
            // 将修改后的Drawable设置回RadioButton
            temp.setCompoundDrawablesRelative(null, drawableTop, null, null);


        }


        Button my= (Button) view;
        // 获取RadioButton的CompoundDrawableTop
        Drawable drawableTop=my.getCompoundDrawablesRelative()[1];
        //设置主题颜色
        // 创建一个颜色过滤器，设置你想要的颜色
        int color = Color.parseColor("#F24960"); // 例如，这里设置为红色

        drawableTop.setColorFilter(new PorterDuffColorFilter(color,  PorterDuff.Mode.SRC_IN));
        // 将修改后的Drawable设置回RadioButton
        my.setCompoundDrawablesRelative(null, drawableTop, null, null);

    }
}
