package com.hmwg.main.location;

import android.content.Context;

import com.hmwg.base.BaseInterfacePresenter;
import com.hmwg.base.BaseInterfaceView;
import com.hmwg.bean.LocationInfo;

import java.util.List;

/**
 * Created by eric_qiantw on 16/5/8.
 */
public interface LocationContract {

    interface View extends BaseInterfaceView<Presenter> {
        void setLocation();
    }

    interface Presenter extends BaseInterfacePresenter {
        List<LocationInfo> getLocation();
    }

}