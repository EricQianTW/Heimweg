package com.hmwg.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hmwg.base.AppManager;
import com.hmwg.base.BaseAppCompatActivity;
import com.hmwg.bean.OrderInfo;
import com.hmwg.bean.ResBoolean;
import com.hmwg.common.Constant;
import com.hmwg.eric.R;
import com.hmwg.utils.DateUtils;
import com.hmwg.utils.GSONUtils;
import com.hmwg.utils.L;
import com.hmwg.utils.T;
import com.hmwg.utils.ValidationUtils;
import com.hmwg.utils.okhttp.OkHttpUtils;
import com.hmwg.utils.okhttp.callback.Callback;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class OrderGoodsActivity extends BaseAppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
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
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordergoods);
        ButterKnife.bind(this);
        initCompatView();
//        initBack();
        initView();
        initAction();
    }

    private void initView() {
        ordergoodsTvOrdertime.setText(DateUtils.dateToString(new Date(),DateUtils.F19));
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

        navView.setNavigationItemSelectedListener(this);
    }

    /**
     * 注册方法
     */
    private void attemptRegister() {
        if (checkValidation()) {
            focusView.requestFocus();
        } else {
            showProgress(true,ordergoodsProgress,ordergoodsSvForm);
            okHttp();
        }
    }

    /**
     * 验证画面控件
     * @return
     */
    private boolean checkValidation(){
        //Reset errors
        ValidationUtils.resetErrorControls(ordergoodsSvForm);
        if (validation.isEmpty(ordergoodsTvOrdertime,validation.isEmptyMessage(R.string.ordergoods_tv_ordertime))) {
            focusView = ordergoodsTvOrdertime;
            return true;
        }
        if (validation.isEmpty(ordergoodsTvCarframeno,validation.isEmptyMessage(R.string.ordergoods_tv_carframeno))) {
            focusView = ordergoodsTvCarframeno;
            return true;
        }
        if (validation.isEmpty(ordergoodsTvTimmodel,validation.isEmptyMessage(R.string.ordergoods_tv_timmodel))) {
            focusView = ordergoodsTvTimmodel;
            return true;
        }
        if (validation.isEmpty(ordergoodsTvCarownername,validation.isEmptyMessage(R.string.ordergoods_tv_carownername))) {
            focusView = ordergoodsTvCarownername;
            return true;
        }
        if (validation.isEmpty(ordergoodsTvPhone,validation.isEmptyMessage(R.string.ordergoods_tv_phone))) {
            focusView = ordergoodsTvPhone;
            return true;
        }
        if (validation.isEmpty(ordergoodsTvDeliveraddress,validation.isEmptyMessage(R.string.ordergoods_tv_deliveraddress))) {
            focusView = ordergoodsTvDeliveraddress;
            return true;
        }
        if (validation.isEmpty(ordergoodsTvExpcartime,validation.isEmptyMessage(R.string.ordergoods_tv_expcartime))) {
            focusView = ordergoodsTvExpcartime;
            return true;
        }

        return false;
    }

    public void okHttp() {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_IP)
                .addParams("_Interface", "Matan.OrderAbout")
                .addParams("_Method", "MeOrderAdd")
                .addParams("deviceid", GSONUtils.toJson(serialNumber))
                .addParams("userid", GSONUtils.toJson(getUser().getEmployeeID()))
                .addParams("oi", GSONUtils.toJson(setModel()))
                .build()//
                .execute(new Callback<ResBoolean>() {
                    @Override
                    public ResBoolean parseNetworkResponse(Response response) throws Exception {
                        return GSONUtils.fromJson(response.body().string(), ResBoolean.class);
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        showProgress(false,ordergoodsProgress,ordergoodsSvForm);
                        L.e(TAG, e.toString());
                    }

                    @Override
                    public void onResponse(ResBoolean response) {
                        showProgress(false,ordergoodsProgress,ordergoodsSvForm);
                        T.showShort(getActivity(), "下单成功");
                    }
                });
    }

    private OrderInfo setModel(){
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        initMenuAction(item.getItemId());
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 连续按两次返回键就退出
     */
    @Override
    public void onBackPressed() {
        if (firstTime + 2000 > System.currentTimeMillis()) {
            AppManager.getAppManager().AppExit(getActivity());
        } else {
            T.showShort(this, "再按一次退出程序");
        }
        firstTime = System.currentTimeMillis();
    }
}
