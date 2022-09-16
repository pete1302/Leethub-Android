package com.example.leetcode_api_request;


import android.app.Notification;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class notifClass {

    private static final String TAG = "notifClass";
    private static NotificationManagerCompat notifManager ;
    private final static AtomicInteger c = new AtomicInteger(0);

    private static WeakReference weakRef;
    public notifClass(MainActivity activity){

        weakRef = new WeakReference<MainActivity>(activity);

        setnotifManager();
    }
    public static void setnotifManager(){
        MainActivity activity = (MainActivity) weakRef.get();
        notifManager =  activity.notifManager;
    }

    public static void sendNotif(String t , String m){
        MainActivity activity = (MainActivity) weakRef.get();
        Notification notif = new NotificationCompat.Builder(
                activity,
                MainActivity.ch1Id
        )
                .setSmallIcon(R.drawable.ic_hehehe)
                .setContentTitle(t)
                .setContentText(m)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notifManager.notify(
                getID(),
                notif
        );
    }
    public static void notifListParse(HashMap<String ,Boolean> notifList){

        Log.d(TAG, "notifListParse: START");
        Iterator it = notifList.entrySet().iterator();
        while( it.hasNext()){

            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getValue().equals(true)) {
                String userName = pair.getKey().toString();
                Log.d(TAG, "notifListParse: NOTIF QUEUED -> " + userName);
                sendNotif(userName , "DESCRIPTION");
            }
            it.remove();
        }
        Log.d(TAG, "notifListParse: END");
    }

    public static void notifChk(){
//        new Storage();
        ArrayList<String> users = Storage.users;
        for (int i = 0; i < users.size(); i++) {
            Log.d(TAG, "notifChk: ");
            String userName = users.get(i);
            sendNotif(userName , "DUMMY " + getID());

        }
    }

    private static int getID() {
        return c.incrementAndGet();
    }

}
