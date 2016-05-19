package com.hmwg.main.location;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hmwg.base.BasePresenter;
import com.hmwg.bean.EmployeeInfo;
import com.hmwg.bean.LocationInfo;
import com.hmwg.common.Constant;
import com.hmwg.utils.GSONUtils;
import com.hmwg.utils.SPUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by eric_qiantw on 16/4/20.
 */
public class LocationPresenter extends BasePresenter implements LocationContract.Presenter{
    private final LocationContract.View mLoginView;
    private String[] provinceArr = {"北京市","天津市","重庆市","上海市","河北省","山西省","辽宁省","吉林省","黑龙江省","江苏省","浙江省","安徽省","福建省","江西省","山东省","河南省","湖北省","湖南省","广东省","海南省","四川省","贵州省","云南省","陕西省","甘肃省","青海省","台湾省","内蒙古自治区","广西壮族自治区","西藏自治区","宁夏回族自治区","新疆维吾尔自治区","香港特别行政区","澳门特别行政区"};

    public LocationPresenter(@NonNull LocationContract.View loginView){
        mLoginView = checkNotNull(loginView, "loginView be null!");

        mLoginView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public List<LocationInfo> getLocation() {
        List<LocationInfo> arr = new ArrayList<LocationInfo>();
        for (int i = 0; i < provinceArr.length; i++) {
            LocationInfo info = new LocationInfo();
            info.setLocation(provinceArr[i]);
            arr.add(info);
        }
        return arr;
    }
}
