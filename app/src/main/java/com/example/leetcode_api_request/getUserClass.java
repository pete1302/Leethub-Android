package com.example.leetcode_api_request;

import static android.content.ContentValues.TAG;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class getUserClass extends AsyncTask<String, Void , String> {

    private WeakReference weakRef;
    private static String userName;

    getUserClass(MainActivity activity) {
        weakRef = new WeakReference<MainActivity>(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.i(TAG, "onPreExecute: ");
        MainActivity activity = (MainActivity) weakRef.get();
        if( activity ==null || activity.isFinishing()){
            return;
        }
//        MainActivity activity = (MainActivity) weakRef.get();
//        userName = activity.findViewById(R.id.evUsername).get;
        userName ="pete1302";

    }

    @Override
    protected String doInBackground(String... strings) {

//        String userName = "pete1302";

        final String[] res = new String[1];
//            Log.i(TAG, strings[0]);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(queryGen(userName , "6" ))
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            final String resData = response.body().string();
            res[0] = resData;
//                Log.i("DATA", res[0]);
//            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "run: OKHTTP ERR");
            res[0] = "{\"errors\":\"run: OKHTTP ERR\"}";

        }

        Log.i(TAG, "run: res " + res[0]);
        return res[0];


    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(chkData(s) && !Storage.chkExist(userName)){
            Storage.saveData(userName);
        }
        MainActivity activity = (MainActivity) weakRef.get();
        Toast.makeText(activity, "SAVED -----> " + userName, Toast.LENGTH_LONG).show();

//        if(Storage.saveData(chkData(s))){
//            Log.e(TAG, "onPostExecute: YOI");
//        }else{
//            Log.e(TAG, "onPostExecute: NOI");
//        }

    }



    //------------------------//------------------------//------------------------

    private static boolean chkData(String s){
        JSONObject jsonData = null;
        if( s == null ){
            s = "{\"errors\":\"null\"}";
        }
        try {
            jsonData = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        if (jsonData.has("errors")) {
            Log.e( "ERROR", "error in data");
            return false;
        }else{
            return true;
        }

    }
//    private static void saveData(JSONObject jsonData){
//
//        if(jsonData.get)
//
//    }

    private static String queryGen(String username, String qid){

        String baseUrl =
//                "https://leet-api-2.herokuapp.com/";
                "https://api-2-git.herokuapp.com/api/user/";
        baseUrl += username + '/' + qid + '/';
        return baseUrl;
    }
}
