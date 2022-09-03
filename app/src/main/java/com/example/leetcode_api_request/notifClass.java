package com.example.leetcode_api_request;


import android.app.Notification;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicInteger;

public class notifClass {

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

    private static int getID() {
        return c.incrementAndGet();
    }

}
