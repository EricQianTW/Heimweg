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

        DialFragment dialFragment = (DialFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (dialFragment == null) {
            dialFragment = DialFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    dialFragment, R.id.contentFrame);
        }

        // Create the presenter
        new DialPresenter(dialFragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
