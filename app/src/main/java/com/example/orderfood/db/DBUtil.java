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

        String businessPath1 = Tools.getImagePath() + "/" + uuid + "mdl.jpg";
        Tools.saveByteArrayAsPng(getImg(R.drawable.mdl), businessPath1, context);
        UserDao.addBusiness(db, "maidanglao", "123456", "麦当劳", "儿童套餐9.9起！！！", "二食堂二楼", businessPath1);

        String businessPath2 = Tools.getImagePath() + "/" + uuid + "mxbc.jpg";
        Tools.saveByteArrayAsPng(getImg(R.drawable.mxbc), businessPath2, context);
        UserDao.addBusiness(db, "mixuebingcheng", "123456", "蜜雪冰城", "你爱我我爱你蜜雪冰城甜蜜蜜！", "二食堂一楼", businessPath2);

//        String Path3 = Tools.getImagePath() + "/" + uuid + "maidanglao.jpg";
//        Tools.saveByteArrayAsPng(getImg(R.drawable.mdl), Path1, context);
//        UserDao.addBusiness(db, "tianpin", "123456", "甜品", "我超甜！！！", "二食堂一楼", Path3);

        //2.创建用户表
        db.execSQL("drop table if exists d_user");//如果这表存在则删除
        db.execSQL("create table d_user(s_id varchar(20) primary key," +//账号
                "s_pwd varchar(20)," +//密码
                "s_name varchar(20)," +//昵称
                "s_sex varchar(200)," +//性别
                "s_address varchar(20)," +//地址
                "s_phone varchar(20)," +//联系方式
                "s_img varchar(255))");//头像
        String userPath1 = Tools.getImagePath() + "/" + uuid + "dadaliya.jpg";
        Tools.saveByteArrayAsPng(getImg(R.drawable.dadaliya), userPath1, context);
        UserDao.addUser(db, "zzy", "123456", "吃货张三", "男", "辽宁省沈阳市", "15311223344", userPath1);

        String userPath2 = Tools.getImagePath() + "/" + uuid + "miku.jpg";
        Tools.saveByteArrayAsPng(getImg(R.drawable.miku), userPath2, context);
        UserDao.addUser(db, "wc", "123456", "吃货吴四", "男", "辽宁省沈阳市", "13011223344", userPath2);


        //3.创建商品表
        db.execSQL("drop table if exists d_food");//如果这表存在则删除
        db.execSQL("create table d_food(s_food_id varchar(20)primary key ," +//食物ID
                "s_business_id varchar(20) ," +//商家ID
                "s_food_name varchar(20)," +//食物名
                "s_food_des varchar(200)," +//食物描述
                "s_food_price varchar(20)," +//食物价格
                "s_food_img varchar(255))");//食物图片

        //select * from d_food LEFT JOIN
        String hbPath = Tools.getImagePath() + "/" + uuid + "hanbao.png";
        Tools.saveByteArrayAsPng(getImg(R.drawable.hanbao), hbPath, context);
        String da[] = {"1", "maidanglao", "汉堡", "荤素搭配，营养美味.", "11", hbPath};
        db.execSQL("insert into d_food (s_food_id,s_business_id,s_food_name,s_food_des,s_food_price,s_food_img) values(?,?,?,?,?,?)", da);

        String stPath = Tools.getImagePath() + "/" + uuid + "shutiao.png";
        Tools.saveByteArrayAsPng(getImg(R.drawable.shutiao), stPath, context);
        String da1[] = {"2", "maidanglao", "薯条", "上好的土豆", "8", stPath};
        db.execSQL("insert into d_food (s_food_id,s_business_id,s_food_name,s_food_des,s_food_price,s_food_img) values(?,?,?,?,?,?)", da1);

        String st2Path = Tools.getImagePath() + "/" + uuid + "shutiao2.png";
        Tools.saveByteArrayAsPng(getImg(R.drawable.shutiao_2), st2Path, context);
        String da2[] = {"3", "maidanglao", "2份薯条", "快乐翻倍！！！", "14", st2Path};
        db.execSQL("insert into d_food (s_food_id,s_business_id,s_food_name,s_food_des,s_food_price,s_food_img) values(?,?,?,?,?,?)", da2);

        String sdPath = Tools.getImagePath() + "/" + uuid + "shengdai.png";
        Tools.saveByteArrayAsPng(getImg(R.drawable.shengdai), sdPath, context);
        String da3[] = {"4", "mixuebingcheng", "圣代", "很冰！！！", "6", sdPath};
        db.execSQL("insert into d_food (s_food_id,s_business_id,s_food_name,s_food_des,s_food_price,s_food_img) values(?,?,?,?,?,?)", da3);

        String tt2Path = Tools.getImagePath() + "/" + uuid + "tiantong2.png";
        Tools.saveByteArrayAsPng(getImg(R.drawable.tiantong_2), tt2Path, context);
        String da4[] = {"5", "mixuebingcheng", "2份甜筒", "双倍快乐！！！", "4", tt2Path};
        db.execSQL("insert into d_food (s_food_id,s_business_id,s_food_name,s_food_des,s_food_price,s_food_img) values(?,?,?,?,?,?)", da4);

        String cmPath = Tools.getImagePath() + "/" + uuid + "caomeiguocha.png";
        Tools.saveByteArrayAsPng(getImg(R.drawable.caomeiguocha), cmPath, context);
        String da5[] = {"6", "mixuebingcheng", "草莓果茶", "热爱一百零五度的你~", "8", cmPath};
        db.execSQL("insert into d_food (s_food_id,s_business_id,s_food_name,s_food_des,s_food_price,s_food_img) values(?,?,?,?,?,?)", da5);

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
        String img[] = {userPath1, businessPath1};
        String img2[] = {userPath1, businessPath2};
        // select * from d_orders
        db.execSQL("insert into d_orders values('1','2024-4-07 11:56','zzy','吃货张三',?,'maidanglao','麦当劳',?,'1','1','张某人-堂食-15311223344')", img);
        db.execSQL("insert into d_orders values('2','2024-5-07 10:40','zzy','吃货张三',?,'mixuebingcheng','蜜雪冰城',?,'2','1','张某人-校内二舍617-15311223344')", img2);
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
        db.execSQL("insert into d_address values('2','zzy','张某人','堂食','15311223344')");
        db.execSQL("insert into d_address values('3','wc','吴某','校内二舍617','13011223344')");

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

        String mdls[] = {hbPath};
        String mxsd[] = {sdPath};
        String mxtt[] = {tt2Path};

