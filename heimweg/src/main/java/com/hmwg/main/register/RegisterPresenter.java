package com.hmwg.main.register;

import android.support.annotation.NonNull;

import com.hmwg.base.BasePresenter;
import com.hmwg.bean.EmployeeInfo;
import com.hmwg.bean.OrderInfo;
import com.hmwg.bean.ResBoolean;
import com.hmwg.bean.User;
import com.hmwg.common.Constant;
import com.hmwg.utils.GSONUtils;
import com.hmwg.utils.L;
import com.hmwg.utils.T;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by eric_qiantw on 16/4/20.
 */
public class RegisterPresenter extends BasePresenter implements RegisterContract.Presenter{
    private final RegisterContract.View mLoginView;

    public RegisterPresenter(@NonNull RegisterContract.View loginView){
        mLoginView = checkNotNull(loginView, "loginView be null!");

        mLoginView.setPresenter(this);
    }

    @Override
    public void loginTask() {
        okHttp();
    }

    public void okHttp() {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_IP)
                .addParams("_Interface", "Matan.User_1")
                .addParams("_Method", "MBUserLogin")
                .addParams("deviceid", "123")
                .addParams("accountMobile", "13739146726")
                .addParams("password", "198756")
                .build()//
                .execute(new Callback<User>()
                {
                    @Override
                    public User parseNetworkResponse(Response response) throws Exception {
                        String string = response.body().string();
                        User user = new GSONUtils().fromJson(string, User.class);
                        return null;
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        Logger.e(e,"something happend");
                    }

                    @Override
                    public void onResponse(User response) {
                        mLoginView.loginSuccess();
                    }
                });
    }

    @Override
    public void start() {

    }
}
