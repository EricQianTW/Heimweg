package com.hmwg.main.searchorder;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hmwg.base.BaseFragment;
import com.hmwg.eric.R;
import com.hmwg.utils.T;
import com.hmwg.utils.ValidationUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by eric_qiantw on 16/4/20.
 */
public class SearchOrderFragment extends BaseFragment implements SearchOrderContract.View {

    @Bind(R.id.searchgoods_progress)
    ProgressBar searchgoodsProgress;
    @Bind(R.id.searchgoods_tv_ordertime)
    EditText searchgoodsTvOrdertime;
    @Bind(R.id.searchgoods_tv_carframeno)
    EditText searchgoodsTvCarframeno;
    @Bind(R.id.searchgoods_tv_timmodel)
    EditText searchgoodsTvTimmodel;
    @Bind(R.id.searchgoods_tv_actureconstrustion)
    EditText searchgoodsTvActureconstrustion;
    @Bind(R.id.searchgoods_ll_form)
    LinearLayout searchgoodsLlForm;
    @Bind(R.id.searchgoods_sv_form)
    ScrollView searchgoodsSvForm;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    private SearchOrderContract.Presenter mPresenter;

    public SearchOrderFragment(){
        new SearchOrderPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.searchgoods_frg, container, false);
        ButterKnife.bind(this, view);

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

    public static SearchOrderFragment newInstance() {
        return new SearchOrderFragment();
    }

    private void initAction(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        searchgoodsTvActureconstrustion.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == R.id.actionId_register) {
                    search();
                    return true;
                }
                return false;
            }
        });
    }

    private void search(){
        mPresenter.searchTask();
    }

    @Override
    public void setPresenter(@NonNull SearchOrderContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void searchSuccess() {

    }

    @Override
    public void searchFaild() {

    }

}
