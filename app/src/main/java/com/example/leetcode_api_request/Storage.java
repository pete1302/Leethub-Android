package com.example.leetcode_api_request;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;

import java.util.ArrayList;


public class Storage {


//        static Context context = MainActivity.getContext();
//        WeakReference weakRef;
        static SharedPreferences sp = null;
        static ArrayAdapter<String> arrAdpt;
        public static ArrayList<String> users = new ArrayList<>();

        public Storage(){
                // 1662843965 dummy epoch
                users.add("votrubac");
                users.add("pete1302");
//                MainActivity act = (MainActivity) weakRef.get();
                sp = MainActivity.getContext().getSharedPreferences("spUser", Context.MODE_PRIVATE);
//                arrAdpt = new ArrayAdapter<String>();
                loadData();
        }

        public static void saveData(String userName){

//                String userData = jsonData.toString();
//
//                if(!chkExist(jsonData)){
//                        SharedPreferences.Editor edit = sp.edit();
//                        edit.putString("USERLIST" , userData);
//                        edit.commit();
//                        return true;
//                }else{
//                     return false;
//                }
                users.add(userName);
                arrAdpt.notifyDataSetChanged();

        }
        public static String loadData(){
                SharedPreferences.Editor edit = sp.edit();
                String userList = sp.getString("USERLIST" , "NULL");
                return userList;
        }

        public static boolean chkExist(String userName){
                if( users.contains(userName)){
                        return true;
                }
                return false;
        }

        private static void syncList(ArrayList<String> list){

                String data = list.toString();
                SharedPreferences.Editor edit = sp.edit();
                edit.remove("USERLIST");
                edit.putString("USERLIST" , data);
                edit.commit();
        }
        public static Long getUserSub(String userName){



                return null;
        }



//                String user = evQid.getText().toString();
//                Toast.makeText(this, user + " added", Toast.LENGTH_SHORT).show();
//                evQid.setText("");
//                ls.add(user);
//                arrayAdtp.notifyDataSetChanged();
//
//                String toast_text = adapterView.getItemAtPosition(i).toString();
//                Toast.makeText(MainActivity.this, toast_text + " DELETED", Toast.LENGTH_SHORT).show();
//                ls.remove(i);
//                arrayAdtp.notifyDataSetChanged();


}


