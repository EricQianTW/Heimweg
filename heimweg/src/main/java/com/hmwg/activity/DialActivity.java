package com.hmwg.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.hmwg.base.BaseActivity;
import com.hmwg.base.BaseAppCompatActivity;
import com.hmwg.bean.User;
import com.hmwg.common.Constant;
import com.hmwg.control.LuckyPanView;
import com.hmwg.eric.R;
import com.hmwg.utils.GSONUtils;
import com.hmwg.utils.L;
import com.hmwg.utils.T;
import com.hmwg.utils.okhttp.OkHttpUtils;
import com.hmwg.utils.okhttp.callback.Callback;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class DialActivity extends BaseAppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.id_luckypan)
    LuckyPanView mLuckyPanView;
    @Bind(R.id.id_start_btn)
    ImageView mStartBtn;

    private int ramdon = 0;
    private int block = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);
        ButterKnife.bind(this);
        initCompatView();
        initBack();
        initAction();
    }

    private void initAction() {
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mLuckyPanView.isStart()) {

                    mStartBtn.setImageResource(R.drawable.stop);
                    ramdon = (int) (Math.random() * 1000);
                    if (ramdon == 0) {
                        block = 0;
                    } else if (ramdon > 0 && ramdon <= 100) {
                        block = 1;
                    } else if (ramdon > 100 && ramdon <= 200) {
                        block = 2;
                    } else if (ramdon > 200 && ramdon <= 300) {
                        block = 3;
                    } else if (ramdon > 300 && ramdon <= 400) {
                        block = 4;
                    } else {
                        block = 5;
                    }
                    mLuckyPanView.luckyStart(block);
                } else {
                    if (!mLuckyPanView.isShouldEnd()) {
                        mStartBtn.setImageResource(R.drawable.start);
                        mLuckyPanView.luckyEnd();
                    }
                }
            }
        });

        mLuckyPanView.setOnPanStoppedListening(new LuckyPanView.OnPanStoppedListening() {
            @Override
            public void PanStoppedListening() {
                commitResult();
            }
        });
    }

    public void commitResult() {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_IP)
                .addParams("_Interface", "Matan.User_1")
                .addParams("_Method", "MBUserLogin")
                .addParams("deviceid", "123")
                .addParams("accountMobile", "13739146726")
                .addParams("password", "198756")
                .build()//
                .execute(new Callback<User>() {
                    @Override
                    public User parseNetworkResponse(Response response) throws Exception {
                        String string = response.body().string();
                        User user = new GSONUtils().fromJson(string, User.class);
                        return null;
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        L.e(TAG, e.toString());
                    }

                    @Override
                    public void onResponse(User response) {
                        T.showShort(getActivity(), "注册成功");
                    }
                });
    }
}
