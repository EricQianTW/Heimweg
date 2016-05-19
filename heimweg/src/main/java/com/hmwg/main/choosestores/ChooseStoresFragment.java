package com.hmwg.main.choosestores;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.hmwg.base.BaseFragment;
import com.hmwg.bean.SaleList;
import com.hmwg.control.pacificadapter.HorizontalItemDecoration;
import com.hmwg.eric.R;
import com.hmwg.utils.SPUtils;
import com.hmwg.utils.T;
import com.orhanobut.logger.Logger;
import com.pacific.adapter.RecyclerAdapter;
import com.pacific.adapter.RecyclerAdapterHelper;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by eric_qiantw on 16/4/20.
 */
public class ChooseStoresFragment extends BaseFragment implements ChooseStoresContract.View {

    @Bind(R.id.location_progress)
    ProgressBar locationProgress;
    @Bind(R.id.rv_icon)
    RecyclerView rvIcon;
    @Bind(R.id.location_form)
    ScrollView locationForm;
    private ChooseStoresContract.Presenter mPresenter;
    private RecyclerAdapter<SaleList> adapter;

    public ChooseStoresFragment() {
        new ChooseStoresPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choosestore_frg, container, false);
        ButterKnife.bind(this, view);

        try{
            initAdapter();

            initViews();
        }catch (Exception e){
            Logger.e(e, TAG + "");
        }

        // 让Fragment中的onCreateOptionsMenu生效必须先调用setHasOptionsMenu方法
        setHasOptionsMenu(true);
        // 在 Activity 重绘时，Fragment 不会被重复绘制，也就是它会被“保留”
        setRetainInstance(true);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public static ChooseStoresFragment newInstance() {
        return new ChooseStoresFragment();
    }

    @Override
    public void setPresenter(@NonNull ChooseStoresContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void setStore(List<SaleList> array) {
        adapter.replaceAll(array);
    }

    private void initViews() throws Exception {
        rvIcon.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvIcon.addItemDecoration(new HorizontalItemDecoration
                .Builder(getContext())
                .colorResId(R.color.gray_88)
                .sizeResId(R.dimen.height_explore_divider_1)
                .build());
        rvIcon.setAdapter(adapter);
    }

    private void initData(){
        mPresenter.getStore(user.getId(),SPUtils.get(getActivity(),SPUtils.SP_LOCATION_INFO,"").toString());
    }

    private void initAdapter() {
        adapter = new RecyclerAdapter<SaleList>(getContext(), R.layout.common_adp_siglelefttext) {
            @Override
            protected void convert(final RecyclerAdapterHelper helper, final SaleList info) {
                final int position = helper.getAdapterPosition();
                helper.setText(R.id.tv_string, info.getShopName()).getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        T.showShort(getActivity(), "您选择" + info.getShopName());
                        SPUtils.put(getActivity(), SPUtils.SP_STORE_INFO, info.getShopName());
                        SPUtils.put(getActivity(), SPUtils.SP_STOREID_INFO, String.valueOf(info.getId()));
                        getActivity().finish();
                    }
                });
                helper.getItemView().setTag(TAG);
            }
        };
    }
}
