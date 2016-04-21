package com.yzyh.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Abaddon on 2016/2/20.
 */
public class IntentUtils {

    public static void startActivity(Context context,Class className){
        Intent intent = new Intent(context,className);
        context.startActivity(intent);
    }
}
