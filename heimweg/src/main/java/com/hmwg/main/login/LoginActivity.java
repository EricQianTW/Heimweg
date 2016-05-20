package com.hmwg.main.login;

import android.os.Bundle;

import com.hmwg.base.BaseAppCompatActivity;
import com.hmwg.eric.R;
import com.hmwg.utils.ActivityUtils;
import com.orhanobut.logger.Logger;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseAppCompatActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_act);

        try {
            initCompatView();
            initBack();

            LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

            if (loginFragment == null) {
                loginFragment = LoginFragment.newInstance();

                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                        loginFragment, R.id.contentFrame);
            }

            // Create the presenter
            new LoginPresenter(loginFragment);
        } catch (Exception e) {
            Logger.e(e,"");
        } finally {
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}

