package com.hmwg.main.login;

import android.content.Context;

import com.hmwg.base.BaseInterfacePresenter;
import com.hmwg.base.BaseInterfaceView;

/**
 * Created by eric_qiantw on 16/5/8.
 */
public interface LoginContract {

    interface View extends BaseInterfaceView<Presenter> {
        void loginSuccess(int userId);
        void loginFaild();
    }

    interface Presenter extends BaseInterfacePresenter {
        void loginTask(String userId, String passwd, Context mContext);
    }

}