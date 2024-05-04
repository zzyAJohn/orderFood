package com.example.orderfood.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.orderfood.R;
import com.example.orderfood.bean.AddressBean;
import com.example.orderfood.user.activity.comment.UpdateAddressActivity;

import java.util.List;

/**
 * 这个是先生收货地址
 */
public class AddressUserAdapter extends ArrayAdapter<AddressBean> {


    private List<AddressBean> list;

    private Context context;

    public AddressUserAdapter(@NonNull Context context, List<AddressBean> list) {
        super(context, R.layout.address_user_list, list);
        this.list=list;
        this.context=context;

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Log.d("FoodAdapter", "getView called for position: " + position);
        if(convertView==null){

            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.address_user_list,parent,false);
        }

        AddressBean addressBean=list.get(position);
        //地址 姓名 联系方式
        TextView peo =convertView.findViewById(R.id.address_peo);
        TextView address =convertView.findViewById(R.id.address_address);
        TextView phone = convertView.findViewById(R.id.address_phone);


        ImageView img =convertView.findViewById(R.id.address_edit);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开更改界面
                Intent intent=new Intent(getContext(), UpdateAddressActivity.class);
                intent.putExtra("id",addressBean.getS_id());
                intent.putExtra("sta","1");//1 代表更改
                getContext().startActivity(intent);
            }
        });


        peo.setText(addressBean.getS_user_name());
        address.setText(addressBean.getS_user_address());
        phone.setText(addressBean.getS_phone());

        return convertView;
    }
}
