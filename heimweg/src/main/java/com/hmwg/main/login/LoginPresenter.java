package com.hmwg.main.login;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hmwg.base.BasePresenter;
import com.hmwg.bean.EmployeeInfo;
import com.hmwg.common.Constant;
import com.hmwg.utils.GSONUtils;
import com.hmwg.utils.RSAUtils;
import com.hmwg.utils.SPUtils;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.math.BigDecimal;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by eric_qiantw on 16/4/20.
 */
public class LoginPresenter extends BasePresenter implements LoginContract.Presenter{
    private final LoginContract.View mLoginView;

    public LoginPresenter(@NonNull LoginContract.View loginView){
        mLoginView = checkNotNull(loginView, "loginView be null!");

        mLoginView.setPresenter(this);
    }

    @Override
    public void loginTask(String userId,String passwd,Context mContext) {
        okHttp(userId,passwd,mContext);
    }

    public void okHttp(String userId,String passwd,final Context mContext) {
//        OkHttpUtils
//                .get()
//                .url(Constant.HTTP_IP)
//                .addParams("_Interface", "Matan.User_1")
//                .addParams("_Method", "MBUserLogin")
//                .addParams("deviceid", Constant.serialNumber)
//                .addParams("loginname", GSONUtils.toJson(userId))
//                .addParams("password", GSONUtils.toJson(passwd))
//                .addParams("RSA", GSONUtils.toJson(RSAUtils.getRSA(getRSAMap(userId, passwd))))
//                .build()
//                .execute(new Callback<EmployeeInfo>() {
//                    @Override
//                    public EmployeeInfo parseNetworkResponse(Response response) throws Exception {
//                        return GSONUtils.fromJson(response.body().string(), EmployeeInfo.class);
//                    }
//
//                    @Override
//                    public void onError(Call call, Exception e) {
//                        Logger.e(e,"something happend");
//                    }
//
//                    @Override
//                    public void onResponse(EmployeeInfo response) {
//                        if(response.getState() == Constant.OKHTTP_RESULT_SUCESS){
//                            response.setLogin(true);
//                            SPUtils.put(mContext,SPUtils.SP_LOGIN_INFO, GSONUtils.toJson(response));
                            mLoginView.loginSuccess();
//                        }else{
//                            Logger.e(TAG, response.getCustomMessage());
//                            mLoginView.loginFaild();
//                        }
//                    }
//                });
    }

    @NonNull
    private HashMap getRSAMap(String userId, String passwd) {
        HashMap map = new HashMap();
        map.put("deviceid", Constant.serialNumber);
        map.put("loginname", userId);
        map.put("password", passwd);
        return map;
    }

    @Override
    public void start() {

    }
}
