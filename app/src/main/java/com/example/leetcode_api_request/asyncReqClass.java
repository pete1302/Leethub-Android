package com.example.leetcode_api_request;

import static android.content.ContentValues.TAG;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public abstract class asyncReqClass extends AsyncTask<String , Void , String> implements callBack{

    private WeakReference weakRef;

    asyncReqClass(MainActivity activity) {
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

//    @Override
//    protected String doInBackground(String... strings) {
//
//        Log.i(TAG, "doInBackground: " + strings[0]);
//        final String[] res = new String[1];
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(queryGen(strings[0], "1"))
//                .build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//                e.printStackTrace();
//                res[0] = "{\"errors\":\"failure fetching\"";
//                Log.i("ERROR", "fetching");
//            }
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    final String myResponse = response.body().string();
//                    res[0] = String.format("{\"data\":\"%s\"}", myResponse);
//                    Log.i("DATA", myResponse);
//                }
//            }
//        });
//        Log.i(TAG, "doInBackground: " + res[0]);
//        return res[0];
//    }


//    @Override
//    protected String doInBackground(String... strings) {
//        Log.i(TAG, "doInBackground: START");
//        threadClass th = new threadClass();
//        th.run();
//        Log.i(TAG, "doInBackground: END");
//        return "finshed";
//    }


//    @Override
//    protected String doInBackground(String... strings) {
//        new Thread(() -> {
//            final String[] res = new String[1];
//            Log.i(TAG, strings[0]);
//            OkHttpClient client = new OkHttpClient();
//            Request request = new Request.Builder()
//                    .url(queryGen(strings[0], "1"))
//                    .build();
//            client.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//
//                    e.printStackTrace();
//                    res[0] = "{\"errors\":\"failure fetching\"";
//                    Log.i("ERROR", "fetching");
//                }
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    if (response.isSuccessful()) {
//                        final String myResponse = response.body().string();
//                        res[0] = String.format("{\"data\":\"%s\"}", myResponse);
//                        Log.i("DATA", myResponse);
//                    }
//                }
//            });
//            Log.i(TAG, "doInBackground: " + res[0]);
//        }).start();
//        return "FINISHED";
//    }

//
//    @Override
//    protected String doInBackground(String... strings) {
//        Log.e(TAG, "doInBackground: Start");
//        threadClass th = new threadClass();
//        th.run();
//        Log.e(TAG, "doInBackground: END");
//        return "FINISHED";
//    }


    @Override
    protected String doInBackground(String... strings) {
        final String[] res = new String[1];
//            Log.i(TAG, strings[0]);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(queryGen(strings[0] , "6" ))
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
        }
        Log.i(TAG, "run: res " + res[0]);
        return res[0];

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.i(TAG, "onPostExecute: ");
        MainActivity activity = (MainActivity) weakRef.get();
        Toast.makeText(activity, "HTTP REQUEST EXECed " + s, Toast.LENGTH_SHORT).show();

    }

    private static String queryGen(String username, String qid){

        String baseUrl =
//                "https://leet-api-2.herokuapp.com/";
                "https://api-2-git.herokuapp.com/api/user/";
        baseUrl += username + '/' + qid + '/';
        return baseUrl;
    }

}
