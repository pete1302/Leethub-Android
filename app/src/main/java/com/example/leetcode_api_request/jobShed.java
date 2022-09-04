package com.example.leetcode_api_request;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;
import android.widget.Toast;

import java.lang.ref.WeakReference;

@SuppressLint("SpecifyJobSchedulerIdRange")
public class jobShed extends JobService {

    private static WeakReference weakRef;
    public static final boolean sts = false;

    public jobShed(MainActivity activity){
        weakRef = new WeakReference<>(activity);
    }

    public static final String JOB = "job1";
    private static  String TAG = "JOB-SERVICE";

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "onStartJob: START");
        bgWork();
        jobFinished(jobParameters , false);
        Log.d(TAG, "onStartJob: END");
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        sts = true;
        Log.d(TAG, "onStopJob: CANCELLED BEFORE COMPLETION");
        Toast.makeText(this, "CANCELLED", Toast.LENGTH_SHORT).show();

        return true;
    }
    private static void bgWork(){
        MainActivity activity = (MainActivity) weakRef.get();
        for (int i = 0; i < Storage.users.size(); i++) {
            if(sts){
                return;
            }
            getUserUpdate getUpdt = new getUserUpdate(activity);
            if(Storage.users.get(0) != null){
                getUpdt.execute(Storage.users.get(i));
            }
        }
    }

}
