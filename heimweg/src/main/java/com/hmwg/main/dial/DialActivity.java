package com.hmwg.main.dial;

import android.os.Bundle;

import com.hmwg.base.BaseAppCompatActivity;
import com.hmwg.eric.R;
import com.hmwg.utils.ActivityUtils;

public class DialActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dial_act);

        initCompatView();
        initBack();

        DialWvFragment DialWvFragment = (DialWvFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (DialWvFragment == null) {
            DialWvFragment = DialWvFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    DialWvFragment, R.id.contentFrame);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
