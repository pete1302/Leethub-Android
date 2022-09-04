package com.example.leetcode_api_request;

import static android.content.ContentValues.TAG;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class getUserUpdate extends AsyncTask<String , Void , String> {

    private static WeakReference weakRef;
    private static String userName;

    getUserUpdate(MainActivity activity) {
        weakRef = new WeakReference<MainActivity>(activity);
    }
    //------------
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
    protected String doInBackground(String... strings) {

        String userName = "pete1302";
//        userName = strings[0];

        final String[] res = new String[1];
//            Log.i(TAG, strings[0]);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(queryGen(userName , "7" ))
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
        MainActivity activity = (MainActivity) weakRef.get();
        JSONObject jsonData = null;
        try{
            jsonData = new JSONObject(s);
        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(activity, "ERROR IN JSON CONVERSION", Toast.LENGTH_SHORT).show();
        }
        if(jsonData.has("errors")){
            Log.e(TAG, "onPostExecute: ERROR IN JSON");
        }else{
            try {
                getTime(jsonData);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, "onPostExecute: ");
            }
        }

    }
    //-------------------//-------------------//-------------------
    private static String queryGen(String username, String qid){

        String baseUrl =
//                "https://leet-api-2.herokuapp.com/";
                "https://api-2-git.herokuapp.com/api/user/";
        baseUrl += username + '/' + qid + '/';
        return baseUrl;
    }

    private static boolean getTime(JSONObject jsonData) throws JSONException {

        Long time = null;
        long epoch = System.currentTimeMillis()/1000;
        if(jsonData.has("data")){
            JSONObject data = (JSONObject) jsonData.getJSONObject("data");
            if( data.has("recentAcSubmissionList")){
                JSONArray arr = data.getJSONArray("recentAcSubmissionList");
                if( arr.length() == 1 ){
                    JSONObject ele = (JSONObject) arr.get(0);
                    if( ele.has("timestamp")){
                        time = ele.getLong("timestamp");
                    }
                }
            }
        }
        Log.i(TAG, "getTime: "+ epoch + "--" + time);
        if( time != null){
            if(time > epoch){
                Log.i(TAG, "getTime: DONE SOMETHING $$$$");
                return true;
            }else{
                Log.i(TAG, "getTime: NAYYYYYYYYYYYYYYYYY");
                return false;
            }
        }else {
            Log.e(TAG, "getTime: ERROR time => NULL");
            return false;
        }

    }
}

