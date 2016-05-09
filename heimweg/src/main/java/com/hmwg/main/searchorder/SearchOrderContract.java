package com.hmwg.main.searchorder;

import com.hmwg.base.BaseInterfacePresenter;
import com.hmwg.base.BaseInterfaceView;

/**
 * Created by eric_qiantw on 16/5/8.
 */
public interface SearchOrderContract {

    interface View extends BaseInterfaceView<Presenter> {
        void searchSuccess();
        void searchFaild();
    }

    interface Presenter extends BaseInterfacePresenter {
        void searchTask();
    }

}