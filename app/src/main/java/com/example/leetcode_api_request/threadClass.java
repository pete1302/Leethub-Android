package com.example.leetcode_api_request;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class threadClass extends Thread{

    private static final String TAG = "THREAD";


    public void run() {
        final String[] res = new String[1];
//            Log.i(TAG, strings[0]);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api-2-git.herokuapp.com/api/user/pete1302/6")
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
    }



}
