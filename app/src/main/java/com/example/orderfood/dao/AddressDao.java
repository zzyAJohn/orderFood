package com.example.orderfood.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.orderfood.bean.AddressBean;
import com.example.orderfood.db.DBUtil;

import java.util.ArrayList;
import java.util.List;

public class AddressDao {

    public static SQLiteDatabase db= DBUtil.db;

    public static List<AddressBean> getAllAddress(String id){

        List<AddressBean> list=new ArrayList<>();
        String da[]={id};
        Cursor rs = db.rawQuery("select * from d_address where s_user_id=?", da);
        while(rs.moveToNext()){
            AddressBean tem = new AddressBean(rs.getString(0), rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
            list.add(tem);
        }
        return list;

    }


    public static AddressBean getAllAddressById(String id){

        List<AddressBean> list=new ArrayList<>();
        String da[]={id};
        Cursor rs = db.rawQuery("select * from d_address where s_id=?", da);
        while(rs.moveToNext()){
            AddressBean tem = new AddressBean(rs.getString(0), rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
            return tem;
        }
        return null;


    }

    /**
     * 更改我的地址
     * @param id
     * @return
     */
//        db.execSQL("create table d_address(s_id varchar(30)primary key ," +//收货地址ID
//                "s_user_id varchar(255) ," +//用户ID
//                "s_user_name varchar(255)," +//收货人
//                "s_user_address varchar(255)," +//收货地址
//                "s_phone varchar(255))");//收货联系方式
    public static int uplAddress(String id,String peo,String address,String phone){


        String da[]={peo,address,phone,id};
        try {
         ;db.execSQL("update d_address set s_user_name=?,s_user_address=?,s_phone=? where s_id=?", da);
            return 1;
        }catch (Exception e){
            return 0;
        }



    }

    /**
     * 删除我的地址
     * @param id
     * @return
     */
    public static int delAddress(String id){


        String da[]={id};
        try {
            ;db.execSQL("delete from  d_address  where s_id=?", da);
            return 1;
        }catch (Exception e){
            return 0;
        }



    }

    public static int addAddress(String id,String uid,String peo,String address,String phone){


        String da[]={id,uid,peo,address,phone};
        try {
            db.execSQL("insert into d_address values(?,?,?,?,?)",da);

            return 1;
        }catch (Exception e){
            return 0;
        }



    }


}
