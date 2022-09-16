package com.example.leetcode_api_request;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

//    public static final String KEY_TASK_DESC = "key_task_desc";
    public static final String ch1Id = "channel1";
    private static final String TAG = "MAINACTIVITY";

//    public static Context c;
//    public static Context getContext(){
//        return c;
//    }
    public NotificationManagerCompat notifManager;

//    private Button btChk1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        c = getApplicationContext();
        createNotifChannel();
        notifManager = NotificationManagerCompat.from(this);


        //-------------------------------------
//        PeriodicWorkRequest request =
//                new PeriodicWorkRequest.Builder(MyWorker.class, 16, TimeUnit.MINUTES)
//                        .build();

        findViewById(R.id.workerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                WorkManager.getInstance().enqueue(request);
            }
        });

        final TextView textView = findViewById(R.id.tvWorker);

//        WorkManager.getInstance().getWorkInfoByIdLiveData(request.getId())
//                .observe(this, new Observer<WorkInfo>() {
//                    @Override
//                    public void onChanged(@Nullable WorkInfo workInfo) {
//
//                        if (workInfo != null) {
//
//                            if (workInfo.getState().isFinished()) {
//
//                                Data data = workInfo.getOutputData();
//
////                                textView.append( + "\n");
//                            }
//
//                            String status = workInfo.getState().name();
//                            textView.append(status + "\n");
//                        }
//                    }
//                });

        new Storage(MainActivity.this);

        Button btChk1 = findViewById(R.id.btChk1);
        btChk1.setOnClickListener(view -> {
            checkClass chk = new checkClass(MainActivity.this);
//            checkClass.chk2();
//            chk.execut
//            getUserUpdate.pipeLine(MainActivity.this);
            new notifClass(MainActivity.this);
            new asyncUpdate().execute();

        });
        Button btChk2 = findViewById(R.id.btChk2);
        btChk2.setOnClickListener(view -> {
//            getUserUpdate updt = new getUserUpdate(MainActivity.this);
//            updt.execute("pete1302");
            new notifClass(MainActivity.this);
            notifClass.notifChk();

        });
        Button btSave = findViewById(R.id.btJobStart);
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


        //----------------
        new jobShed(MainActivity.this);

        Button btJobStart = findViewById(R.id.btJobStart);
        btJobStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                jobShed js = new jobShed(MainActivity.this);
//                js.startJob();

                startJob();
            }
        });

        Button btJobEnd = findViewById(R.id.btJobEnd);
        btJobEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                jobShed js = new jobShed(MainActivity.this);
//                js.cancleJob();
                cancleJob();
            }
        });
    }

    //-----------------------------------------------------------------------------

    public void startJob(){
//        MainActivity activity = (MainActivity) weakRef.get();
        ComponentName cn = new ComponentName(this , jobShed.class);
        JobInfo jInfo = new JobInfo.Builder( 1,cn )
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int schedRes = scheduler.schedule(jInfo);
        if(schedRes == JobScheduler.RESULT_SUCCESS){
            Log.d(TAG, "startJob: ");
        }else{
            Log.d(TAG, "startJob: ");
        }

    }

    public void cancleJob(){
//        MainActivity activity = (MainActivity) weakRef.get();
        JobScheduler jSched = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        jSched.cancel(1);
        Log.d(TAG, "cancleJob: CANCELLED");
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
