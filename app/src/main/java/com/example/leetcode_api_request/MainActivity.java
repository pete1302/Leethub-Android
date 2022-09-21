package com.example.leetcode_api_request;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import java.util.concurrent.atomic.AtomicInteger;


public class MainActivity extends AppCompatActivity {

//    public static final String KEY_TASK_DESC = "key_task_desc";
    public static final String ch1Id = "channel1";
    private static final String TAG = "MAINACTIVITY";
    private final static AtomicInteger count = new AtomicInteger(0);

    public NotificationManagerCompat notifManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotifChannel();
        notifManager = NotificationManagerCompat.from(this);


//        final TextView textView = findViewById(R.id.tvWorker);

        new Storage(MainActivity.this);

        Button btChk1 = findViewById(R.id.btChk1);
        btChk1.setOnClickListener(view -> {
//            checkClass chk = new checkClass(MainActivity.this);
//            checkClass.chk2();
//            chk.execut
//            getUserUpdate.pipeLine(MainActivity.this);
            new notifClass(MainActivity.this);
            new asyncUpdate().execute();

        });
//        Button btChk2 = findViewById(R.id.btChk2);
//        btChk2.setOnClickListener(view -> {
////            getUserUpdate updt = new getUserUpdate(MainActivity.this);
////            updt.execute("pete1302");
//            new notifClass(MainActivity.this);
//            notifClass.notifChk();
//
//        });
//        Button btSave = findViewById(R.id.btAsyncTask);
//        btSave.setOnClickListener(view -> {
//            checkClass chk = new checkClass(MainActivity.this);
//
//            String userInp = "pete1302";
//            try {
//                checkClass.chk3(userInp);
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });

        Button btAdd = findViewById(R.id.btAdd);
        btAdd.setOnClickListener(view -> {
            TextView evUserName = findViewById(R.id.evUsername);
            String userName = evUserName.getText().toString();
            if(userName.equals(null) || userName.equals("")){
                Toast.makeText(this, "ENTER VALID USERNAME", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "ADDING "+userName, Toast.LENGTH_SHORT).show();
                getUserClass userInstance = new getUserClass(MainActivity.this);
                userInstance.execute(userName);
            }
        });
        //----------------

        Button btChk = findViewById(R.id.btChk);
        btChk.setOnClickListener(view -> {
            String sCount = String.valueOf(count.incrementAndGet());
            btChk.setText(sCount);
        });

        Button btJobTh = findViewById(R.id.btAsyncTask);
        btJobTh.setOnClickListener(view -> {
            Toast.makeText(this, "Thread Start", Toast.LENGTH_SHORT).show();
            new Thread(() -> {
                for (int i = 0; i < 5; i++) {
                    Log.d(TAG, "run: STEP" + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            Toast.makeText(this, "Thread End", Toast.LENGTH_SHORT).show();
        });

        Button btUiTh = findViewById(R.id.btUiTask);
        btUiTh.setOnClickListener(view -> {
            Toast.makeText(this, "UI Thread Start", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < 5; i++) {
                Log.d(TAG, "run: STEP" + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Toast.makeText(this, "UI Thread End", Toast.LENGTH_SHORT).show();
        });


        //----------------
        new jobShed(MainActivity.this);
        Button btJobStart = findViewById(R.id.btJob);
        btJobStart.setOnClickListener(view -> {
            new notifClass(MainActivity.this);
            startJob();
            Toast.makeText(this, "Notification Turned On", Toast.LENGTH_SHORT).show();
        });

        Button btJobEnd = findViewById(R.id.btJobCancel);
        btJobEnd.setOnClickListener(view -> {
            cancleJob();
            Toast.makeText(this, "Notification Cancelled", Toast.LENGTH_SHORT).show();
        });
        Button btJobChk = findViewById(R.id.btChk2);
        btJobChk.setOnClickListener(view -> {
            boolean flg = isJobServiceOn(this);
            Toast.makeText(this, String.valueOf(flg) , Toast.LENGTH_SHORT).show();

        });
    }

    //-----------------------------------------------------------------------------

    public void startJob(){
        ComponentName cn = new ComponentName(this , jobShed.class);
        JobInfo jInfo = new JobInfo.Builder( 1,cn )
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setRequiresCharging(false)
                .setPeriodic(15*60*1000)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int schedRes = scheduler.schedule(jInfo);
        if(schedRes == JobScheduler.RESULT_SUCCESS){
            Log.d(TAG, "startJob: SUCCESS");
        }else{
            Log.d(TAG, "startJob: FAIL");
        }
    }

    public void cancleJob(){
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
    public static boolean isJobServiceOn( Context context ) {
        JobScheduler scheduler = (JobScheduler) context.getSystemService( Context.JOB_SCHEDULER_SERVICE ) ;

        boolean hasBeenScheduled = false ;

        for ( JobInfo jobInfo : scheduler.getAllPendingJobs() ) {
            if ( jobInfo.getId() == 1 ) {
                hasBeenScheduled = true ;
                break ;
            }
        }

        return hasBeenScheduled ;
    }



}
