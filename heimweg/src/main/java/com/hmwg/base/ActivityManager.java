package com.hmwg.base;

import android.app.Activity;

import java.lang.ref.WeakReference;

/**
 * Created by androidyue on 2/9/16.
 */
public class ActivityManager {
    private static ActivityManager mInstance = new ActivityManager();
    private WeakReference<Activity> activityCurrentActivityWeakRef;

    private ActivityManager() {

    }

    public static ActivityManager getInstance() {
        return mInstance;
    }

    public Activity getCurrentActivity() {
        Activity currentActivity = null;
        if (activityCurrentActivityWeakRef != null) {
            currentActivity = activityCurrentActivityWeakRef.get();
        }
        return currentActivity;
    }

    public void setCurrentActivity(Activity activity) {
        activityCurrentActivityWeakRef = new WeakReference<Activity>(activity);
    }
}
