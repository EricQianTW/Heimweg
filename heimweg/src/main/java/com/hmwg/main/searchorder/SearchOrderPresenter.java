package com.hmwg.main.searchorder;

import android.support.annotation.NonNull;

import com.hmwg.base.BasePresenter;
import com.hmwg.bean.User;
import com.hmwg.common.Constant;
import com.hmwg.utils.GSONUtils;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by eric_qiantw on 16/4/20.
 */
public class SearchOrderPresenter extends BasePresenter implements SearchOrderContract.Presenter{
    private final SearchOrderContract.View mLoginView;

    public SearchOrderPresenter(@NonNull SearchOrderContract.View loginView){
        mLoginView = checkNotNull(loginView, "loginView be null!");

        mLoginView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void searchTask() {

    }
}
