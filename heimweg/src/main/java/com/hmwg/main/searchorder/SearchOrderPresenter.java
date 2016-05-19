package com.hmwg.main.searchorder;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.hmwg.base.BasePresenter;
import com.hmwg.base.Message;
import com.hmwg.bean.CODE_SPEC;
import com.hmwg.bean.User;
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
public class SearchOrderPresenter extends BasePresenter implements SearchOrderContract.Presenter{
    private final SearchOrderContract.View mLoginView;

    public SearchOrderPresenter(@NonNull SearchOrderContract.View loginView){
        mLoginView = checkNotNull(loginView, "loginView be null!");

        mLoginView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void searchTask() {

    }

    @Override
    public void getFileModel(int UserId) {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_IP)
                .addParams("_Interface", "Matan.User_1")
                .addParams("_Method", "MB_GetGrmList")
                .addParams("deviceid", GSONUtils.toJson(Constant.serialNumber))
                .addParams("UserId", GSONUtils.toJson(UserId))
                .build()
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
                        TypeToken<Message<List<CODE_SPEC>>> token = new TypeToken<Message<List<CODE_SPEC>>>() {
                        };
                        Message<List<CODE_SPEC>> dataPackage = GSONUtils.fromJson(response, token);
                        if(dataPackage.getState() == Constant.OKHTTP_RESULT_SUCESS){
                            mLoginView.setFileModel(dataPackage.getBody());
                        }else{
                            Logger.e(TAG, dataPackage.getCustomMessage());
                        }
                    }
                });
    }
}
