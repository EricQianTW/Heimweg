package com.hmwg.main.ordergoods;

import android.content.Context;

import com.hmwg.base.BaseInterfacePresenter;
import com.hmwg.base.BaseInterfaceView;
import com.hmwg.bean.EmployeeInfo;
import com.hmwg.bean.OrderInfo;

/**
 * Created by eric_qiantw on 16/5/8.
 */
public interface OrderGoodsContract {

    interface View extends BaseInterfaceView<Presenter> {
        void loginSuccess();
        void loginFaild();
    }

    interface Presenter extends BaseInterfacePresenter {
        void addOrder(OrderInfo info,EmployeeInfo employeeInfo);

        void getAddress(EmployeeInfo employeeInfo);
    }

}