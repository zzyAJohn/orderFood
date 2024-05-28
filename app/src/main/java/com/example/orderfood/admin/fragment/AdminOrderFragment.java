package com.example.orderfood.admin.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.orderfood.R;
import com.example.orderfood.admin.adapter.OrderAdminAdapter;
import com.example.orderfood.bean.OrderBean;
import com.example.orderfood.dao.OrderDao;


import java.util.List;


public class AdminOrderFragment extends Fragment {


    String account;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_admin_order, container, false);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        account = sharedPreferences.getString("account", "");


        new Thread(new Runnable() {
            @Override
            public void run() {
                initializeViews();
            }
        }).start();

        return rootView;
    }


    private void initializeViews() {
        //获取当前账号,进行查询
        ListView foodList = rootView.findViewById(R.id.user_list_view);

        //管理员获取所有订单
        List<OrderBean> originalItems = OrderDao.getAllOrder();

        if (originalItems == null || originalItems.size() == 0) {
            foodList.setAdapter(null);
        } else {
            OrderAdminAdapter adapter = new OrderAdminAdapter(getContext(), originalItems);
            foodList.setAdapter(adapter);
        }

        //实现搜索功能

        SearchView search = rootView.findViewById(R.id.user_search_search);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (query.equals("")) {
                    ListView foodList = rootView.findViewById(R.id.user_list_view);
                    List<OrderBean> originalItems = OrderDao.getAllOrderUser(account);

                    if (originalItems == null || originalItems.size() == 0) {
                        foodList.setAdapter(null);
                    } else {
                        OrderAdminAdapter adapter = new OrderAdminAdapter(getContext(), originalItems);
                        foodList.setAdapter(adapter);
                    }
                } else {
                    ListView foodList = rootView.findViewById(R.id.user_list_view);
                    List<OrderBean> originalItems = OrderDao.getAllOrderUser(account, query);

                    if (originalItems == null || originalItems.size() == 0) {
                        foodList.setAdapter(null);
                    } else {
                        OrderAdminAdapter adapter = new OrderAdminAdapter(getContext(), originalItems);
                        foodList.setAdapter(adapter);
                    }
                }


                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")) {
                    ListView foodList = rootView.findViewById(R.id.user_list_view);
                    List<OrderBean> originalItems = OrderDao.getAllOrderUser(account);

                    if (originalItems == null || originalItems.size() == 0) {
                        foodList.setAdapter(null);
                    } else {
                        OrderAdminAdapter adapter = new OrderAdminAdapter(getContext(), originalItems);
                        foodList.setAdapter(adapter);
                    }
                } else {
                    ListView foodList = rootView.findViewById(R.id.user_list_view);
                    List<OrderBean> originalItems = OrderDao.getAllOrderUser(account, newText);

                    if (originalItems == null || originalItems.size() == 0) {
                        foodList.setAdapter(null);
                    } else {
                        OrderAdminAdapter adapter = new OrderAdminAdapter(getContext(), originalItems);
                        foodList.setAdapter(adapter);
                    }
                }

                return true;
            }
        });
    }


}