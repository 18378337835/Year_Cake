package com.hjn.year_cake.manager;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import utilpacket.utils.ActivitiesManager;

/**
 * Created by Year_Cake on 2018/8/28.
 * decription: 覆盖的activity
 */
public class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        ActivitiesManager.createActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
//        if(activity instanceof SplashActivity){
//        }else{
//            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        }
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ActivitiesManager.closeThis(activity);
    }
}
