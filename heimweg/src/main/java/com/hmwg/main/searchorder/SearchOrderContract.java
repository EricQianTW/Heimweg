package com.hmwg.main.searchorder;

import com.hmwg.base.BaseInterfacePresenter;
import com.hmwg.base.BaseInterfaceView;
import com.hmwg.bean.CODE_SPEC;

import java.util.List;

/**
 * Created by eric_qiantw on 16/5/8.
 */
public interface SearchOrderContract {

    interface View extends BaseInterfaceView<Presenter> {
        void setFileModel(List<CODE_SPEC> array);
    }

    interface Presenter extends BaseInterfacePresenter {
        void getFileModel(int UserId);
    }

}