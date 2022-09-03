package com.example.leetcode_api_request;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

import java.util.ArrayList;


public class Storage {


        static Context context = MainActivity.getContext();
        static SharedPreferences sp = null;
        public static ArrayList<String> userList = new ArrayList<String>();

        ArrayList<String> users = new ArrayList<>();

        public Storage(){
                users.add("votrubac");
                users.add("pete1302");
                sp = context.getSharedPreferences("spUser", context.MODE_PRIVATE);
                loadData();
        }
        public static boolean saveData(JSONObject jsonData){

                String userData = jsonData.toString();

                if(!chkExist(jsonData)){
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putString("USERLIST" , userData);
                        edit.commit();
                        return true;
                }else{
                     return false;
                }

        }
        public static String loadData(){
                SharedPreferences.Editor edit = sp.edit();
                String userList = sp.getString("USERLIST" , "NULL");
                return userList;
        }

        private static boolean chkExist(JSONObject jsonData){

                return true;
        }




}


