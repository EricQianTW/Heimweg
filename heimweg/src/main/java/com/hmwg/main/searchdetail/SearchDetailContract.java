package com.hmwg.main.searchdetail;

import com.hmwg.base.BaseInterfacePresenter;
import com.hmwg.base.BaseInterfaceView;
import com.hmwg.bean.OrderInfoAPI;

import java.util.List;

/**
 * Created by eric_qiantw on 16/5/8.
 */
public interface SearchDetailContract {

    interface View extends BaseInterfaceView<Presenter> {
        void searchSuccess(OrderInfoAPI info);
        void searchFaild();
        void deleteSuccess();
    }

    interface Presenter extends BaseInterfacePresenter {
        void searchTask(int userId,int orderId);
        void deleteTask(int userId,int orderId);
    }

}