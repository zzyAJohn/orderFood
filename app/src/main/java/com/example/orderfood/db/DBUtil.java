package com.example.orderfood.db;


import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;

import com.example.orderfood.R;
import com.example.orderfood.dao.UserDao;
import com.example.orderfood.tools.Tools;

import java.io.ByteArrayOutputStream;

public class DBUtil extends SQLiteOpenHelper {

    //数据库名
    private static final String DB_NAME = "db_takeaway.db";

    public static SQLiteDatabase db = null;//通过db进行操作数据库

    private static final int VERSION = 47;//版本 //每一次对数据库脚本的更新都需要将这个数字进行加1

    private Context context;

    public DBUtil(Context context) {
        super(context, DB_NAME, null, VERSION, null);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys = false");
        byte tx[] = getImg(R.drawable.upimg);

        //将二进制转换成图片
        String uuid = Tools.getUUID();
        String filePath = Tools.getImagePath() + "/" + uuid + "tx.png";
        Tools.saveByteArrayAsPng(tx, filePath, context);

        //1.创建商家表
        db.execSQL("drop table if exists d_business");//如果这表存在则删除
        db.execSQL("create table d_business(s_id varchar(20) primary key," +//商家ID
                "s_pwd varchar(20)," +//密码
                "s_name varchar(20)," +//商家名
                "s_describe varchar(200)," +//描述
                "s_type varchar(20)," +//地址
                "s_img varchar(255))");//头像
        UserDao.addBusiness(db, "ganguo", "123456", "干锅", "好吃的干锅！！！", "二食堂二楼", filePath);
        UserDao.addBusiness(db, "zhujiaofan", "123456", "猪脚饭", "新鲜猪脚！！！", "二食堂一楼", filePath);
        UserDao.addBusiness(db, "tianpin", "123456", "甜品", "我超甜！！！", "二食堂一楼", filePath);

        //2.创建用户表
        db.execSQL("drop table if exists d_user");//如果这表存在则删除
        db.execSQL("create table d_user(s_id varchar(20) primary key," +//账号
                "s_pwd varchar(20)," +//密码
                "s_name varchar(20)," +//昵称
                "s_sex varchar(200)," +//性别
                "s_address varchar(20)," +//地址
                "s_phone varchar(20)," +//联系方式
                "s_img varchar(255))");//头像
        UserDao.addUser(db, "zzy", "123456", "吃货张三", "男", "辽宁省沈阳市", "15311223344", filePath);
        UserDao.addUser(db, "wc", "123456", "吃货吴四", "男", "辽宁省沈阳市", "13011223344", filePath);


        //3.创建商品表
        db.execSQL("drop table if exists d_food");//如果这表存在则删除
        db.execSQL("create table d_food(s_food_id varchar(20)primary key ," +//食物ID
                "s_business_id varchar(20) ," +//商家ID
                "s_food_name varchar(20)," +//食物名
                "s_food_des varchar(200)," +//食物描述
                "s_food_price varchar(20)," +//食物价格
                "s_food_img varchar(255))");//食物图片

        //select * from d_food LEFT JOIN
        String mltPath = Tools.getImagePath() + "/" + uuid + "mlt.png";
        Tools.saveByteArrayAsPng(getImg(R.drawable.mlt), mltPath, context);
        String da[] = {"1", "ganguo", "干锅小肉茄子", "小肉+茄子.", "11", mltPath};
        db.execSQL("insert into d_food (s_food_id,s_business_id,s_food_name,s_food_des,s_food_price,s_food_img) values(?,?,?,?,?,?)", da);

        String klmPath = Tools.getImagePath() + "/" + uuid + "klm.png";
        Tools.saveByteArrayAsPng(getImg(R.drawable.klm), klmPath, context);
        String da1[] = {"2", "ganguo", "干锅小肉土豆", "小肉+土豆", "11", klmPath};
        db.execSQL("insert into d_food (s_food_id,s_business_id,s_food_name,s_food_des,s_food_price,s_food_img) values(?,?,?,?,?,?)", da1);

        String zjPath = Tools.getImagePath() + "/" + uuid + "klm.png";
        Tools.saveByteArrayAsPng(getImg(R.drawable.klm), zjPath, context);
        String da2[] = {"3", "zhujiaofan", "猪脚饭", "全是猪脚！！！", "13", zjPath};
        db.execSQL("insert into d_food (s_food_id,s_business_id,s_food_name,s_food_des,s_food_price,s_food_img) values(?,?,?,?,?,?)", da2);


//        String teaSundaePath = Tools.getImagePath() + "/" + uuid + "sundae.png";
//        Tools.saveByteArrayAsPng(getImg(R.drawable.tea_sundae), teaSundaePath, context);
//        String da3[] = {"3", "tianpin", "圣代", "超好吃！！！", "6", teaSundaePath};
//        db.execSQL("insert into d_food (s_food_id,s_business_id,s_food_name,s_food_des,s_food_price,s_food_img) values(?,?,?,?,?,?)", da3);

//        String zjPath = Tools.getImagePath() + "/" + uuid + "klm.png";
//        Tools.saveByteArrayAsPng(getImg(R.drawable.klm), zjPath, context);
//        String da2[] = {"3", "zhujiaofan", "猪脚饭", "全是猪脚！！！", "13", zjPath};
//        db.execSQL("insert into d_food (s_food_id,s_business_id,s_food_name,s_food_des,s_food_price,s_food_img) values(?,?,?,?,?,?)", da2);
        //4.创建订单表
        db.execSQL("drop table if exists d_orders");//如果这表存在则删除
        db.execSQL("create table d_orders(s_order_id integer primary key autoincrement," +//订单ID
                "s_order_time varchar(20) ," +//购买时间
                "s_order_user_id varchar(20)," +//用户ID
                "s_order_user_name varchar(20)," +//用户昵称
                "s_order_user_img varchar(255)," +//用户头像
                "s_order_business varchar(200)," +//商家ID
                "s_order_business_name varchar(200)," +//商家名
                "s_order_business_img varchar(255)," +//商家头像
                "s_order_business_id varchar(20)," +//订单详情ID
                "s_order_sta varchar(20)," +//订单状态 他有三个值，默认的是，未结单，接单，取消订单  1处理中，2取消订单  3完成订单
                "s_order_adress varchar(255))");//收货地址
        String img[] = {filePath, filePath};
        // select * from d_orders
        db.execSQL("insert into d_orders values('1','2024-4-07 11:56','zzy','吃货张三',?,'ganguo','干锅',?,'1','1','张某人-校内二舍617-15311223344')", img);
        db.execSQL("insert into d_orders values('2','2024-5-07 10:40','zzy','吃货张三',?,'zhujiaofan','猪脚饭',?,'2','1','张某人-校内二舍617-15311223344')", img);
//        db.execSQL("insert into d_orders values('3','2024-5-08 11:30','wc','吃货吴四',?,'zhujiaofan','猪脚饭',?,'2','1','吴某人-校内二舍617-13011223344')", img);
//        db.execSQL("insert into d_orders values('4','2024-5-09 12:00','wc','吃货吴四',?,'zhujiaofan','猪脚饭',?,'2','1','吴某人-校内二舍617-13011223344')", img);



        //5.创建地址表
        db.execSQL("drop table if exists d_address");//如果这表存在则删除
        db.execSQL("create table d_address(s_id varchar(30)primary key ," +//地址ID
                "s_user_id varchar(255) ," +//用户ID
                "s_user_name varchar(255)," +//收货人
                "s_user_address varchar(255)," +//收货地址
                "s_phone varchar(255))");//联系方式

        // select * from d_orders
        db.execSQL("insert into d_address values('1','zzy','张某人','校内二舍617','15311223344')");
        db.execSQL("insert into d_address values('2','wc','吴某','校内二舍617','13011223344')");

        //6.创建订单细节表
        db.execSQL("drop table if exists d_order_detail");//如果这表存在则删除
        db.execSQL("create table d_order_detail(s_order_business_id varchar(20) ," +//订单ID
                "s_food_id varchar(20) ," +//食物ID
                "s_food_name varchar(20)," +//食物名
                "s_food_des varchar(200)," +//食物描述
                "s_food_price varchar(20)," +//食物单价
                "s_food_img varchar(255)," +//食物图片
                "s_food_time varchar(200)," +//购买时间
                "s_food_num varchar(255))");//购买的数量

        String klms[] = {klmPath};
        String mlts[] = {mltPath};

        //订单1
        db.execSQL("insert into d_order_detail values('1','2','干锅小肉茄子','不吃就会后悔','11',?,'2024-4-07','2')", klms);
//        db.execSQL("insert into d_order_detail values('1','1','东北麻辣烫2','不吃就会后悔的麻辣烫2','19.86',?,'2023-10-07','15')", mlts);
//        db.execSQL("insert into d_order_detail values('1','1','东北麻辣3','不吃就会后悔的麻辣烫3','19.86',?,'2023-10-07','20')", mlts);
        //订单2
        db.execSQL("insert into d_order_detail values('2','1','猪脚饭','吃！！','13',?,'2024-5-07','1')", mlts);
//        db.execSQL("insert into d_order_detail values('2','1','东北麻辣烫2','不吃就会后悔的麻辣烫2','19.86',?,'2023-12-07','13')", mlts);
//        db.execSQL("insert into d_order_detail values('2','1','东北麻辣烫3','不吃就会后悔的麻辣烫3','19.86',?,'2023-12-07','14')", mlts);



        //7.创建评价表
        db.execSQL("drop table if exists d_comments");//如果这表存在则删除  代表的是用户表
        //评价管理，默认的是不可以删除评级的内容
        db.execSQL("create table d_comments(s_comment_id varchar(255) primary key ," +//评论ID
                "s_comment_user_id varchar(255) ," +//评论用户的ID
                "s_comment_user_name varchar(255)," +//评论用户的昵称
                "s_comment_user_img varchar(255)," +//评论用户的头像
                "s_comment_contenct varchar(255)," +//评论的内容
                "s_comment_img varchar(255)," +//评论的图片，可以有多个
                "s_comment_time varchar(255)," +//评论的时间
                "s_comment_business_id varchar(255)," +//评论的店铺ID (商家可以根据这个进行查看自己店铺的评论 与评分)
                "s_comment_score varchar(255))");//评论可以有5个分数，1分-2分差评 3分中评 4 5好评
        String imgz[] = {filePath};
        db.execSQL("insert into d_comments values('1','zzy','吃货张三',?,'这个菜是真的好吃，我要给五分好评！',?,'2023-10-07 10:56','ganguo','5')", img);
        db.execSQL("insert into d_comments values('2','zzy','吃货张三',?,'怕你骄傲给三分','','2023-10-25 10:56','zhujiaofan','3')", imgz);

        db.execSQL("PRAGMA foreign_keys = true");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }

    private byte[] getImg(int value) {

        Resources res = context.getResources();//获取资源
        Bitmap mlt = BitmapFactory.decodeResource(res, value); // 麻辣烫
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  // 将Bitmap转换为字节数组
        mlt.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] imageByteArray = byteArrayOutputStream.toByteArray();// 执行插入操作


        return imageByteArray;
    }
}
