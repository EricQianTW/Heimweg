package com.hmwg.main.searchlist;

import com.hmwg.base.BaseInterfacePresenter;
import com.hmwg.base.BaseInterfaceView;
import com.hmwg.bean.CODE_SPEC;
import com.hmwg.bean.OrderInfoAPI;

import java.util.List;

/**
 * Created by eric_qiantw on 16/5/8.
 */
public interface SearchListContract {

    interface View extends BaseInterfaceView<Presenter> {
        void searchSuccess(List<OrderInfoAPI> array);
        void searchFaild();
        void deleteSuccess();
    }

    interface Presenter extends BaseInterfacePresenter {
        void searchTask(int userId,OrderInfoAPI infoAPI);
        void deleteTask(int userId,int orderId);
    }

}