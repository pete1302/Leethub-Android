package com.example.leetcode_api_request;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class checkClass {

    public static void testfx() {
        Log.i("TEST", "TESTFX");
    }


    public static void chk() {
        Storage sto = new Storage();
        JSONObject jsonData = null;

        for (int i = 0; i < sto.users.size(); i++) {
            Log.i("users- ", sto.users.get(i));
            try {
                jsonData = new JSONObject(chkValidUser(sto.users.get(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (jsonData.has("erros")) {
                Log.e("ERROR", "error in data'");

            }

        }
    }

    public static String chkValidUser(String userName){
        final String[] res = new String[1];
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(queryGen(userName, "6"))
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                e.printStackTrace();
                res[0] = "{\"errors\":\"faliure fetching\"";
                Log.i("ERROR", "fetching");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    res[0] = String.format("{\"data\":\"%s\"}", myResponse);
                    Log.i("DATA", myResponse);
                }
            }
        });
        return res[0];
    }

    public void requestQuery (String url){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

//                    MainActivity.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            tvRes.setText(myResponse);
//                        }
//                    });
                }
            }
        });
    }
    public static String queryGen(String username, String qid){

        String baseUrl =
//                "https://leet-api-2.herokuapp.com/";
                "https://api-2-git.herokuapp.com/api/user/";
        baseUrl += username + '/' + qid + '/';
        return baseUrl;
    }
}
