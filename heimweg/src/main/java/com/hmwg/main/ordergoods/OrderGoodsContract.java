package com.hmwg.main.ordergoods;

import com.hmwg.base.BaseInterfacePresenter;
import com.hmwg.base.BaseInterfaceView;
import com.hmwg.bean.CODE_SPEC;
import com.hmwg.bean.EmployeeInfo;
import com.hmwg.bean.OrderInfoAPI;

import java.util.List;

/**
 * Created by eric_qiantw on 16/5/8.
 */
public interface OrderGoodsContract {

    interface View extends BaseInterfaceView<Presenter> {
        void orderSuccess();
        void orderFaild();
        void setFileModel(List<CODE_SPEC> array);
        void setOrderInfo(OrderInfoAPI info);
    }

    interface Presenter extends BaseInterfacePresenter {
        void startOrder(int userId,String strTime,int ShopId);

        void addOrder(OrderInfoAPI info,EmployeeInfo employeeInfo);

        void getAddress(EmployeeInfo employeeInfo);

        void getFileModel(int UserId);
    }

}