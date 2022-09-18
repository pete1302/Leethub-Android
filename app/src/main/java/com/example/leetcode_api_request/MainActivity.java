package com.example.leetcode_api_request;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

//    public static final String KEY_TASK_DESC = "key_task_desc";
    public static final String ch1Id = "channel1";
    private static final String TAG = "MAINACTIVITY";

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

        Button btAdd = findViewById(R.id.btAdd);
        btAdd.setOnClickListener(view -> {
            TextView evUserName = findViewById(R.id.evUsername);
            String userName = evUserName.getText().toString();
            if(userName.equals(null) || userName.equals("")){
                Toast.makeText(this, "ENTER VALID USERNAME", Toast.LENGTH_SHORT).show();
            }else{
                getUserClass userInstance = new getUserClass(MainActivity.this);
                userInstance.execute(userName);
            }
            
        });


        //----------------
        new jobShed(MainActivity.this);
        Button btJobStart = findViewById(R.id.btJobStart);
        btJobStart.setOnClickListener(view -> {
            new notifClass(MainActivity.this);
            startJob();
        });

        Button btJobEnd = findViewById(R.id.btJobEnd);
        btJobEnd.setOnClickListener(view -> {
            cancleJob();
        });
    }

    //-----------------------------------------------------------------------------

    public void startJob(){
        ComponentName cn = new ComponentName(this , jobShed.class);
        JobInfo jInfo = new JobInfo.Builder( 1,cn )
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



}
