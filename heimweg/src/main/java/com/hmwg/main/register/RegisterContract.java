package com.hmwg.main.register;

import com.hmwg.base.BaseInterfacePresenter;
import com.hmwg.base.BaseInterfaceView;
import com.hmwg.bean.EmployeeInfo;
import com.hmwg.bean.OrderInfo;

/**
 * Created by eric_qiantw on 16/5/8.
 */
public interface RegisterContract {

    interface View extends BaseInterfaceView<Presenter> {
        void loginSuccess();
        void loginFaild();
    }

    interface Presenter extends BaseInterfacePresenter {
        void loginTask();
    }

}