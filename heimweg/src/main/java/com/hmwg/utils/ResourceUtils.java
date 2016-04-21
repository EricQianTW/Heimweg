package com.hmwg.utils;

import android.content.Context;

import com.hmwg.eric.R;

/**
 * Created by eric on 16-2-23.
 */
public class ResourceUtils {

    public static String[] getArrayResource(Context context,int resourceId){
        return context.getResources().getStringArray(R.array.register_storename);
    }

}
