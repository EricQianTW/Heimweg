package com.hmwg.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Abaddon on 2016/2/20.
 */
public class IntentUtils {

    public static void startActivity(Context context,Class className){
        Intent intent = new Intent(context,className);
        ((Activity)context).startActivity(intent);
    }

    public static void startActivityWithFinish(Context context,Class className){
        Intent intent = new Intent(context,className);
        ((Activity)context).startActivity(intent);
        ((Activity)context).finish();
    }
}
