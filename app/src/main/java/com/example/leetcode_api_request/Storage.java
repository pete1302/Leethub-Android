package com.example.leetcode_api_request;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;


public class Storage {


        static Context context = MainActivity.getContext();
        static SharedPreferences sp = null;

        ArrayList<String> users = new ArrayList<>();

        public Storage(){
                users.add("votrubac");
                users.add("pete1302");
                sp = context.getSharedPreferences("spUser", context.MODE_PRIVATE);

        }
        public static void saveData(String userData){
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("USERLIST" , userData);
                edit.commit();
        }
        public static String loadData(){
                SharedPreferences.Editor edit = sp.edit();
                String userList = sp.getString("USERLIST" , "NULL");

                return userList;
        }




}


