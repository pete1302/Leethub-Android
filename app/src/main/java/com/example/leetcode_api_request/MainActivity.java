package com.example.leetcode_api_request;

import android.os.Bundle;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Data data = new Data.Builder()
//                .putString(KEY_TASK_DESC, "Hey I am sending the work data")
//                .build();
//
//        Constraints constraints = new Constraints.Builder()
//                .setRequiresCharging(true)
//                .build();

//        final OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MyWorker.class)
//                .build();
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

    }
}
