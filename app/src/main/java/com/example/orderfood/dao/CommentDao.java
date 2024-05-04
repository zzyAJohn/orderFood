package com.example.orderfood.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.orderfood.bean.CommentBean;
import com.example.orderfood.db.DBUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 这个是评论的dao
 */
public class CommentDao {

    public static SQLiteDatabase db= DBUtil.db;

    /**
     * 查询所有的注释
     * @param id
     * @return
     */
    public static List<CommentBean> getAllComment(String id){
        String da[]={id};

        List<CommentBean> list=new ArrayList();
        Cursor rs = db.rawQuery("select * from d_comments where s_comment_business_id=?", da);
        while(rs.moveToNext()){
            CommentBean commentBean=new CommentBean(
                    rs.getString(0),
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8)


            );
            list.add(commentBean);
        }
        return list;

    }

    /**
     * 保存评论
     * @param data
     * @return
     */
    public static int saveComment(String ...data){

        try {

            db.execSQL("insert into d_comments values(?,?,?,?,?,?,?,?,?)",data);
            return 1;
        }catch (Exception e){
            return 0;
        }



    }

    /**
     * 有则返回 1
     * @param id
     * @return
     */

    public static int getIsComment(String id){

        Cursor rs = db.rawQuery("select * from d_comments where s_comment_id=?", new String[]{ id});
        while (rs.moveToNext()){
            return 1;
        }

        return 0;
    }





}
