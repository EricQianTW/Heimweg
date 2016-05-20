package com.hmwg.main.searchlist;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.hmwg.base.BasePresenter;
import com.hmwg.base.Message;
import com.hmwg.bean.CODE_SPEC;
import com.hmwg.bean.OrderInfoAPI;
import com.hmwg.common.Constant;
import com.hmwg.utils.GSONUtils;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by eric_qiantw on 16/4/20.
 */
public class SearchListPresenter extends BasePresenter implements SearchListContract.Presenter{
    private final SearchListContract.View mLoginView;

    public SearchListPresenter(@NonNull SearchListContract.View loginView){
        mLoginView = checkNotNull(loginView, "loginView be null!");

        mLoginView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void searchTask(int userId,OrderInfoAPI infoAPI) {
        try {
            GetBuilder builder = new GetBuilder();
            builder = OkHttpUtils
                    .get()
                    .url(Constant.HTTP_IP)
                    .addParams("_Interface", "Matan.OrderAbout")
                    .addParams("_Method", "MeOrderSearch")
                    .addParams("deviceid", GSONUtils.toJson(Constant.serialNumber))
                    .addParams("userid", GSONUtils.toJson(userId));
            if(infoAPI.getCardNo() != null && !"".equals(infoAPI.getCardNo())){
                builder = builder.addParams("cardNo", GSONUtils.toJson(infoAPI.getCardNo()));
            }
            if(infoAPI.getGrmxh() != null && !"".equals(infoAPI.getGrmxh())){
                builder = builder.addParams("grmXh", GSONUtils.toJson(infoAPI.getGrmxh()));
            }
            if(infoAPI.getStrTjsj() != null && !"".equals(infoAPI.getStrTjsj())){
                builder = builder.addParams("strTjsj", GSONUtils.toJson(infoAPI.getStrTjsj()));
            }
            if(infoAPI.getStrSgsj() != null && !"".equals(infoAPI.getStrSgsj())){
                builder = builder.addParams("strSgsj", GSONUtils.toJson(infoAPI.getStrSgsj()));
            }

            builder.build()
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
                                TypeToken<Message<List<OrderInfoAPI>>> token = new TypeToken<Message<List<OrderInfoAPI>>>() {
                                };
                                Message<List<OrderInfoAPI>> dataPackage = GSONUtils.fromJson(response, token);
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

}
