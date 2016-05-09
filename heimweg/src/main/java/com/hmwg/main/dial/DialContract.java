package com.hmwg.main.dial;

import android.content.Context;

import com.hmwg.base.BaseInterfacePresenter;
import com.hmwg.base.BaseInterfaceView;

/**
 * Created by eric_qiantw on 16/5/8.
 */
public interface DialContract {

    interface View extends BaseInterfaceView<Presenter> {
        void dialSuccess();
        void dialFaild();
    }

    interface Presenter extends BaseInterfacePresenter {
        void dialTask();
    }

}