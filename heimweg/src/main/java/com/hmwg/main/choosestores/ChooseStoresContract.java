package com.hmwg.main.choosestores;

import android.content.Context;

import com.hmwg.base.BaseInterfacePresenter;
import com.hmwg.base.BaseInterfaceView;
import com.hmwg.bean.SaleList;

import java.util.List;

/**
 * Created by eric_qiantw on 16/5/8.
 */
public interface ChooseStoresContract {

    interface View extends BaseInterfaceView<Presenter> {
        void setStore(List<SaleList> array);
    }

    interface Presenter extends BaseInterfacePresenter {
        void getStore(int userId,String provin);
    }

}