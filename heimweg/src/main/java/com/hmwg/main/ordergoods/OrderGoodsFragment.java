package com.hmwg.main.ordergoods;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
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
import com.hmwg.bean.OrderInfo;
import com.hmwg.control.DateTimePicker.SublimePickerFragment;
import com.hmwg.control.DateTimePicker.Tools;
import com.hmwg.eric.R;
import com.hmwg.utils.DateUtils;
import com.hmwg.utils.T;
import com.hmwg.utils.ValidationUtils;

import java.util.Date;

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
    @Bind(R.id.ordergoods_tv_deliveraddress)
    EditText ordergoodsTvDeliveraddress;
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

    private OrderGoodsContract.Presenter mPresenter;

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
        initView();
        initAction();
    }

    private void initView() {
        ordergoodsTvOrdertime.setText(DateUtils.dateToString(new Date(), DateUtils.F19));

        mPresenter.getAddress(user);
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
                if(hasFocus){
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

                    ordergoodsTvExpcartime.clearFocus();

                    fab.setFocusable(true);
                    fab.setFocusableInTouchMode(true);
                    fab.requestFocus();
                    fab.requestFocusFromTouch();
                }
            }
        });

        ordergoodsTilExpcartime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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

    private OrderInfo setModel() {
        OrderInfo info = new OrderInfo();
        info.setYjtcsj(ordergoodsTvExpcartime.getText().toString());
        info.setAddress(ordergoodsTvDeliveraddress.getText().toString());
        info.setPhoneNum(ordergoodsTvPhone.getText().toString());
        info.setGrmxh(ordergoodsTvTimmodel.getText().toString());
        info.setCardMan(ordergoodsTvCarownername.getText().toString());
        info.setCardNo(ordergoodsTvCarframeno.getText().toString());
        info.setCreateTime(ordergoodsTvOrdertime.getText().toString());
        return info;
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
        if (validation.isEmpty(ordergoodsTvDeliveraddress, validation.isEmptyMessage(R.string.ordergoods_tv_deliveraddress))) {
            focusView = ordergoodsTvDeliveraddress;
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
    public void loginSuccess() {
        showProgress(false, ordergoodsProgress, ordergoodsSvForm);
        T.showShort(getActivity(), "下单成功");
    }

    @Override
    public void loginFaild() {
        showProgress(false, ordergoodsProgress, ordergoodsSvForm);
        T.showShort(getActivity(), "下单失败");
    }
}
