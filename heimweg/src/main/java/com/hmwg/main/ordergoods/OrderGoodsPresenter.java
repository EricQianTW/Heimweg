package com.hmwg.main.ordergoods;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.hmwg.base.BasePresenter;
import com.hmwg.base.Message;
import com.hmwg.bean.CODE_SPEC;
import com.hmwg.bean.EmployeeInfo;
import com.hmwg.bean.OrderInfoAPI;
import com.hmwg.bean.ResBoolean;
import com.hmwg.common.Constant;
import com.hmwg.utils.GSONUtils;
import com.hmwg.utils.RSAUtils;
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
public class OrderGoodsPresenter extends BasePresenter implements OrderGoodsContract.Presenter{
    private final OrderGoodsContract.View mLoginView;

    public OrderGoodsPresenter(@NonNull OrderGoodsContract.View loginView){
        mLoginView = checkNotNull(loginView, "loginView be null!");

        mLoginView.setPresenter(this);
    }

    @Override
    public void startOrder(int userId,String strTime,int ShopId) {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_IP)
                .addParams("_Interface", "Matan.OrderAbout")
                .addParams("_Method", "MeOrderAdd")
                .addParams("deviceid", GSONUtils.toJson(Constant.serialNumber))
                .addParams("userid", GSONUtils.toJson(userId))
                .addParams("strTime", GSONUtils.toJson(strTime))
                .addParams("ShopId", GSONUtils.toJson(ShopId))
                .addParams("RSA", GSONUtils.toJson(RSAUtils.getRSA(getRSAMapForStartOrder(userId, strTime,ShopId))))
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
                        TypeToken<Message<OrderInfoAPI>> token = new TypeToken<Message<OrderInfoAPI>>() {
                        };
                        Message<OrderInfoAPI> dataPackage = GSONUtils.fromJson(response, token);
                        if(dataPackage.getState() == Constant.OKHTTP_RESULT_SUCESS){
                            mLoginView.setOrderInfo(dataPackage.getBody());
                        }else{
                            Logger.e(TAG, dataPackage.getCustomMessage());
                        }
                    }
                });
    }

    @NonNull
    private HashMap getRSAMapForStartOrder(int userId,String strTime,int ShopId) {
        HashMap map = new HashMap();
        map.put("deviceid", Constant.serialNumber);
        map.put("userid", userId);
        map.put("strTime", strTime);
        map.put("ShopId", ShopId);
        return map;
    }

    @Override
    public void addOrder(OrderInfoAPI info, EmployeeInfo employeeInfo) {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_IP)
                .addParams("_Interface", "Matan.OrderAbout")
                .addParams("_Method", "MeOrderUpdate")
                .addParams("deviceid", GSONUtils.toJson(Constant.serialNumber))
                .addParams("userid", GSONUtils.toJson(employeeInfo.getId()))
                .addParams("OrderId", GSONUtils.toJson(info.getId()))
                .addParams("CardNo", GSONUtils.toJson(info.getCardNo()))
                .addParams("GrmXh", GSONUtils.toJson(info.getGrmxh()))
                .addParams("CardMan", GSONUtils.toJson(info.getCardMan()))
                .addParams("PhoneNum", GSONUtils.toJson(info.getPhoneNum()))
                .addParams("Yjtcsj", GSONUtils.toJson(info.getYjtcsj()))
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
                        ResBoolean resBoolean = GSONUtils.fromJson(response,ResBoolean.class);
                        if(resBoolean.getState() == 1){
                            mLoginView.orderSuccess();
                        }else{
                            mLoginView.orderFaild();
                        }
                    }
                });
    }

    @Override
    public void getAddress(EmployeeInfo employeeInfo) {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_IP)
                .addParams("_Interface", "Matan.User_1")
                .addParams("_Method", "MB_GetAddress")
                .addParams("deviceid", GSONUtils.toJson(Constant.serialNumber))
                .addParams("UserId", GSONUtils.toJson(employeeInfo.getEmployeeID()))
                .addParams("password", GSONUtils.toJson(employeeInfo.getPassword()))
                .addParams("RSA", GSONUtils.toJson(RSAUtils.getRSA(getRSAMapForGetAddress(employeeInfo.getEmployeeID(), employeeInfo.getPassword()))))
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
                    }
                });
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

    @NonNull
    private HashMap getRSAMapForGetAddress(String userId, String password) {
        HashMap map = new HashMap();
        map.put("deviceid", Constant.serialNumber);
        map.put("UserId", userId);
        map.put("password", password);
        return map;
    }

    @Override
    public void start() {

    }
}
