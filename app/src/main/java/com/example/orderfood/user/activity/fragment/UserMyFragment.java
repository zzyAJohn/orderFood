package com.example.orderfood.user.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.orderfood.MainActivity;
import com.example.orderfood.R;
import com.example.orderfood.activity.AddGoodActivity;
import com.example.orderfood.activity.BusinessActivity;
import com.example.orderfood.activity.BusinessMyActivity;
import com.example.orderfood.activity.UpdateBusinessMesActivity;
import com.example.orderfood.activity.UpdatePwdActivity;
import com.example.orderfood.adapter.UserFoodAdapter;
import com.example.orderfood.bean.FoodBean;
import com.example.orderfood.bean.UserBean;
import com.example.orderfood.dao.UserDao;
import com.example.orderfood.tools.Tools;
import com.example.orderfood.user.activity.UpdatePwdUserActivity;
import com.example.orderfood.user.activity.UpdateUserMesActivity;
import com.example.orderfood.user.activity.UserAddressActivity;
import com.example.orderfood.user.activity.UserManageActivity;
import com.example.orderfood.user.activity.UserMyOrderActivity;

import java.util.ArrayList;

/**
 * 这个是咱们用户购买的首页
 */
public class UserMyFragment extends Fragment {

    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.frament_user_my, container, false);

        SharedPreferences sharedPreferences=getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        String account=sharedPreferences.getString("account","");

        UserBean user = UserDao.getUser(account);

        ImageView img = rootView.findViewById(R.id.business_my_tx);//头像
        Tools.showImage(img,user.getS_img(),getContext());

        TextView id = rootView.findViewById(R.id.business_my_id);//头像
        id.setText(user.getS_id());

        TextView name = rootView.findViewById(R.id.business_my_name);//头像
        name.setText(user.getS_name());

        TextView des = rootView.findViewById(R.id.business_my_des);//头像
        des.setText(user.getS_address());


        //实现查看所有的订单
        TextView allOrder=rootView.findViewById(R.id.user_my_order);//查看我的所有订单
        allOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), UserMyOrderActivity.class);
                startActivity(intent);
            }
        });


        // 获取对宿主 AppCompatActivity 的引用
        UserManageActivity activity = (UserManageActivity) getActivity();


        //实现退出功能
        TextView exit= rootView.findViewById(R.id.business_my_exit);//实现退出功能
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.exit();
            }
        });


        //实现注销功能
        TextView zx=  rootView.findViewById(R.id.business_my_zx);//实现退出功能
        zx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        //修改密码和店铺信息


        //实现修改密码的功能
        TextView xgmm= rootView.findViewById(R.id.business_my_xgmm);//实现退出功能
        xgmm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), UpdatePwdUserActivity.class);
                startActivity(intent);
            }
        });
        //修改店铺信息

        TextView xgdpxx= rootView.findViewById(R.id.business_my_xgdpxx);//实现退出功能
        xgdpxx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), UpdateUserMesActivity.class);
                startActivity(intent);
            }
        });

        //添加地址

        TextView dz= rootView.findViewById(R.id.user_dz_order);//实现退出功能
        dz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), UserAddressActivity.class);
                startActivity(intent);
            }
        });


        return rootView;
    }
}
