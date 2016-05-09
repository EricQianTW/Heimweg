package com.hmwg.main.ordergoods;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hmwg.base.BasePresenter;
import com.hmwg.bean.EmployeeInfo;
import com.hmwg.bean.OrderInfo;
import com.hmwg.bean.ResBoolean;
import com.hmwg.common.Constant;
import com.hmwg.utils.GSONUtils;
import com.hmwg.utils.L;
import com.hmwg.utils.RSAUtils;
import com.hmwg.utils.T;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by eric_qiantw on 16/4/20.
 */
public class OrderGoodsPresenter extends BasePresenter implements OrderGoodsContract.Presenter{
    private final OrderGoodsContract.View mLoginView;

    public OrderGoodsPresenter(@NonNull OrderGoodsContract.View loginView){
        mLoginView = checkNotNull(loginView, "loginView be null!");

        mLoginView.setPresenter(this);
    }

    @Override
    public void loginTask(OrderInfo info, EmployeeInfo employeeInfo) {
        okHttp(info,employeeInfo);
    }

    public void okHttp(OrderInfo info, EmployeeInfo employeeInfo) {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_IP)
                .addParams("_Interface", "Matan.OrderAbout")
                .addParams("_Method", "MeOrderAdd")
                .addParams("deviceid", GSONUtils.toJson(Constant.serialNumber))
                .addParams("userid", GSONUtils.toJson(employeeInfo.getEmployeeID()))
                .addParams("oi", GSONUtils.toJson(info))
                .addParams("RSA", GSONUtils.toJson(RSAUtils.getRSA(getRSAMap(employeeInfo.getEmployeeID(), GSONUtils.toJson(info)))))
                .build()
                .execute(new Callback<ResBoolean>() {
                    @Override
                    public ResBoolean parseNetworkResponse(Response response) throws Exception {
                        return GSONUtils.fromJson(response.body().string(), ResBoolean.class);
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        Logger.e(e,"something happend");
                    }

                    @Override
                    public void onResponse(ResBoolean response) {
                        mLoginView.loginSuccess();
                    }
                });
    }

    @NonNull
    private HashMap getRSAMap(String userId, String oi) {
        HashMap map = new HashMap();
        map.put("deviceid", Constant.serialNumber);
        map.put("userid", userId);
        map.put("oi", oi);
        return map;
    }

    @Override
    public void start() {

    }
}
