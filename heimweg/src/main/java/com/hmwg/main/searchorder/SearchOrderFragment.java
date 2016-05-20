package com.hmwg.main.searchorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.hmwg.base.BaseFragment;
import com.hmwg.bean.CODE_SPEC;
import com.hmwg.bean.OrderInfoAPI;
import com.hmwg.control.DateTimePicker.DateTimePickerUtils;
import com.hmwg.control.DateTimePicker.SublimePickerFragment;
import com.hmwg.control.DateTimePicker.Tools;
import com.hmwg.control.pacificadapter.HorizontalItemDecoration;
import com.hmwg.eric.R;
import com.hmwg.main.searchlist.SearchListActivity;
import com.hmwg.utils.DateUtils;
import com.hmwg.utils.ViewUtils;
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
    private RecyclerAdapter<CODE_SPEC> adapter;
    private RecyclerView recyclerView;
    private BottomSheetDialog dialog;
    private int fileModelId;

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
        try {
            initView();
            initAction();
        } catch (Exception e) {
            Logger.e(e, TAG);
        }
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

    public SearchOrderFragment(){
        new SearchOrderPresenter(this);
    }

    public static SearchOrderFragment newInstance() {
        return new SearchOrderFragment();
    }

    private void initView() throws Exception{
        fab.setFocusable(true);
        fab.setFocusableInTouchMode(true);
        fab.requestFocus();
        fab.requestFocusFromTouch();
    }

    private void initAction() throws Exception{
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

        searchgoodsTvOrdertime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DateTimePickerUtils.openDatePop(getContext(),getChildFragmentManager(),Tools.getDateOptions(),mFragmentCallback);
                    ViewUtils.clearFocus(searchgoodsTvActureconstrustion,fab);
                }
            }


        });

        searchgoodsTvActureconstrustion.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DateTimePickerUtils.openDatePop(getContext(),getChildFragmentManager(),Tools.getDateOptions(),mFragmentTrueCallback);
                    ViewUtils.clearFocus(searchgoodsTvActureconstrustion,fab);
                }
            }
        });

        searchgoodsTvTimmodel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                try {
                    if(hasFocus){
                        openBottomSheet();

                        ViewUtils.clearFocus(searchgoodsTvTimmodel,fab);
                    }
                } catch (Exception e) {
                    Logger.e(e, TAG);
                }
            }
        });
    }

    public void openBottomSheet() throws Exception{
        initBottomSheet();

        initAdapter();

        mPresenter.getFileModel(user.getId());
    }

    private void initAdapter() throws Exception{
        adapter = new RecyclerAdapter<CODE_SPEC>(getContext(), R.layout.common_adp_siglecentertext) {
            @Override
            protected void convert(final RecyclerAdapterHelper helper, final CODE_SPEC info) {
                final int position = helper.getAdapterPosition();
                helper.setText(R.id.tv_string, info.getGRMXH()).getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchgoodsTvTimmodel.setText(info.getGRMXH());
                        fileModelId = info.getId();
                        dialog.dismiss();
                    }
                });
                helper.getItemView().setTag(TAG);
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private void initBottomSheet() throws Exception{
        recyclerView = (RecyclerView) LayoutInflater.from(getActivity())
                .inflate(R.layout.common_bs_list, null);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new HorizontalItemDecoration
                .Builder(getContext())
                .colorResId(R.color.gray_88)
                .showLastDivider(true)
                .sizeResId(R.dimen.height_explore_divider_1)
                .build());
    }

    SublimePickerFragment.Callback mFragmentCallback = new SublimePickerFragment.Callback() {
        @Override
        public void onCancelled() {
        }

        @Override
        public void onDateTimeRecurrenceSet(SelectedDate selectedDate,
                                            int hourOfDay, int minute,
                                            SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                            String recurrenceRule) {
            String temp = DateUtils.calendarToString(selectedDate.getStartDate(),DateUtils.F20) + " " + hourOfDay + ":" + minute;
            searchgoodsTvOrdertime.setText(temp);
        }
    };

    SublimePickerFragment.Callback mFragmentTrueCallback = new SublimePickerFragment.Callback() {
        @Override
        public void onCancelled() {
        }

        @Override
        public void onDateTimeRecurrenceSet(SelectedDate selectedDate,
                                            int hourOfDay, int minute,
                                            SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                            String recurrenceRule) {
            String temp = DateUtils.calendarToString(selectedDate.getStartDate(),DateUtils.F20) + " " + hourOfDay + ":" + minute;
            searchgoodsTvActureconstrustion.setText(temp);
        }
    };

    private void search(){
        try {
            OrderInfoAPI infoAPI = new OrderInfoAPI();
            infoAPI.setStrTjsj(searchgoodsTvOrdertime.getText().toString());
            infoAPI.setCardNo(searchgoodsTvCarframeno.getText().toString());
            infoAPI.setGrmxh(String.valueOf(fileModelId));
            infoAPI.setStrSgsj(searchgoodsTvActureconstrustion.getText().toString());
            Intent intent = new Intent(getActivity(), SearchListActivity.class);
            intent.putExtra(SearchListActivity.INTENTNAME_SEARCHINFO,infoAPI);
            startActivity(intent);
        } catch (Exception e) {
            Logger.e(e, TAG);
        }
    }

    @Override
    public void setPresenter(@NonNull SearchOrderContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void setFileModel(List<CODE_SPEC> array) {
        try {
            if(adapter.getSize() == 0){
                adapter.addAll(array);
                dialog = new BottomSheetDialog(getActivity());
                dialog.setContentView(recyclerView);
                dialog.show();
            }
        } catch (Exception e) {
            Logger.e(e, TAG);
        }
    }

}
