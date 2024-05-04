package com.example.orderfood.user.activity.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.orderfood.R;
import com.example.orderfood.adapter.FoodAdapter;
import com.example.orderfood.adapter.UserFoodAdapter;
import com.example.orderfood.bean.FoodBean;
import com.example.orderfood.dao.UserDao;

import java.util.ArrayList;

/**
 * 这个是咱们用户购买的首页
 */
public class HomeFragment extends Fragment {

    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_user_home, container, false);


        //获取当前账号,进行查询
        ListView foodList = rootView.findViewById(R.id.user_list_view);
        ArrayList<FoodBean> originalItems = UserDao.getAllFood();//查找到所有数据

        if(originalItems==null||originalItems.size()==0){
            foodList.setAdapter(null);
        }else{
            UserFoodAdapter adapter = new UserFoodAdapter(getContext(), originalItems);
            foodList.setAdapter(adapter);
        }

        //实现搜素功能

        SearchView search = rootView.findViewById(R.id.user_search_search);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(query.equals("")){
                    ArrayList<FoodBean> originalItems = UserDao.getAllFood();//查找到所有数据

                    if(originalItems==null||originalItems.size()==0){
                        foodList.setAdapter(null);
                    }else{
                        UserFoodAdapter adapter = new UserFoodAdapter(getContext(), originalItems);
                        foodList.setAdapter(adapter);
                    }
                }else{
                    ArrayList<FoodBean> originalItems = UserDao.getAllFoodByName(query);//查找到所有数据

                    if(originalItems==null||originalItems.size()==0){
                        foodList.setAdapter(null);
                    }else{
                        UserFoodAdapter adapter = new UserFoodAdapter(getContext(), originalItems);
                        foodList.setAdapter(adapter);
                    }
                }


                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")){
                    ArrayList<FoodBean> originalItems = UserDao.getAllFood();//查找到所有数据

                    if(originalItems==null||originalItems.size()==0){
                        foodList.setAdapter(null);
                    }else{
                        UserFoodAdapter adapter = new UserFoodAdapter(getContext(), originalItems);
                        foodList.setAdapter(adapter);
                    }
                }else{
                    ArrayList<FoodBean> originalItems = UserDao.getAllFoodByName(newText);//查找到所有数据

                    if(originalItems==null||originalItems.size()==0){
                        foodList.setAdapter(null);
                    }else{
                        UserFoodAdapter adapter = new UserFoodAdapter(getContext(), originalItems);
                        foodList.setAdapter(adapter);
                    }
                }
                return true;
            }
        });
        return rootView;
    }
}
