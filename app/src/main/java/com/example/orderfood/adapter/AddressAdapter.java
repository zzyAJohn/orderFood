package com.example.orderfood.adapter;

import android.app.Activity;
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
import com.example.orderfood.activity.UpdateGoodActivity;
import com.example.orderfood.bean.BusinessBean;
import com.example.orderfood.bean.CommentBean;
import com.example.orderfood.bean.AddressBean;
import com.example.orderfood.dao.CommentDao;
import com.example.orderfood.dao.UserDao;
import com.example.orderfood.tools.Tools;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个是先生收货地址
 */
public class AddressAdapter extends ArrayAdapter<AddressBean> {


    private List<AddressBean> list;

    private Context context;
    private View view;
    public AddressAdapter(@NonNull Context context, List<AddressBean> list,View view) {
        super(context, R.layout.address_list, list);
        this.list=list;
        this.context=context;
        this.view=view;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Log.d("FoodAdapter", "getView called for position: " + position);
        if(convertView==null){

            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.address_list,parent,false);
        }

        AddressBean addressBean=list.get(position);
        //地址 姓名 联系方式
        TextView peo =view.findViewById(R.id.order_accept_text);
        TextView address =view.findViewById(R.id.order_Address_text);
        TextView phone = view.findViewById(R.id.order_phone_text);


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                peo.setText(addressBean.getS_user_name());
                address.setText(addressBean.getS_user_address());
                phone.setText(addressBean.getS_phone());
            }
        });
        peo.setText("");
        address.setText("");
        phone.setText("");

        TextView addressN = convertView.findViewById(R.id.a_address);
        TextView nameN=convertView.findViewById(R.id.a_name);
        TextView phoneN=convertView.findViewById(R.id.a_phone);

        nameN.setText(addressBean.getS_user_name());
        addressN.setText(addressBean.getS_user_address());
        phoneN.setText(addressBean.getS_phone());
        
        return convertView;
    }
}
