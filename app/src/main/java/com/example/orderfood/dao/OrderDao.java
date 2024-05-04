package com.example.orderfood.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.orderfood.bean.OrderBean;
import com.example.orderfood.bean.OrderDetail;
import com.example.orderfood.db.DBUtil;

import java.util.ArrayList;
import java.util.List;

public class OrderDao {


    public static SQLiteDatabase db= DBUtil.db;

    /**
     * 通过商家的账号，进行查询，这个商家的所有的订单内容
     * @param id
     */

    public static List<OrderBean> getAllOrderByBusiness(String id){
        Cursor rs = db.rawQuery("select * from d_orders where s_order_business=? and s_order_sta='1'", new String[]{id});
        List<OrderBean> list=new ArrayList<>();
        while(rs.moveToNext()){
            OrderBean detail = new OrderBean(rs.getString(0), rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));
            list.add(detail);
        }
        return list;
    }

    /**
     * 获取当前账号的所有完成订单
     * @param id
     * @return
     */

    public static List<OrderBean> getAllOrderByBusinessFinish(String id){

        Cursor rs = db.rawQuery("select * from d_orders where s_order_business=? and s_order_sta!='1'", new String[]{id});
        List<OrderBean> list=new ArrayList<>();
        while(rs.moveToNext()){
            OrderBean detail = new OrderBean(rs.getString(0), rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));
            list.add(detail);
        }
        return list;
    }


    /**
     * 实现订单编号搜索功能
     * @param id
     * @param orderId
     * @return
     */
    public static List<OrderBean> getAllOrderByBusiness(String id,String orderId){

        String temp="%"+orderId+"%";
        String sql="select d_orders.* from d_orders " +
                "LEFT JOIN d_order_detail   on d_orders.s_order_business_id=d_order_detail.s_order_business_id " +
                "where d_orders.s_order_id like ? or" +
                " d_order_detail.s_food_name like ? or" +
                " d_order_detail.s_food_des like ? and" +
                " d_orders.s_order_sta='1' and " +
                " d_orders.s_order_business=?  " +
                "GROUP BY d_orders.s_order_id ";


        Cursor rs = db.rawQuery(sql, new String[]{id,temp,temp,temp});
        List<OrderBean> list=new ArrayList<>();
        while(rs.moveToNext()){
            OrderBean detail = new OrderBean(rs.getString(0), rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));
            list.add(detail);
        }
        return list;
    }

    public static List<OrderBean> getAllOrderByBusinessFinish(String id,String orderId){

        //查询的时候传过来的可能是单号

        String temp="%"+orderId+"%";
        String sql="select d_orders.* from d_orders " +
                "LEFT JOIN d_order_detail   on d_orders.s_order_business_id=d_order_detail.s_order_business_id " +
                "where d_orders.s_order_id like ? or" +
                " d_order_detail.s_food_name like ? or" +
                " d_order_detail.s_food_des like ? and" +
                " d_orders.s_order_sta!='1' and " +
                " d_orders.s_order_business=?  " +
                "GROUP BY d_orders.s_order_id ";


        Cursor rs = db.rawQuery(sql, new String[]{id,temp,temp,temp});
        List<OrderBean> list=new ArrayList<>();
        while(rs.moveToNext()){
            OrderBean detail = new OrderBean(rs.getString(0), rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));
            list.add(detail);
        }
        return list;
    }



    /**
     * 获取订单详情内容
     * @param id
     */
    public static  List<OrderDetail> getAllOrderDetailByBusiness(String id){

        Cursor rs = db.rawQuery("select * from d_order_detail where s_order_business_id=?", new String[]{id});
        List<OrderDetail> list=new ArrayList<>();
        while(rs.moveToNext()){
            OrderDetail detail = new OrderDetail(rs.getString(0), rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
            list.add(detail);
        }
        return list;

    }


    /**
     * 这个是更改订单的状态
     * @param id
     * @param sta
     * @return
     */

    public static int updateOrder(String id,String sta){

        try {
            db.execSQL("update d_orders set s_order_sta =? where s_order_id=?",new String[]{sta,id});
            return 1;
        }catch (Exception e){
            return 0;
        }


    }


    /**
     * 添加订单
     * @param data
     * @return
     */


    public  static int addOrder(String ...data){

        try{
            db.execSQL("insert into d_orders values(?,?,?,?,?,?,?,?,?,?,?)",data);
            return 1;
        }catch (Exception e){
            return 0;
        }


    }

    /**
     * 添加等待详情
     * @param data
     * @return
     */
    public  static int addOrderDetail(String ...data){

        try{
            db.execSQL("insert into d_order_detail values(?,?,?,?,?,?,?,?)",data);
            return 1;
        }catch (Exception e){
            return 0;
        }


    }










    /**
     * 实现订单编号搜索功能
     * @param
     * @param orderId
     * @return
     */
    public static List<OrderBean> getAllOrderUser(String orderId){

        String temp="%"+orderId+"%";
        String sql="select * from d_orders where s_order_user_id=? and s_order_sta='1'";


        Cursor rs = db.rawQuery(sql, new String[]{orderId});
        List<OrderBean> list=new ArrayList<>();
        while(rs.moveToNext()){
            OrderBean detail = new OrderBean(rs.getString(0), rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));
            list.add(detail);
        }
        return list;
    }

    /**
     * 实现订单编号搜索功能
     * @param
     * @param orderId
     * @return
     */
    public static List<OrderBean> getAllOrderUserFinish(String orderId){

        String temp="%"+orderId+"%";
        String sql="select * from d_orders where s_order_user_id=? and s_order_sta!='1'";


        Cursor rs = db.rawQuery(sql, new String[]{orderId});
        List<OrderBean> list=new ArrayList<>();
        while(rs.moveToNext()){
            OrderBean detail = new OrderBean(rs.getString(0), rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));
            list.add(detail);
        }
        return list;
    }


    public static List<OrderBean> getAllOrderUserFinish(String orderId,String con){

        String temp="%"+con+"%";
        String sql="SELECT" +
                " d_orders.* " +
                "FROM" +
                " d_orders " +
                "LEFT JOIN d_order_detail ON d_orders.s_order_business_id = d_order_detail.s_order_business_id " +
                "WHERE " +
                "d_orders.s_order_user_id = ? " +
                "AND ( d_order_detail.s_food_name like ? or d_order_detail.s_food_des like ? or d_orders.s_order_id=?) and d_orders.s_order_sta!='1' GROUP BY d_orders.s_order_id";


        Cursor rs = db.rawQuery(sql, new String[]{orderId,temp,temp,con});



        List<OrderBean> list=new ArrayList<>();
        while(rs.moveToNext()){
            OrderBean detail = new OrderBean(rs.getString(0), rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));
            list.add(detail);
        }
        return list;
    }


    /**
     * 根据订单来查
     * @param orderId
     * @param con
     * @return
     */

    public static List<OrderBean> getAllOrderUser(String orderId,String con){

        String temp="%"+con+"%";
        String sql="SELECT" +
                " d_orders.* " +
                "FROM" +
                " d_orders " +
                "LEFT JOIN d_order_detail ON d_orders.s_order_business_id = d_order_detail.s_order_business_id " +
                "WHERE " +
                "d_orders.s_order_user_id = ? " +
                "AND ( d_order_detail.s_food_name like ? or d_order_detail.s_food_des like ? or d_orders.s_order_id=?) and d_orders.s_order_sta='1' GROUP BY d_orders.s_order_id";


        Cursor rs = db.rawQuery(sql, new String[]{orderId,temp,temp,con});
        List<OrderBean> list=new ArrayList<>();
        while(rs.moveToNext()){
            OrderBean detail = new OrderBean(rs.getString(0), rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));
            list.add(detail);
        }
        return list;
    }






}
