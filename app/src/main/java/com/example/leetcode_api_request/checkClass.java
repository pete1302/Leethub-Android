package com.example.leetcode_api_request;

import android.util.Log;

public class checkClass {

    public static void testfx(){
        Log.i("TEST" , "TESTFX");
    }


    public static void chk(){
        Storage sto = new Storage();

        for (int i = 0; i <sto.users.size() ; i++) {
            Log.i("users-" , sto.users.get(i).toString());
        }

    }

    public static boolean chkValidUser(String userName) {


        return true;
    }
    public static String getUserData(String userName){



        return "";
    }

}
