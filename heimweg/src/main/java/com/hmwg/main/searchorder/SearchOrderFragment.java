package com.hmwg.main.searchorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.hmwg.main.searchlist.SearchListActivity;
import com.hmwg.utils.DateUtils;
import com.hmwg.utils.T;
import com.hmwg.utils.ValidationUtils;
import com.hmwg.utils.ViewUtils;
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
        fab.setFocusable(true);
        fab.setFocusableInTouchMode(true);
        fab.requestFocus();
        fab.requestFocusFromTouch();
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

        searchgoodsTvOrdertime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

                    ViewUtils.clearFocus(searchgoodsTvOrdertime,fab);
                }
            }


        });

        searchgoodsTvActureconstrustion.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // DialogFragment to host SublimePicker
                    SublimePickerFragment pickerFrag = new SublimePickerFragment();
                    pickerFrag.setCallback(mFragmentTrueCallback);

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

                    ViewUtils.clearFocus(searchgoodsTvActureconstrustion,fab);
                }
            }


        });

        searchgoodsTvTimmodel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    openBottomSheet();

                    ViewUtils.clearFocus(searchgoodsTvTimmodel,fab);
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

    private void initBottomSheet() {
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
        OrderInfoAPI infoAPI = new OrderInfoAPI();
        infoAPI.setStrTjsj(searchgoodsTvOrdertime.getText().toString());
        infoAPI.setCardNo(searchgoodsTvCarframeno.getText().toString());
        infoAPI.setGrmxh(String.valueOf(fileModelId));
        infoAPI.setStrSgsj(searchgoodsTvActureconstrustion.getText().toString());
        Intent intent = new Intent(getActivity(), SearchListActivity.class);
        intent.putExtra(SearchListActivity.INTENTNAME_SEARCHINFO,infoAPI);
        startActivity(intent);
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

    @Override
    public void setFileModel(List<CODE_SPEC> array) {
        if(adapter.getSize() == 0){
            adapter.addAll(array);
            dialog = new BottomSheetDialog(getActivity());
            dialog.setContentView(recyclerView);
            dialog.show();
        }
    }

}
