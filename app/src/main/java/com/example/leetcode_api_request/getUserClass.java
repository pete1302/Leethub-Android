package com.example.leetcode_api_request;

import static android.content.ContentValues.TAG;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class getUserClass extends AsyncTask<Void, Void , String> {

    private WeakReference weakRef;

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
    }

    @Override
    protected String doInBackground(Void... voids) {

        String userName = "pete1302";


//        MainActivity activity = (MainActivity) weakRef.get();
//        userName = activity.findViewById(R.id.evUsername).get;


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

        if(Storage.saveData(chkData(s))){
            Log.e(TAG, "onPostExecute: YOI");
        }else{
            Log.e(TAG, "onPostExecute: NOI");
        }

    }

    //------------------------//------------------------//------------------------

    private static JSONObject chkData(String s){
        JSONObject jsonData = null;
        if( s == null ){
            s = "{\"errors\":\"null\"}";
        }
        try {
            jsonData = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonData.has("errors")) {
            Log.e( "ERROR", "error in data'");
        }
        return jsonData;
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