//        订单1
        db.execSQL("insert into d_order_detail values('1','1','汉堡','荤素搭配，营养美味.','11',?,'2024-4-07','2')", mdls);
//        db.execSQL("insert into d_order_detail values('1','1','东北麻辣烫2','不吃就会后悔的麻辣烫2','19.86',?,'2023-10-07','15')", mlts);
//        db.execSQL("insert into d_order_detail values('1','1','东北麻辣3','不吃就会后悔的麻辣烫3','19.86',?,'2023-10-07','20')", mlts);
//        订单2
        db.execSQL("insert into d_order_detail values('2','4','圣代','很冰！！！','6',?,'2024-5-07','1')", mxsd);
        db.execSQL("insert into d_order_detail values('2','5','2份甜筒','双倍快乐！！！','4',?,'2023-12-07','13')", mxtt);
//        db.execSQL("insert into d_order_detail values('2','1','东北麻辣烫3','不吃就会后悔的麻辣烫3','19.86',?,'2023-12-07','14')", mlt);


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
        String imgz[] = {userPath1, hbPath};
        db.execSQL("insert into d_comments values('1','zzy','吃货张三',?,'这个汉堡是真的好吃，我要给五分好评！',?,'2024-4-8 10:56','maidanglao','5')", imgz);
//        db.execSQL("insert into d_comments values('2','zzy','吃货张三',?,'怕你骄傲给三分','','2023-10-25 10:56','zhujiaofan','3')", imgz);

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
