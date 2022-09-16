package com.example.leetcode_api_request;

import android.content.SharedPreferences;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class Storage {


//        static Context context = MainActivity.getContext();
        WeakReference weakRef;
        static SharedPreferences sp = null;
        static ArrayAdapter<String> arrAdpt;
        public static ArrayList<String> users = new ArrayList<>();

        public static ArrayList<CustPair<String , Integer>> users2 = new ArrayList<>();


        public Storage(MainActivity activity){
                // 1662843965 dummy epoch

                users.add("votrubac");
                users.add("pete1302");
                weakRef = new WeakReference(activity);
                CustPair<String , Integer> pair = new CustPair<>("pete1302", 1662843965);
                CustPair<String , Integer> pair2 = new CustPair<>("votrubac", 1662843965);

                users2.add(pair);
                users2.add(pair2);

                MainActivity act = (MainActivity) weakRef.get();
//                sp = MainActivity.getContext().getSharedPreferences("spUser", Context.MODE_PRIVATE);
//                arrAdpt = new ArrayAdapter<String>();
//                loadData();
                ArrayAdapter<String> adapt2 = new ArrayAdapter(act , android.R.layout.simple_list_item_1 , users);
                CustAdapter adapt = new CustAdapter(act , R.layout.cust_list_view_2 , users2);
                ListView lvUsers = act.findViewById(R.id.lvUser);
                lvUsers.setAdapter(adapt);
//                lvUsers.setAdapter(adapt2);

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

        public static void saveData2(String userName) {

                Integer currEpoch = Math.toIntExact(System.currentTimeMillis() / 1000);;
                CustPair<String , Integer> userData = new CustPair<>(userName , currEpoch);
                users2.add(userData);
                arrAdpt.notifyDataSetChanged();
        }

        private static void syncList(){

                String data = users2.toString();
                SharedPreferences.Editor edit = sp.edit();
                edit.remove("USERLIST");
                edit.putString("USERLIST" , data);
                edit.commit();
        }

        public static boolean chkExist2(String userName){
                for (int i = 0; i < users2.size() ; i++) {
                        if(users2.get(i).getL() == userName){
                                return true;
                        }
                }
                return false;
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

        public static void updateTime(String userName , Long time) {
                for (int i = 0; i < users2.size() ; i++) {
                        if(users2.get(i).getL() == userName){
                                users2.get(i).setR(time.intValue());
                        }
                }
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


