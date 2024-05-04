package com.example.orderfood.dao;

import android.content.ContentValues;
import android.database.AbstractWindowedCursor;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.example.orderfood.bean.BusinessBean;
import com.example.orderfood.bean.FoodBean;
import com.example.orderfood.bean.UserBean;
import com.example.orderfood.db.DBUtil;

import java.util.ArrayList;

public class UserDao {

    public static SQLiteDatabase db= DBUtil.db;


    //注册普通用户
    public static  Long addUser(String ...data){

        ContentValues values = new ContentValues();
        values.put("s_id",data[0]);
        values.put("s_pwd",data[1]);
        values.put("s_name",data[2]);
        values.put("s_sex",data[3]);
        values.put("s_address",data[4]);
        values.put("s_phone",data[5]);
        values.put("s_img",data[6]);

        Long rowId = db.insert("d_user", null, values);
      return  rowId;

    }

    public static  Long addUser(SQLiteDatabase db,String ...data){

        ContentValues values = new ContentValues();
        values.put("s_id",data[0]);
        values.put("s_pwd",data[1]);
        values.put("s_name",data[2]);
        values.put("s_sex",data[3]);
        values.put("s_address",data[4]);
        values.put("s_phone",data[5]);
        values.put("s_img",data[6]);

        Long rowId = db.insert("d_user", null, values);
        return  rowId;

    }


    public static  int updateUser(String ...data){


        try {
            db.execSQL("update  d_user set s_name=?,s_sex=?,s_address=?,s_phone=?,s_img=? where s_id=?",data);
            return 1;
        }catch (Exception e){
            return 0;
        }


    }



    /**
     * 注册商户
     * @param bit
     * @param data
     * @return
     */
    public static  Long addBusiness(String ...data){

        ContentValues values = new ContentValues();
        values.put("s_id",data[0]);
        values.put("s_pwd",data[1]);
        values.put("s_name",data[2]);
        values.put("s_describe",data[3]);
        values.put("s_type",data[4]);
        values.put("s_img",data[5]);
        Long rowId = db.insert("d_business", null, values);
        return  rowId;

    }

    /**
     * 更改商家店铺信息
     * @param data
     * @return
     */

    public static  int  updateBusiness(String ...data){

        try {
            db.execSQL("update d_business set s_name=?,s_describe=?,s_type=?,s_img=? where s_id=?",data);
            return 1;
        }catch (Exception e){
            return 0;
        }





    }


    /**
     *  db.execSQL("insert into d_food (s_food_id,s_business_id,s_food_name,s_food_des,s_food_price,s_food_img) values(?,?,?,?,?,?)",da);
     * 实现添加商品内容
     * @param data
     * @return
     */

    public static  Long addGood(String ...data){

        ContentValues values = new ContentValues();
        values.put("s_food_id",data[0]);
        values.put("s_business_id",data[1]);
        values.put("s_food_name",data[2]);
        values.put("s_food_des",data[3]);
        values.put("s_food_price",data[4]);
        values.put("s_food_img",data[5]);
        Long rowId = db.insert("d_food", null, values);
        return  rowId;

    }



    public static  Long addBusiness(SQLiteDatabase db,String ...data){

        ContentValues values = new ContentValues();
        values.put("s_id",data[0]);
        values.put("s_pwd",data[1]);
        values.put("s_name",data[2]);
        values.put("s_describe",data[3]);
        values.put("s_type",data[4]);
        values.put("s_img",data[5]);


        Long rowId = db.insert("d_business", null, values);
        return  rowId;

    }

    /**
     * 账号，密码，登录权限
     * @param account
     * @param pwd
     * @param sta
     * @return
     */
    public static int loginUser(String account,String pwd,String sta){//1登录商家，2登录用户
        String table="";
        String sql;
        if(sta.equals("1")){//1登录商家，2登录用户
            sql="select * from d_business where s_id=? and s_pwd=?";

        }else{
            sql="select * from d_user where s_id=? and s_pwd=?";

        }

        String values[]={account,pwd};
        Cursor result = db.rawQuery(sql,values);
        int a=0;//0 代表用户不存在
        while(result.moveToNext()){
            a++;

        }
        return a;



    }


    /**
     *获取商家用户内容
     * @param account
     * @return
     */
    public static BusinessBean getBusinessUser(String account){//1登录商家，2登录用户

        String sql="select * from d_business where s_id=?";;


        String values[]={account};
        Cursor res = db.rawQuery(sql,values);
        int a=0;//0 代表用户不存在
        while(res.moveToNext()){
            String s_id=res.getString(0);
            String s_pwd=res.getString(1);
            String s_name=res.getString(2);
            String s_describe=res.getString(3);
            String s_type=res.getString(4);
            String s_img=res.getString(5);

            BusinessBean businessBean=new BusinessBean(s_id,s_pwd,s_name,s_describe,s_type,s_img);
            return businessBean;
        }
        return null;

    }

