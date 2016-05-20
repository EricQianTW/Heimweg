package com.hmwg.main.choosestores;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.hmwg.base.BasePresenter;
import com.hmwg.base.Message;
import com.hmwg.bean.EmployeeInfo;
import com.hmwg.bean.SaleList;
import com.hmwg.bean.User;
import com.hmwg.common.Constant;
import com.hmwg.utils.GSONUtils;
import com.hmwg.utils.SPUtils;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by eric_qiantw on 16/4/20.
 */
public class ChooseStoresPresenter extends BasePresenter implements ChooseStoresContract.Presenter{
    private final ChooseStoresContract.View mLoginView;

    public ChooseStoresPresenter(@NonNull ChooseStoresContract.View loginView){
        mLoginView = checkNotNull(loginView, "loginView be null!");

        mLoginView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getStore(int userId,String provin) {
        try {
            OkHttpUtils
                    .get()
                    .url(Constant.HTTP_IP)
                    .addParams("_Interface", "Matan.User_1")
                    .addParams("_Method", "MB_GetSale")
                    .addParams("deviceid", GSONUtils.toJson(Constant.serialNumber))
                    .addParams("UserId", GSONUtils.toJson(userId))
                    .addParams("provin", GSONUtils.toJson(provin))
                    .build()//
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
                                TypeToken<Message<List<SaleList>>> token = new TypeToken<Message<List<SaleList>>>() {
                                };
                                Message<List<SaleList>> dataPackage = GSONUtils.fromJson(response, token);
                                if(dataPackage.getState() == Constant.OKHTTP_RESULT_SUCESS){
                                    mLoginView.setStore(dataPackage.getBody());
                                }else{
                                    Logger.e(TAG, dataPackage.getCustomMessage());
                                }
                            } catch (Exception e) {
                                Logger.e(e, TAG);
                            }
                        }
                    });
        } catch (Exception e) {
            Logger.e(e, TAG);
        }
    }
}
