package com.example.leetcode_api_request;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class Storage {


        private static final String TAG ="STORAGE" ;
        //        static Context context = MainActivity.getContext();
        WeakReference weakRef;
        static SharedPreferences sp = null;
        static ArrayAdapter<String> arrAdpt;
        static CustAdapter adapt;
        public static ArrayList<String> users = new ArrayList<>();

        public static ArrayList<CustPair<String , Integer>> users2 = new ArrayList<>();


        public Storage(MainActivity activity){
                // 1662843965 default epoch

//                users.add("votrubac");
//                users.add("pete1302");
                weakRef = new WeakReference(activity);
                MainActivity act = (MainActivity) weakRef.get();
                sp = act.getSharedPreferences("spUser", Context.MODE_PRIVATE);


                if(chkShrPref()){
                        String userData = sp.getString("User-Data" , null);
                        if( userData.equals(null) ){
                                users2 = getDefaultList();
                        }else{
//                                users2 = new ArrayList<CustPair<String,Integer>>(userData);
                                users2 = loadShredPref();
                        }

                }

//                CustPair<String , Integer> pair = new CustPair<>("pete1302", 1662843965);
//                CustPair<String , Integer> pair2 = new CustPair<>("votrubac", 1662843965);
//                users2.add(pair);
//                users2.add(pair2);

//                arrAdpt = new ArrayAdapter<String>();
//                loadData();
                ArrayAdapter<String> adapt2 = new ArrayAdapter(act , android.R.layout.simple_list_item_1 , users);
                 adapt = new CustAdapter(act , R.layout.cust_list_view_2 , users2);
                ListView lvUsers = act.findViewById(R.id.lvUser);
                lvUsers.setAdapter(adapt);
//                lvUsers.setAdapter(adapt2);

                lvUsers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String toast_text = adapterView.getItemAtPosition(i).toString();
                                Toast.makeText(act, toast_text + " DELETED", Toast.LENGTH_SHORT).show();
                                users2.remove(i);
                                adapt.notifyDataSetChanged();
                                saveToPref();
                                return true;
                        }
                });
        }

        private ArrayList<CustPair<String, Integer>> loadShredPref() {
                String sData =sp.getString("User-Data" , null);
                if(!sData.equals(null)){
                        Gson gData = new Gson();
                        return gData.fromJson(sData , new TypeToken<ArrayList<CustPair<String , Integer>>>(){}.getType());
                }
                return null;
        }
        private static void saveToPref(){
                Gson gData = new Gson();
                String sData = gData.toJson(users2);
                SharedPreferences.Editor edit = sp.edit();
                if(sp.contains("User-Data")){
                        edit.remove("User-Data");
                }
                edit.putString("User-Data" , sData);
                edit.commit();
        }

        private static ArrayList<CustPair<String, Integer>> getDefaultList() {

                return null;
        }

        private static boolean chkShrPref(){
                if(sp.contains("User-Data")){
                        return true;
                }
                return false;
        }
//        private static void syncPref(){
//                Gson gData = new Gson();
//                String sData = gData.toJson(users2);
//                SharedPreferences.Editor edit = sp.edit();
//                if(sp.contains("User-Data")){
//                        edit.remove("User-Data");
//                }
//                edit.putString("User-Data" , sData);
//                edit.commit();
//        }

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
                adapt.notifyDataSetChanged();
        }

        public static void saveData2(String userName) {

                Integer currEpoch = Math.toIntExact(System.currentTimeMillis() / 1000);
                CustPair<String , Integer> userData = new CustPair<>(userName , currEpoch);
                users2.add(userData);
                adapt.notifyDataSetChanged();
                saveToPref();
        }

        private static void syncList(){

                String data = users2.toString();
                SharedPreferences.Editor edit = sp.edit();
                edit.remove("User-Data");
                edit.putString("User-Data" , data);
                edit.commit();
        }

        public static boolean chkExist2(String userName){
                for (int i = 0; i < users2.size() ; i++) {
                        Log.d(TAG, "chkExist2:  " + userName + ' ' + users2.get(i).getL());
                        if(users2.get(i).getL().equals(userName)){
                                return true;
                        }
                }
                return false;
        }

        public static String loadData(){
                SharedPreferences.Editor edit = sp.edit();
                String userList = sp.getString("User-Data" , "NULL");
                return userList;
        }

        public static boolean chkExist(String userName){
                if( users2.contains(userName)){
                        return true;
                }
                return false;
        }

        public static void updateTime(String userName , Long time) {
                if (time.equals(null) || time.equals(0)){
                        time = (System.currentTimeMillis() / 1000);
                }
                for (int i = 0; i < users2.size() ; i++) {
                        if(users2.get(i).getL() == userName){
                                users2.get(i).setR(time.intValue());
                                Log.d(TAG, "updateTime: "+ users2.get(i).getL() + " "
                                + timeConv(users2.get(i).getR().longValue()));
                        }
                }
                adapt.notifyDataSetChanged();
                // TODO: 21-09-2022
        }

        public static Long getUserSub(String userName){

                for (int i = 0; i < Storage.users2.size(); i++) {

                        String user = Storage.users2.get(i).getL();
                        if(user.equals(userName)){
                                return Storage.users2.get(i).getR().longValue();
                        }
                }
                return 0L;
        }
        public static String timeConv(Long timeLong){
                String date = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                        .format(new java.util.Date (timeLong*1000));

                return date;
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


