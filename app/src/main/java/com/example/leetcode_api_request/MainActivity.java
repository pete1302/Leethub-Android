package com.example.leetcode_api_request;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

//    public static final String KEY_TASK_DESC = "key_task_desc";

    public static Context c;
    public static Context getContext(){
        return c;
    }

//    private Button btChk1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        c = getApplicationContext();

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

    }
//    public void chk2(){
//        Storage sto = new Storage();
//        JSONObject jsonData = null;
//        for (int i = 0; i < sto.users.size(); i++) {
//            Log.i("users- ", sto.users.get(i));
//
//            asyncReqClass reqtask = new asyncReqClass(MainActivity.this);
//            reqtask.execute(sto.users.get(i));
//
//        }
//    }
}
