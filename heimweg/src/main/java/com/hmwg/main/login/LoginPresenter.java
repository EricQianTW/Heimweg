package com.hmwg.main.login;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.hmwg.base.BasePresenter;
import com.hmwg.base.Message;
import com.hmwg.bean.EmployeeInfo;
import com.hmwg.bean.SaleList;
import com.hmwg.common.Constant;
import com.hmwg.utils.GSONUtils;
import com.hmwg.utils.RSAUtils;
import com.hmwg.utils.SPUtils;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

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
        try {
            OkHttpUtils
                    .get()
                    .url(Constant.HTTP_IP)
                    .addParams("_Interface", "Matan.User_1")
                    .addParams("_Method", "MBUserLogin")
                    .addParams("deviceid", GSONUtils.toJson(Constant.serialNumber))
                    .addParams("loginname", GSONUtils.toJson(userId))
                    .addParams("password", GSONUtils.toJson(passwd))
                    .addParams("RSA", GSONUtils.toJson(RSAUtils.getRSA(getRSAMap(userId, passwd))))
                    .build()
                    .connTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)
                    .execute(new Callback<String>() {
                        @Override
                        public String parseNetworkResponse(Response response) throws Exception {
                            return response.body().string();
                        }

                        @Override
                        public void onError(Call call, Exception e) {
                            Logger.e(e,"something happend");
                        }

                        @Override
                        public void onResponse(String response) {
                            try {
                                TypeToken<Message<EmployeeInfo>> token = new TypeToken<Message<EmployeeInfo>>() {
                                };
                                Message<EmployeeInfo> dataPackage = GSONUtils.fromJson(response, token);
                                if(dataPackage.getState() == Constant.OKHTTP_RESULT_SUCESS){
                                    EmployeeInfo info = dataPackage.getBody();
                                    info.setLogin(true);
                                    SPUtils.put(mContext,SPUtils.SP_LOGIN_INFO, GSONUtils.toJson(info));
                                    mLoginView.loginSuccess(info.getId());
                                }else{
                                    Logger.e(TAG, dataPackage.getCustomMessage());
                                    mLoginView.loginFaild();
                                }
                            } catch (Exception e) {
                                Logger.e(e,TAG);
                            }
                        }
                    });
        } catch (Exception e) {
            Logger.e(e,TAG);
        }
    }

    @NonNull
    private HashMap getRSAMap(String userId, String passwd) throws Exception {
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
