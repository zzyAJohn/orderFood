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


    //写一个数据库得名字
    private static final  String DB_NAME="db_takeaway.db";//数据库得名字

    public static SQLiteDatabase db=null;//通过db进行操作数据库


    private static  final int VERSION=47;//版本 //每一次对数据库脚本的更新都需要将这个数字进行加1

    private  Context context;

    public DBUtil(Context context){
        super(context,DB_NAME,null,VERSION,null);
        this.context=context;

    }



    @Override
    public void onCreate(SQLiteDatabase db) {




        db.execSQL("PRAGMA foreign_keys = false");
        byte tx[]=getImg(R.drawable.upimg);
        //将二进制转换成图品
        String uuid=Tools.getUUID();

        String filePath=Tools.getImagePath()+"/"+uuid+"tx.png";
        Tools.saveByteArrayAsPng(tx,filePath,context);








        //分为商家表和用户表，第一步先创建一个商家表
        db.execSQL("drop table if exists d_business");//如果这表存在则删除
        //商家ID,密码，店名，描述，类型
        db.execSQL("create table d_business(s_id varchar(20) primary key," +
                "s_pwd varchar(20)," +
                "s_name varchar(20)," +
                "s_describe varchar(200)," +
                "s_type varchar(20)," +
                "s_img varchar(255))");//如果这表存在则删除
        UserDao.addBusiness(db,"root","123456","小铭杂货铺","卖一些小玩具...","玩具店.",filePath);


        //分为商家表和用户表，第一步先创建一个商家表
        db.execSQL("drop table if exists d_user");//如果这表存在则删除  代表的是用户表
        //商家ID,密码，店名，描述，类型
        db.execSQL("create table d_user(s_id varchar(20) primary key," +//账号
                "s_pwd varchar(20)," +//密码
                "s_name varchar(20)," +//昵称
                "s_sex varchar(200)," +//性别
                "s_address varchar(20)," +//地址
                "s_phone varchar(20)," +//联系方式
                "s_img varchar(255))");//表示这个用户的头像

        UserDao.addUser(db,"admin","123456","小铭用户","男","北京市","123123",filePath);



        //建立一个存储商品给的表
        //分为商家表和用户表，第一步先创建一个商家表
        db.execSQL("drop table if exists d_food");//如果这表存在则删除  代表的是用户表
        //商家ID,密码，店名，描述，类型
        db.execSQL("create table d_food(s_food_id varchar(20)primary key ," +//食物ID
                "s_business_id varchar(20) ," +//店铺ID
                "s_food_name varchar(20)," +//食物名字
                "s_food_des varchar(200)," +//食物描述
                "s_food_price varchar(20)," +//食物价格
                "s_food_img varchar(255))");//表示这个用户的头像

        //select * from d_food LEFT JOIN


        String mltPath=Tools.getImagePath()+"/"+uuid+"mlt.png";
        Tools.saveByteArrayAsPng(getImg(R.drawable.mlt),mltPath,context);

        String da[]={"1","root","东北麻辣烫","不吃就会后悔的麻辣烫.","19.86", mltPath};
      db.execSQL("insert into d_food (s_food_id,s_business_id,s_food_name,s_food_des,s_food_price,s_food_img) values(?,?,?,?,?,?)",da);


        String klmPath=Tools.getImagePath()+"/"+uuid+"klm.png";
        Tools.saveByteArrayAsPng(getImg(R.drawable.klm),klmPath,context);
        String da1[]={"2","root","东北麻烤冷面","不吃就会后悔的烤冷面","19.86", klmPath};
        db.execSQL("insert into d_food (s_food_id,s_business_id,s_food_name,s_food_des,s_food_price,s_food_img) values(?,?,?,?,?,?)",da1);



        //订单ID，订单时间，购买人，商家，商品ID
        //商品ID，商家用户，名字，描述，单价，数量
        db.execSQL("drop table if exists d_orders");//如果这表存在则删除  代表的是用户表
        db.execSQL("create table d_orders(s_order_id varchar(20)primary key ," +//订单ID
                "s_order_time varchar(20) ," +//购买时间
                "s_order_user_id varchar(20)," +//购买人账号
                "s_order_user_name varchar(20)," +//购买人账号名称
                "s_order_user_img varchar(255)," +//购买人的头像
                "s_order_business varchar(200)," +//商家账号
                "s_order_business_name varchar(200)," +//商家账号
                "s_order_business_img varchar(255)," +//商家
                "s_order_business_id varchar(20)," +//订单详情ID
                "s_order_sta varchar(20)," +//订单状态 他有三个值，默认的是，未结单，接单，取消订单  1处理中，2取消订单  3完成订单
                "s_order_adress varchar(255))");//收货地址
        String img[]={filePath,filePath};
            // select * from d_orders
        db.execSQL("insert into d_orders values('1','2023-10-07','admin','小铭用户',?,'root','小铭杂货铺',?,'1','1','小铭先生-四川成都，山上学院-123123')",img);
        db.execSQL("insert into d_orders values('2','2023-12-07','admin','小铭用户',?,'root','小铭杂货铺',?,'2','1','小李先生-江苏南京，卡通-123123')",img);



        //订单ID，订单时间，购买人，商家，商品ID
        //商品ID，商家用户，名字，描述，单价，数量
        db.execSQL("drop table if exists d_address");//如果这表存在则删除  代表的是用户表
        db.execSQL("create table d_address(s_id varchar(30)primary key ," +//收货地址ID
                "s_user_id varchar(255) ," +//用户ID
                "s_user_name varchar(255)," +//收货人
                "s_user_address varchar(255)," +//收货地址
                "s_phone varchar(255))");//收货联系方式

        // select * from d_orders
        db.execSQL("insert into d_address values('1','admin','小铭先生','四川成都，山上学院','123456')");
        db.execSQL("insert into d_address values('2','admin','小小铭先生','江苏南京，卡通','12345678')");





        db.execSQL("drop table if exists d_order_detail");//如果这表存在则删除  代表的是用户表
        db.execSQL("create table d_order_detail(s_order_business_id varchar(20) ," +//订单ID
                "s_food_id varchar(20) ," +//食物ID
                "s_food_name varchar(20)," +//食物名字
                "s_food_des varchar(200)," +//食物的描述
                "s_food_price varchar(20)," +//食物单价
                "s_food_img varchar(255)," +//食物的图片
                "s_food_time varchar(200)," +//购买时间
                "s_food_num varchar(255))");//购买的数量"东北麻烤冷面","不吃就会后悔的烤冷面","19.86"

        String klms[]={klmPath};
        String mlts[]={mltPath};

        db.execSQL("insert into d_order_detail values('1','2','东北麻烤冷面1','不吃就会后悔的烤冷面1','19.86',?,'2023-10-07','10')",klms);
        db.execSQL("insert into d_order_detail values('1','1','东北麻辣烫2','不吃就会后悔的麻辣烫2','19.86',?,'2023-10-07','15')",mlts);
        db.execSQL("insert into d_order_detail values('1','1','东北麻辣3','不吃就会后悔的麻辣烫3','19.86',?,'2023-10-07','20')",mlts);

        db.execSQL("insert into d_order_detail values('2','1','东北麻辣烫1','不吃就会后悔的麻辣烫1','19.86',?,'2023-12-07','11')",mlts);
        db.execSQL("insert into d_order_detail values('2','1','东北麻辣烫2','不吃就会后悔的麻辣烫2','19.86',?,'2023-12-07','13')",mlts);
        db.execSQL("insert into d_order_detail values('2','1','东北麻辣烫3','不吃就会后悔的麻辣烫3','19.86',?,'2023-12-07','14')",mlts);


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
        String imgz[]={filePath};
        db.execSQL("insert into d_comments values('1','admin','小铭用户',?,'这个菜是真的好吃',?,'2023-10-07 10:56','root','5')", img);
        db.execSQL("insert into d_comments values('2','admin','小铭用户',?,'这个是第二个评论','','2023-10-25 10:56','root','3')",imgz);

        db.execSQL("PRAGMA foreign_keys = true");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            onCreate(sqLiteDatabase);
    }

    private byte[] getImg(int value){

        Resources res  =context.getResources();//获取资源
        Bitmap mlt = BitmapFactory.decodeResource(res,value); // 麻辣烫
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();  // 将Bitmap转换为字节数组
        mlt.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] imageByteArray = byteArrayOutputStream.toByteArray();// 执行插入操作


        return imageByteArray;
    }
}
