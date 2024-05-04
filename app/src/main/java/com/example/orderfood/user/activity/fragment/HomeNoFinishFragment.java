package com.example.orderfood.user.activity.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.orderfood.R;
import com.example.orderfood.adapter.UserFoodAdapter;
import com.example.orderfood.bean.FoodBean;
import com.example.orderfood.bean.OrderBean;
import com.example.orderfood.dao.OrderDao;
import com.example.orderfood.dao.UserDao;
import com.example.orderfood.user.adapter.OrderUserAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个是咱们用户购买的首页
 */
public class HomeNoFinishFragment extends Fragment {

    String account;
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_user_no_finish, container, false);
        SharedPreferences sharedPreferences= getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        account=sharedPreferences.getString("account","");




        new Thread(new Runnable(){
            @Override
            public void run() {
                initializeViews();
            }
        }).start();



        return rootView;
    }

    private void initializeViews(){
        //获取当前账号,进行查询
        ListView foodList = rootView.findViewById(R.id.user_list_view);
        List<OrderBean> originalItems = OrderDao.getAllOrderUser(account);

        if(originalItems==null||originalItems.size()==0){
            foodList.setAdapter(null);
        }else{
            OrderUserAdapter adapter = new OrderUserAdapter(getContext(),originalItems);
            foodList.setAdapter(adapter);
        }

        //实现搜素功能

        SearchView search = rootView.findViewById(R.id.user_search_search);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(query.equals("")){
                    ListView foodList = rootView.findViewById(R.id.user_list_view);
                    List<OrderBean> originalItems = OrderDao.getAllOrderUser(account);

                    if(originalItems==null||originalItems.size()==0){
                        foodList.setAdapter(null);
                    }else{
                        OrderUserAdapter adapter = new OrderUserAdapter(getContext(),originalItems);
                        foodList.setAdapter(adapter);
                    }
                }else{
                    ListView foodList = rootView.findViewById(R.id.user_list_view);
                    List<OrderBean> originalItems = OrderDao.getAllOrderUser(account,query);

                    if(originalItems==null||originalItems.size()==0){
                        foodList.setAdapter(null);
                    }else{
                        OrderUserAdapter adapter = new OrderUserAdapter(getContext(),originalItems);
                        foodList.setAdapter(adapter);
                    }
                }


                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")){
                    ListView foodList = rootView.findViewById(R.id.user_list_view);
                    List<OrderBean> originalItems = OrderDao.getAllOrderUser(account);

                    if(originalItems==null||originalItems.size()==0){
                        foodList.setAdapter(null);
                    }else{
                        OrderUserAdapter adapter = new OrderUserAdapter(getContext(),originalItems);
                        foodList.setAdapter(adapter);
                    }
                }else{
                    ListView foodList = rootView.findViewById(R.id.user_list_view);
                    List<OrderBean> originalItems = OrderDao.getAllOrderUser(account,newText);

                    if(originalItems==null||originalItems.size()==0){
                        foodList.setAdapter(null);
                    }else{
                        OrderUserAdapter adapter = new OrderUserAdapter(getContext(),originalItems);
                        foodList.setAdapter(adapter);
                    }
                }

                return true;
            }
        });
    }





}
