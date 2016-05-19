package com.hmwg.main.welcome;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hmwg.base.AppManager;
import com.hmwg.base.BaseActivity;
import com.hmwg.control.UpdateVersion.LoadAnsyReadVersionXML;
import com.hmwg.eric.R;
import com.hmwg.main.login.LoginActivity;
import com.hmwg.main.ordergoods.OrderGoodsActivity;
import com.hmwg.utils.IntentUtils;
import com.hmwg.utils.NetUtils;
import com.hmwg.utils.SDCardUtils;
import com.hmwg.utils.T;

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

        if(!SDCardUtils.isSDCardEnable()){
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setMessage("内存设备未准备好，无法运行");
            builder.setNegativeButton("知道了",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int whichButton) {
                            AppManager.getAppManager().AppExit(getApplicationContext());
                        }
                    });
            builder.create().show();
        }

        if (NetUtils.isConnected(this)) {
            LoadAnsyReadVersionXML loadXml = new LoadAnsyReadVersionXML(WelcomeActivity.this);
            loadXml.setOnCompareFinishListen(new LoadAnsyReadVersionXML.OnCompareFinishListening() {
                @Override
                public void CompareFinishListening() {
                    initJump();
                }
            });
            loadXml.setOnCancelUpdateListen(new LoadAnsyReadVersionXML.OnCancelUpdateListening() {
                @Override
                public void CompareFinishListening() {
                    AppManager.getAppManager().AppExit(getActivity());
                }
            });
            loadXml.execute();
        } else {
            T.showShort(getApplicationContext(), "请先联网.........");
        }
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

    private void jumpPage() {
        if(getUser() != null && getUser().isLogin()){
            IntentUtils.startActivityWithFinish(this, OrderGoodsActivity.class);
        }else{
            IntentUtils.startActivityWithFinish(this, LoginActivity.class);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

}
