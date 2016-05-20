package com.hmwg.main.location;

import android.os.Bundle;

import com.hmwg.base.BaseAppCompatActivity;
import com.hmwg.eric.R;
import com.hmwg.utils.ActivityUtils;
import com.hmwg.utils.SPUtils;
import com.orhanobut.logger.Logger;

/**
 * A login screen that offers login via email/password.
 */
public class LocationActivity extends BaseAppCompatActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_act);

        try {
            initCompatView();
            initBack();

            LocationFragment loginFragment = (LocationFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

            if (loginFragment == null) {
                loginFragment = LocationFragment.newInstance();

                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                        loginFragment, R.id.contentFrame);
            }

            // Create the presenter
            new LocationPresenter(loginFragment);
        } catch (Exception e) {
            Logger.e(e, TAG);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}