    /**
     *获取商家用户内容
     * @param account
     * @return
     */
    public static UserBean getUser(String account){//1登录商家，2登录用户

        String sql="select * from d_user where s_id=?";;


        String values[]={account};
        Cursor res = db.rawQuery(sql,values);
        int a=0;//0 代表用户不存在
        while(res.moveToNext()){


            UserBean businessBean=new UserBean(res.getString(0),res.getString(1),res.getString(2),
                    res.getString(3),res.getString(4),res.getString(5),res.getString(6));
            return businessBean;
        }
        return null;

    }





    /**
     * 修改商家密码
     * @param account
     * @param pwd
     * @return
     */
    public static int updateBusinessUserPwd(String account,String pwd){


        String sql="update  d_business set s_pwd=? where s_id=?";;


        String values[]={pwd,account};
        try {
            db.execSQL(sql,values);
            return 1;
        }catch (Exception exception){
            return 0;
        }


    }

    /**
     * 更改普通用户的密码
     * @param account
     * @param pwd
     * @return
     */

    public static int updateUserPwd(String account,String pwd){


        String sql="update  d_user set s_pwd=? where s_id=?";;


        String values[]={pwd,account};
        try {
            db.execSQL(sql,values);
            return 1;
        }catch (Exception exception){
            return 0;
        }


    }




    /**
     * 这个是获取所有食物信息的方法
     * @return
     */

    public static  ArrayList<FoodBean> getAllFood(String account){

        Cursor res = db.rawQuery("select * from d_food where s_business_id=?",new String[]{account});
        ArrayList<FoodBean> list=new ArrayList();


       while(res.moveToNext()){

             String s_food_id=res.getString(0);
             String s_business_id=res.getString(1);
             String s_food_name=res.getString(2);
             String s_food_des=res.getString(3);
             String s_food_price=res.getString(4);
             String s_food_img=res.getString(5);


            FoodBean foodBean=new FoodBean(s_food_id,s_business_id,s_food_name,s_food_des,s_food_price,s_food_img);

          list.add(foodBean);


        }




        return list;
    }


    /**
     * 这个是获取所有食物信息的方法
     * @return
     */

    public static  ArrayList<FoodBean> getAllFoodByName(String account){
        String id="%"+account+"%";
        Cursor res = db.rawQuery("select * from d_food where s_food_name like ?",new String[]{id});
        ArrayList<FoodBean> list=new ArrayList();


        while(res.moveToNext()){

            String s_food_id=res.getString(0);
            String s_business_id=res.getString(1);
            String s_food_name=res.getString(2);
            String s_food_des=res.getString(3);
            String s_food_price=res.getString(4);
            String s_food_img=res.getString(5);


            FoodBean foodBean=new FoodBean(s_food_id,s_business_id,s_food_name,s_food_des,s_food_price,s_food_img);
            list.add(foodBean);


        }




        return list;
    }

    /**
     * 这个是获取所有食物信息的方法
     * @return
     */

    public static  ArrayList<FoodBean> getAllFood(){

        Cursor res = db.rawQuery("select * from d_food ",null);
        ArrayList<FoodBean> list=new ArrayList();


        while(res.moveToNext()){

            String s_food_id=res.getString(0);
            String s_business_id=res.getString(1);
            String s_food_name=res.getString(2);
            String s_food_des=res.getString(3);
            String s_food_price=res.getString(4);
            String s_food_img=res.getString(5);


            FoodBean foodBean=new FoodBean(s_food_id,s_business_id,s_food_name,s_food_des,s_food_price,s_food_img);

            list.add(foodBean);


        }




        return list;
    }



    /**
     * 这个是获取所有食物信息的方法
     * @return
     */

    public static  ArrayList<FoodBean> getAllFoodByName(String account,String name){

        Cursor res = db.rawQuery("select * from d_food where s_business_id=? and s_food_name like '%"+name+"%'",new String[]{account});
        ArrayList<FoodBean> list=new ArrayList();


        while(res.moveToNext()){

            String s_food_id=res.getString(0);
            String s_business_id=res.getString(1);
            String s_food_name=res.getString(2);
            String s_food_des=res.getString(3);
            String s_food_price=res.getString(4);
            String s_food_img=res.getString(5);


            FoodBean foodBean=new FoodBean(s_food_id,s_business_id,s_food_name,s_food_des,s_food_price,s_food_img);

            list.add(foodBean);


        }




        return list;
    }

    /**
     * 获取食物ID的月销售额
     * 通过食物ID进行查询
     * @return
     */
    public static String getMonthlySales(String food_id){
       // Cursor res = db.rawQuery("select strftime('%Y-%m',s_food_time) as month,sum(s_food_num) as total_sales  from d_order_details ", );

         Cursor res = db.rawQuery("select strftime('%Y-%m',s_food_time) as month,sum(s_food_num) as total_sales  from d_order_detail where s_food_id=?   GROUP BY month ORDER BY month desc", new String[]{food_id});
        //这么多条记录只取第一条
        while(res.moveToNext()){

           return res.getString(1);

        }
        return "0";


    }


}
