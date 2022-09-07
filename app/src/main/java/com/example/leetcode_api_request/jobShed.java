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
    public static  boolean sts = false;

    public jobShed(){

    }

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
        Log.d(TAG, "bgWork: BG WORK START ___ ");
        MainActivity activity = (MainActivity) weakRef.get();
        getUserUpdate getUpdt = new getUserUpdate(activity);
//        new Storage();
//        for (int i = 0; i < Storage.users.size(); i++) {
//            if(sts){
//                Log.d(TAG, "bgWork: STS TRUE!!!!");
//                return;
//            }
//            else if(Storage.users.get(0) != null){
//                Log.d(TAG, "bgWork: ON EXE");
//                getUpdt.execute(Storage.users.get(i));
//            }else{
//                Log.d(TAG, "bgWork: STORAGE EMPTY");
//            }
//        }
        getUpdt.execute("pete1302");
    }
//    public void startJob(){
//        MainActivity activity = (MainActivity) weakRef.get();
//        ComponentName cn = new ComponentName(activity , jobShed.class);
//        JobInfo jInfo = new JobInfo.Builder( 1,cn )
//                .build();
//
//        JobScheduler scheduler = (JobScheduler) activity.getSystemService(JOB_SCHEDULER_SERVICE);
//        int schedRes = scheduler.schedule(jInfo);
//        if(schedRes == JobScheduler.RESULT_SUCCESS){
//            Log.d(TAG, "startJob: ");
//        }else{
//            Log.d(TAG, "startJob: ");
//        }
//
//    }
//
//    public void cancleJob(){
//        MainActivity activity = (MainActivity) weakRef.get();
//        JobScheduler jSched = (JobScheduler) activity.getSystemService(JOB_SCHEDULER_SERVICE);
//        jSched.cancel(1);
//        Log.d(TAG, "cancleJob: CANCELLED");
//    }

}
