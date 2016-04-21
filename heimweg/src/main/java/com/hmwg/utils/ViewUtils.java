package com.hmwg.utils;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

/**
 * Created by Abaddon on 2016/2/22.
 */
public class ViewUtils {
    /**
     * 遍历布局，并禁用所有子控件
     *
     * @param viewGroup
     *            布局对象
     */
    public static void disableSubControls(ViewGroup viewGroup) {
        String TAG = "disableSubControls";
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View v = viewGroup.getChildAt(i);
            if (v instanceof ViewGroup) {
                if (v instanceof Spinner) {
                    Spinner spinner = (Spinner) v;
                    spinner.setClickable(false);
                    spinner.setEnabled(false);

                    Log.i(TAG, "A Spinner is unabled");
                } else if (v instanceof ListView) {
                    v.setClickable(false);
                    v.setEnabled(false);

                    Log.i(TAG, "A ListView is unabled");
                } else {
                    disableSubControls((ViewGroup) v);
                }
            } else if (v instanceof EditText) {
                v.setEnabled(false);
                v.setClickable(false);

                Log.i(TAG, "A EditText is unabled");
            } else if (v instanceof Button) {
                v.setEnabled(false);

                Log.i(TAG, "A Button is unabled");
            }
        }
    }
}
