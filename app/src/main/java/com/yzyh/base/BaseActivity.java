package com.yzyh.base;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Abaddon on 2016/2/20.
 */
public class BaseActivity extends Activity {
    protected Activity mContext = BaseActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
