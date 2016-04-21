package com.hmwg.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hmwg.base.BaseAppCompatActivity;
import com.hmwg.eric.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchGoodsActivity extends BaseAppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchgoods);
        ButterKnife.bind(this);
        initCompatView();
        initBack();
        initView();
        initAction();
    }

    private void initView() {
    }

    private void initAction() {
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



    }
}
