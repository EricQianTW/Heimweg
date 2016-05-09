package com.hmwg.main.register;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.hmwg.base.BaseAppCompatActivity;
import com.hmwg.eric.R;
import com.hmwg.utils.ActivityUtils;

import butterknife.Bind;

/**
 * 注册画面
 */
public class RegisterActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_act);

        initCompatView();
        initBack();

        RegisterFragment registerFragment = (RegisterFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (registerFragment == null) {
            registerFragment = RegisterFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    registerFragment, R.id.contentFrame);
        }

        // Create the presenter
        new RegisterPresenter(registerFragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}

