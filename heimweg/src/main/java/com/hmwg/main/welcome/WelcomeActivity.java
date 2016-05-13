package com.hmwg.main.welcome;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hmwg.base.BaseActivity;
import com.hmwg.eric.R;
import com.hmwg.main.login.LoginActivity;
import com.hmwg.main.ordergoods.OrderGoodsActivity;
import com.hmwg.utils.IntentUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_act);
        ButterKnife.bind(this);

        initJump();
        initAction();
    }

    /**
     * 定时跳转
     */
    private void initJump() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                jumpPage();
            }
        };
        timer.schedule(task, 1000 * 2);
    }

    private void initAction() {

    }

    private void jumpPage() {
        if(getUser() != null && getUser().isLogin()){
            IntentUtils.startActivityWithFinish(getActivity(), OrderGoodsActivity.class);
        }else{
            IntentUtils.startActivityWithFinish(getActivity(), LoginActivity.class);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

}
