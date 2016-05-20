package com.hmwg.main.searchlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.hmwg.base.BaseFragment;
import com.hmwg.bean.OrderInfoAPI;
import com.hmwg.control.pacificadapter.HorizontalItemDecoration;
import com.hmwg.eric.R;
import com.hmwg.main.searchdetail.SearchDetailActivity;
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
public class SearchListFragment extends BaseFragment implements SearchListContract.View {
    @Bind(R.id.searchgoods_progress)
    ProgressBar searchgoodsProgress;
    @Bind(R.id.rv_icon)
    RecyclerView rvIcon;

    private SearchListContract.Presenter mPresenter;
    private RecyclerAdapter<OrderInfoAPI> adapter;
    private OrderInfoAPI infoAPI;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.searchlist_frg, container, false);
        ButterKnife.bind(this, view);

        Bundle data = getArguments();
        infoAPI = (OrderInfoAPI) data.getSerializable(SearchListActivity.INTENTNAME_SEARCHINFO);

        // 让Fragment中的onCreateOptionsMenu生效必须先调用setHasOptionsMenu方法
        setHasOptionsMenu(true);
        // 在 Activity 重绘时，Fragment 不会被重复绘制，也就是它会被“保留”
        setRetainInstance(true);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            initAdapter();
            initBottomSheet();
        } catch (Exception e) {
            Logger.e(e, TAG);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
        try {
            initData();
        } catch (Exception e) {
            Logger.e(e, TAG);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public SearchListFragment() {
        new SearchListPresenter(this);
    }

    private void initData() throws Exception{
        mPresenter.searchTask(user.getId(),infoAPI);
    }

    public static SearchListFragment newInstance() {
        return new SearchListFragment();
    }

    private void initAdapter() throws Exception{
        adapter = new RecyclerAdapter<OrderInfoAPI>(getContext(), R.layout.searchlist_adp) {
            @Override
            protected void convert(final RecyclerAdapterHelper helper, final OrderInfoAPI info) {
                final int position = helper.getAdapterPosition();
                helper.setText(R.id.tv_danhao, "单号：" + String.valueOf(info.getId()))
                      .setText(R.id.tv_wangdian, "网点：" + info.getAddress())
                      .setText(R.id.tv_chejiahao, "车架号：" + info.getCardNo()).getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), SearchDetailActivity.class);
                        intent.putExtra(SearchDetailActivity.INTENTNAME_SEARCHID,info.getId());
                        startActivity(intent);
                    }
                });
                helper.getItemView().setTag(TAG);
            }
        };
    }

    private void initBottomSheet() throws Exception{
        rvIcon.setItemAnimator(new DefaultItemAnimator());
        rvIcon.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvIcon.addItemDecoration(new HorizontalItemDecoration
                .Builder(getContext())
                .colorResId(R.color.gray_88)
                .sizeResId(R.dimen.height_explore_divider_1)
                .build());
        rvIcon.setAdapter(adapter);
    }

    @Override
    public void setPresenter(@NonNull SearchListContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void searchSuccess(List<OrderInfoAPI> infoAPIs) {
        try {
            if(infoAPIs != null){
                adapter.clear();
                adapter.addAll(infoAPIs);
            }
        } catch (Exception e) {
            Logger.e(e, TAG);
        }
    }

    @Override
    public void searchFaild() {

    }

}
