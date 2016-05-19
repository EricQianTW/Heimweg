package com.hmwg.main.searchdetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.hmwg.base.BaseFragment;
import com.hmwg.bean.OrderInfoAPI;
import com.hmwg.control.DialogViewUtils;
import com.hmwg.eric.R;
import com.hmwg.utils.T;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by eric_qiantw on 16/4/20.
 */
public class SearchDetailFragment extends BaseFragment implements SearchDetailContract.View {

    @Bind(R.id.ordergoods_progress)
    ProgressBar ordergoodsProgress;
    @Bind(R.id.ordergoods_tv_ordertime)
    EditText ordergoodsTvOrdertime;
    @Bind(R.id.ordergoods_tv_store)
    EditText ordergoodsTvStore;
    @Bind(R.id.ordergoods_tv_carframeno)
    EditText ordergoodsTvCarframeno;
    @Bind(R.id.ordergoods_tv_timmodel)
    EditText ordergoodsTvTimmodel;
    @Bind(R.id.ordergoods_tv_carownername)
    EditText ordergoodsTvCarownername;
    @Bind(R.id.ordergoods_tv_phone)
    EditText ordergoodsTvPhone;
    @Bind(R.id.ordergoods_tv_expcartime)
    EditText ordergoodsTvExpcartime;
    @Bind(R.id.ordergoods_til_expcartime)
    TextInputLayout ordergoodsTilExpcartime;
    @Bind(R.id.ordergoods_ll_form)
    LinearLayout ordergoodsLlForm;
    @Bind(R.id.ordergoods_sv_form)
    ScrollView ordergoodsSvForm;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    private SearchDetailContract.Presenter mPresenter;
    private int infoId;

    public SearchDetailFragment() {
        new SearchDetailPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.searchdetail_frg, container, false);
        ButterKnife.bind(this, view);

        Bundle data = getArguments();
        infoId = data.getInt(SearchDetailActivity.INTENTNAME_SEARCHID);

        // 让Fragment中的onCreateOptionsMenu生效必须先调用setHasOptionsMenu方法
        setHasOptionsMenu(true);
        // 在 Activity 重绘时，Fragment 不会被重复绘制，也就是它会被“保留”
        setRetainInstance(true);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initAction();
    }

    private void initView() {
        mPresenter.searchTask(user.getId(), infoId);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public static SearchDetailFragment newInstance() {
        return new SearchDetailFragment();
    }

    private void initAction() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogViewUtils.showNoneView(getActivity(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogViewUtils.dialog.dismiss();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.deleteTask(user.getId(),infoId);

                    }
                }, "取消", "确认", "是否确认删除");

            }
        });
    }

    @Override
    public void setPresenter(@NonNull SearchDetailContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void searchSuccess(OrderInfoAPI infoAPIs) {
        ordergoodsTvOrdertime.setText(infoAPIs.getStrCreateTime());
        ordergoodsTvStore.setText(infoAPIs.getAddress());
        ordergoodsTvCarframeno.setText(infoAPIs.getCardNo());
        ordergoodsTvTimmodel.setText(infoAPIs.getGrmxh());
        ordergoodsTvCarownername.setText(infoAPIs.getCardMan());
        ordergoodsTvPhone.setText(infoAPIs.getPhoneNum());
        if(infoAPIs.getStrSgsj()!=null && !"".equals(infoAPIs.getStrSgsj())){
            ordergoodsTvExpcartime.setText(infoAPIs.getStrSgsj());
        }else{
            ordergoodsTvExpcartime.setText("暂无");
        }
    }

    @Override
    public void searchFaild() {

    }

    @Override
    public void deleteSuccess() {
        DialogViewUtils.dialog.dismiss();
        T.showShort(getActivity(),"删除成功");
        getActivity().finish();
    }

}
