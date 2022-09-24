package com.example.leetcode_api_request;

import static android.content.ContentValues.TAG;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class asyncUpdate extends AsyncTask<Void ,Void , HashMap> {

    private static WeakReference weakRef;
    private static ArrayList<CustPair<String, Integer>> users ;



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.i(TAG, "onPreExecute: ");
//        MainActivity activity = (MainActivity) weakRef.get();
//        if( activity ==null || activity.isFinishing()){
//            return;
//        }
//        new Storage();
        users = Storage.users2;
    }

    @Override
    protected HashMap doInBackground(Void... voids) {

        String userName;
        HashMap<String, String> userStat = new HashMap<>();
        for (int i = 0; i < users.size(); i++) {
            userName = users.get(i).getL();
            Log.d(TAG, "doInBackground: "+ userName);

            //----

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
            userStat.put(userName , res[0]);
        }

        return userStat;
    }

    @Override
    protected void onPostExecute(HashMap hashMap) {
        super.onPostExecute(hashMap);
//        final TextView tvLastChk = MainfindViewById(R.id.tvLastChk);

        HashMap timeList  = parser(hashMap);

        notifClass.notifListParse(chkSubmission(timeList));

        Log.d(TAG, "onPostExecute: END");
        
    }

    //------------------//------------------//------------------//------------------

    private static String queryGen(String username, String qid){

        String baseUrl =
//                "https://leet-api-2.herokuapp.com/";
                "https://api-2-git.herokuapp.com/api/user/";
        baseUrl += username + '/' + qid + '/';
        return baseUrl;
    }
    private static HashMap parser(HashMap<String,String> userStat){


        HashMap<String , Long> timeList = new HashMap<>();

        Iterator it = userStat.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Log.d(TAG, "onPostExecute: "+ (pair.getKey() + " = " + pair.getValue()));
            String data = pair.getValue().toString();
            JSONObject jsonData = null;
            try{
                jsonData = new JSONObject(data);
            }catch (JSONException e){
                e.printStackTrace();
            }
            Boolean flg = false;
            if(jsonData.has("errors")){
                Log.e(TAG, "parser: ERROR" + pair.getKey() );
            }else{
                try {
                    flg = getTime(jsonData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if( flg ){
                try {
                    timeList.put(pair.getKey().toString() , getTime2(jsonData));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            it.remove();
        }
        return timeList;

    }


    private static boolean getTime(JSONObject jsonData) throws JSONException {

        return true;
//        Long time = null;
//        long epoch = System.currentTimeMillis()/1000;
//        if(jsonData.has("data")){
//            JSONObject data = (JSONObject) jsonData.getJSONObject("data");
//            if( data.has("recentAcSubmissionList")){
//                JSONArray arr = data.getJSONArray("recentAcSubmissionList");
//                if( arr.length() == 1 ){
//                    JSONObject ele = (JSONObject) arr.get(0);
//                    if( ele.has("timestamp")){
//                        time = ele.getLong("timestamp");
//                    }
//                }
//            }
//        }
//        Log.i(TAG, "getTime: "+ epoch + "--" + time);
//        if( time != null){
//            if(time > epoch){
//                Log.i(TAG, "getTime: DONE SOMETHING $$$$");
//                return true;
//            }else{
//                Log.i(TAG, "getTime: NAYYYYYYYYYYYYYYYYY");
////                statusList.add(time.toString());
//                return false;
//            }
//        }else {
//            Log.e(TAG, "getTime: ERROR time => NULL");
//            return false;
//        }
    }

    private static Long getTime2(JSONObject jsonData) throws JSONException {

        Long time = null;
        long epoch = System.currentTimeMillis() / 1000;
        if (jsonData.has("data")) {
            JSONObject data = (JSONObject) jsonData.getJSONObject("data");
            if (data.has("recentAcSubmissionList")) {
                JSONArray arr = data.getJSONArray("recentAcSubmissionList");
                if (arr.length() == 1) {
                    JSONObject ele = (JSONObject) arr.get(0);
                    if (ele.has("timestamp")) {
                        time = ele.getLong("timestamp");
                    }
                }
            }
        }
        return time;
    }
    private static HashMap chkSubmission(HashMap<String , String> timeList){

        Iterator it = timeList.entrySet().iterator();
        HashMap<String , Boolean > notifList = new HashMap<>();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Log.d(TAG, "onPostExecute: " + (pair.getKey() + " = " + pair.getValue()));
            String userName = pair.getKey().toString();
            Long currSub = (Long) pair.getValue();
            Long subTime = Storage.getUserSub(userName);
            if( subTime != 0) {
                if( currSub != 0){
                    if( subTime < currSub){
                        Log.d(TAG, "chkSubmission: TRUE -> "+ userName);
                        String notifData = userName + " at ->  " + Storage.timeConv(currSub);
                        notifList.put(notifData , true);
                    }
                    Storage.updateTime(userName , currSub);
                }
            }else{
                Log.e(TAG, "chkSubmission: ERROR IN TIME COMPARISON"+ subTime +' '+ currSub);
            }
            it.remove();
        }
        return notifList;

    }

}
