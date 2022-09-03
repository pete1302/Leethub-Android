package com.example.leetcode_api_request;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

//    public static final String KEY_TASK_DESC = "key_task_desc";
    public static final String ch1Id = "channel1";

    public static Context c;
    public static Context getContext(){
        return c;
    }
    public NotificationManagerCompat notifManager;

//    private Button btChk1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        c = getApplicationContext();
        createNotifChannel();
        notifManager = NotificationManagerCompat.from(this);


        //-------------------------------------
        PeriodicWorkRequest request =
                new PeriodicWorkRequest.Builder(MyWorker.class, 16, TimeUnit.MINUTES)
                        .build();

        findViewById(R.id.workerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkManager.getInstance().enqueue(request);
            }
        });

        final TextView textView = findViewById(R.id.tvWorker);

        WorkManager.getInstance().getWorkInfoByIdLiveData(request.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(@Nullable WorkInfo workInfo) {

                        if (workInfo != null) {

                            if (workInfo.getState().isFinished()) {

                                Data data = workInfo.getOutputData();

//                                textView.append( + "\n");
                            }

                            String status = workInfo.getState().name();
                            textView.append(status + "\n");
                        }
                    }
                });

        Button btChk1 = findViewById(R.id.btChk1);
        btChk1.setOnClickListener(view -> {
            checkClass chk = new checkClass(MainActivity.this);
            checkClass.chk2();

        });
        Button btChk2 = findViewById(R.id.btChk2);
        btChk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserUpdate updt = new getUserUpdate(MainActivity.this);
                updt.execute("pete1302");
            }
        });
        Button btSave = findViewById(R.id.btSave);
        btSave.setOnClickListener(view -> {
            checkClass chk = new checkClass(MainActivity.this);
            String userInp = "pete1302";
            try {
                checkClass.chk3(userInp);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


    }
    private void createNotifChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(
                    ch1Id,
                    "ch 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("CHANNEL 1");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }

}
