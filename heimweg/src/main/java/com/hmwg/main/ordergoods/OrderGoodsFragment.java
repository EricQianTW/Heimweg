package com.hmwg.main.ordergoods;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
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
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.hmwg.base.BaseFragment;
import com.hmwg.bean.CODE_SPEC;
import com.hmwg.bean.OrderInfoAPI;
import com.hmwg.control.DateTimePicker.SublimePickerFragment;
import com.hmwg.control.DateTimePicker.Tools;
import com.hmwg.control.pacificadapter.HorizontalItemDecoration;
import com.hmwg.eric.R;
import com.hmwg.utils.DateUtils;
import com.hmwg.utils.SPUtils;
import com.hmwg.utils.T;
import com.hmwg.utils.ValidationUtils;
import com.hmwg.utils.ViewUtils;
import com.pacific.adapter.RecyclerAdapter;
import com.pacific.adapter.RecyclerAdapterHelper;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by eric_qiantw on 16/4/20.
 */
public class OrderGoodsFragment extends BaseFragment implements OrderGoodsContract.View {

    @Bind(R.id.ordergoods_progress)
    ProgressBar ordergoodsProgress;
    @Bind(R.id.ordergoods_tv_ordertime)
    EditText ordergoodsTvOrdertime;
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
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.ordergoods_ll_form)
    LinearLayout ordergoodsLlForm;
    @Bind(R.id.ordergoods_sv_form)
    ScrollView ordergoodsSvForm;
    @Bind(R.id.ordergoods_til_expcartime)
    TextInputLayout ordergoodsTilExpcartime;
    @Bind(R.id.ordergoods_tv_store)
    EditText ordergoodsTvStore;

    private OrderGoodsContract.Presenter mPresenter;
    private RecyclerAdapter<CODE_SPEC> adapter;
    private RecyclerView recyclerView;
    private BottomSheetDialog dialog;
    private OrderInfoAPI orderInfo;
    private int fileModelId;

    public OrderGoodsFragment() {
        new OrderGoodsPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ordergoods_frg, container, false);
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

    }

    private void initView() {
        ordergoodsTvOrdertime.setText(DateUtils.dateToString(new Date(), DateUtils.F19));
        ordergoodsTvStore.setText(SPUtils.get(getActivity(),SPUtils.SP_STORE_INFO,"").toString());
        if(!"".equals(SPUtils.get(getActivity(),SPUtils.SP_STOREID_INFO,""))) {
            mPresenter.startOrder(user.getId(),ordergoodsTvOrdertime.getText().toString(),Integer.parseInt(SPUtils.get(getActivity(),SPUtils.SP_STOREID_INFO,"").toString()));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
        initView();
        initAction();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public static OrderGoodsFragment newInstance() {
        return new OrderGoodsFragment();
    }

    private void initAction() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });

        ordergoodsTvExpcartime.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == R.id.actionId_register) {
                    attemptRegister();
                    return true;
                }
                return false;
            }
        });

        ordergoodsTvExpcartime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // DialogFragment to host SublimePicker
                    SublimePickerFragment pickerFrag = new SublimePickerFragment();
                    pickerFrag.setCallback(mFragmentCallback);

                    // Options
                    Pair<Boolean, SublimeOptions> optionsPair = Tools.getNormalOptions();

                    if (!optionsPair.first) { // If options are not valid
                        T.showShort(getContext(), "No pickers activated");
                        return;
                    }

                    // Valid options
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("SUBLIME_OPTIONS", optionsPair.second);
                    pickerFrag.setArguments(bundle);

                    pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
                    pickerFrag.show(getChildFragmentManager(), "SUBLIME_PICKER");

                    ViewUtils.clearFocus(ordergoodsTvExpcartime,fab);
                }
            }


        });

        ordergoodsTvTimmodel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    openBottomSheet();

                    ViewUtils.clearFocus(ordergoodsTvTimmodel,fab);
                }
            }
        });
    }

    public void openBottomSheet() {
        initBottomSheet();

        initAdapter();

        mPresenter.getFileModel(user.getId());
    }

    private void initAdapter() {
        adapter = new RecyclerAdapter<CODE_SPEC>(getContext(), R.layout.common_adp_siglecentertext) {
            @Override
            protected void convert(final RecyclerAdapterHelper helper, final CODE_SPEC info) {
                final int position = helper.getAdapterPosition();
                helper.setText(R.id.tv_string, info.getGRMXH()).getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ordergoodsTvTimmodel.setText(info.getGRMXH());
                        fileModelId = info.getId();
                        dialog.dismiss();
                    }
                });
                helper.getItemView().setTag(TAG);
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private void initBottomSheet() {
        recyclerView = (RecyclerView) LayoutInflater.from(getActivity())
                .inflate(R.layout.common_bs_list, null);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new HorizontalItemDecoration
                .Builder(getContext())
                .colorResId(R.color.gray_88)
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
            ordergoodsTvExpcartime.setText(temp);
        }
    };

    /**
     * 注册方法
     */
    private void attemptRegister() {
        if (checkValidation()) {
            focusView.requestFocus();
        } else {
            showProgress(true, ordergoodsProgress, ordergoodsSvForm);
            mPresenter.addOrder(setModel(), user);
        }
    }

    private OrderInfoAPI setModel() {
        orderInfo.setYjtcsj(ordergoodsTvExpcartime.getText().toString());
        orderInfo.setPhoneNum(ordergoodsTvPhone.getText().toString());
        orderInfo.setGrmxh(String.valueOf(fileModelId));
        orderInfo.setCardMan(ordergoodsTvCarownername.getText().toString());
        orderInfo.setCardNo(ordergoodsTvCarframeno.getText().toString());
        orderInfo.setStrCreateTime(ordergoodsTvOrdertime.getText().toString());
        orderInfo.setShopName(ordergoodsTvStore.getText().toString());
        return orderInfo;
    }

    /**
     * 验证画面控件
     *
     * @return
     */
    private boolean checkValidation() {
        //Reset errors
        ValidationUtils.resetErrorControls(ordergoodsSvForm);
        if (validation.isEmpty(ordergoodsTvOrdertime, validation.isEmptyMessage(R.string.ordergoods_tv_ordertime))) {
            focusView = ordergoodsTvOrdertime;
            return true;
        }
        if (validation.isEmpty(ordergoodsTvCarframeno, validation.isEmptyMessage(R.string.ordergoods_tv_carframeno))) {
            focusView = ordergoodsTvCarframeno;
            return true;
        }
        if (validation.isEmpty(ordergoodsTvTimmodel, validation.isEmptyMessage(R.string.ordergoods_tv_timmodel))) {
            focusView = ordergoodsTvTimmodel;
            return true;
        }
        if (validation.isEmpty(ordergoodsTvCarownername, validation.isEmptyMessage(R.string.ordergoods_tv_carownername))) {
            focusView = ordergoodsTvCarownername;
            return true;
        }
        if (validation.isEmpty(ordergoodsTvPhone, validation.isEmptyMessage(R.string.ordergoods_tv_phone))) {
            focusView = ordergoodsTvPhone;
            return true;
        }
        if (validation.isNotMobileNumber(ordergoodsTvPhone, validation.isNotMobileNumberMessage(R.string.ordergoods_tv_phone))) {
            focusView = ordergoodsTvPhone;
            return true;
        }
        if (validation.isEmpty(ordergoodsTvExpcartime, validation.isEmptyMessage(R.string.ordergoods_tv_expcartime))) {
            focusView = ordergoodsTvExpcartime;
            return true;
        }

        return false;
    }

    @Override
    public void setPresenter(@NonNull OrderGoodsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void orderSuccess() {
        showProgress(false, ordergoodsProgress, ordergoodsSvForm);
        T.showShort(getActivity(), "下单成功");
    }

    @Override
    public void orderFaild() {
        showProgress(false, ordergoodsProgress, ordergoodsSvForm);
        T.showShort(getActivity(), "下单失败");
    }

    @Override
    public void setFileModel(List<CODE_SPEC> array) {
        if(adapter.getSize() == 0){
            adapter.addAll(array);
            dialog = new BottomSheetDialog(getActivity());
            dialog.setContentView(recyclerView);
            dialog.show();
        }
    }

    @Override
    public void setOrderInfo(OrderInfoAPI info) {
        orderInfo = info;
    }
}
