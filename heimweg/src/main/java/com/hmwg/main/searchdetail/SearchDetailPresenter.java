package com.hmwg.main.searchdetail;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.hmwg.base.BasePresenter;
import com.hmwg.base.Message;
import com.hmwg.bean.OrderInfoAPI;
import com.hmwg.bean.ResBoolean;
import com.hmwg.common.Constant;
import com.hmwg.utils.GSONUtils;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by eric_qiantw on 16/4/20.
 */
public class SearchDetailPresenter extends BasePresenter implements SearchDetailContract.Presenter{
    private final SearchDetailContract.View mLoginView;

    public SearchDetailPresenter(@NonNull SearchDetailContract.View loginView){
        mLoginView = checkNotNull(loginView, "loginView be null!");

        mLoginView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void searchTask(int userId,int orderId) {
        try {
            OkHttpUtils
                    .get()
                    .url(Constant.HTTP_IP)
                    .addParams("_Interface", "Matan.OrderAbout")
                    .addParams("_Method", "MeOrderDtlSearch")
                    .addParams("deviceid", GSONUtils.toJson(Constant.serialNumber))
                    .addParams("userid", GSONUtils.toJson(userId))
                    .addParams("orderId", GSONUtils.toJson(orderId))
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
                                TypeToken<Message<OrderInfoAPI>> token = new TypeToken<Message<OrderInfoAPI>>() {
                                };
                                Message<OrderInfoAPI> dataPackage = GSONUtils.fromJson(response, token);
                                if(dataPackage.getState() == Constant.OKHTTP_RESULT_SUCESS){
                                    mLoginView.searchSuccess(dataPackage.getBody());
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

    @Override
    public void deleteTask(int userId,int orderId) {
        try {
            OkHttpUtils
                    .get()
                    .url(Constant.HTTP_IP)
                    .addParams("_Interface", "Matan.OrderAbout")
                    .addParams("_Method", "MeOrderRemove")
                    .addParams("deviceid", GSONUtils.toJson(Constant.serialNumber))
                    .addParams("userid", GSONUtils.toJson(userId))
                    .addParams("OrderId", GSONUtils.toJson(orderId))
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
                                ResBoolean dataPackage = GSONUtils.fromJson(response, ResBoolean.class);
                                if(dataPackage.getState() == Constant.OKHTTP_RESULT_SUCESS){
                                    mLoginView.deleteSuccess();
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
