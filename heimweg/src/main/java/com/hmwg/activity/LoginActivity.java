package com.hmwg.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hmwg.base.BaseAppCompatActivity;
import com.hmwg.bean.EmployeeInfo;
import com.hmwg.common.Constant;
import com.hmwg.eric.R;
import com.hmwg.utils.GSONUtils;
import com.hmwg.utils.IntentUtils;
import com.hmwg.utils.L;
import com.hmwg.utils.SPUtils;
import com.hmwg.utils.T;
import com.hmwg.utils.ValidationUtils;
import com.hmwg.utils.okhttp.OkHttpUtils;
import com.hmwg.utils.okhttp.callback.Callback;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseAppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.login_progress)
    ProgressBar loginProgress;
    @Bind(R.id.account)
    AutoCompleteTextView account;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.email_login_form)
    LinearLayout emailLoginForm;
    @Bind(R.id.login_form)
    ScrollView loginForm;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.register)
    TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
                login();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startActivityWithFinish(getActivity(),RegisterActivity.class);
            }
        });
    }

    /**
     * 注册方法
     */
    private void login() {
        if (checkValidation()) {
            focusView.requestFocus();
        } else {
            showProgress(true, loginProgress, loginForm);
            okHttp();
        }
    }

    /**
     * 验证画面控件
     *
     * @return
     */
    private boolean checkValidation() {
        //Reset errors
        ValidationUtils.resetErrorControls(loginForm);
        if (validation.isEmpty(account, validation.isEmptyMessage(R.string.prompt_account))) {
            focusView = account;
            return true;
        }
        if (validation.isEmpty(password, validation.isEmptyMessage(R.string.prompt_password))) {
            focusView = password;
            return true;
        }

        return false;
    }

    public void okHttp() {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_IP)
                .addParams("_Interface", "Matan.User_1")
                .addParams("_Method", "MBUserLogin")
                .addParams("deviceid", "123")
                .addParams("loginname", GSONUtils.toJson(account.getText().toString()))
                .addParams("password", GSONUtils.toJson(password.getText().toString()))
                .build()//
                .execute(new Callback<EmployeeInfo>() {
                    @Override
                    public EmployeeInfo parseNetworkResponse(Response response) throws Exception {
                        return GSONUtils.fromJson(response.body().string(), EmployeeInfo.class);
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        showProgress(false, loginProgress, loginForm);
                        L.e(TAG, e.toString());
                    }

                    @Override
                    public void onResponse(EmployeeInfo response) {
                        showProgress(false, loginProgress, loginForm);
                        if(response.getState() == Constant.OKHTTP_RESULT_SUCESS){
                            T.showShort(getActivity(), "登录成功");
                            EmployeeInfo info = new EmployeeInfo();
                            response.setLogin(true);
                            SPUtils.put(getActivity(),SPUtils.SP_LOGIN_INFO,GSONUtils.toJson(response));

                            IntentUtils.startActivityWithFinish(getActivity(), OrderGoodsActivity.class);
                        }else{
                            T.showShort(getActivity(),response.getCustomMessage());
                        }
                    }
                });
    }

}

