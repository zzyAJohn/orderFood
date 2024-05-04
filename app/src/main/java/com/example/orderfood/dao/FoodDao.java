package com.example.orderfood.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.orderfood.bean.FoodBean;
import com.example.orderfood.db.DBUtil;

public class FoodDao {


    public static SQLiteDatabase db= DBUtil.db;

    public static int delFood(String foodId){

        try {
             db.execSQL("delete from d_food where s_food_id=?", new String[]{foodId});
             return 1;
        }catch (Exception e){
            return 0;
        }



    }

    /**
     * 通过食物的ID进行查询单个的食物
     * @param id
     * @return
     */

    public static FoodBean getFoodByID(String id){

        Cursor res = db.rawQuery("select * from d_food where s_food_id=?", new String[]{id});
        FoodBean foodBean=null;
        while(res.moveToNext()){

            String s_food_id=res.getString(0);
            String s_business_id=res.getString(1);
            String s_food_name=res.getString(2);
            String s_food_des=res.getString(3);
            String s_food_price=res.getString(4);
            String s_food_img=res.getString(5);


            foodBean=new FoodBean(s_food_id,s_business_id,s_food_name,s_food_des,s_food_price,s_food_img);

        }
        return foodBean;
    }


    /**
     * 实现更改商品的内容
     * @param data
     * @return
     */
    public static  int  updateGood(String ...data){


        try {
            db.execSQL("update d_food set s_food_name=?,s_food_price=?,s_food_des=?,s_food_img=? where s_food_id=?", data);
            return 1;
        }catch (Exception e){
            return 0;
        }





    

    }
    
}
