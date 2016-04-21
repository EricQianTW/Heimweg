package com.hmwg.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hmwg.base.BaseAppCompatActivity;
import com.hmwg.bean.User;
import com.hmwg.common.Constant;
import com.hmwg.eric.R;
import com.hmwg.utils.GSONUtils;
import com.hmwg.utils.L;
import com.hmwg.utils.T;
import com.hmwg.utils.ValidationUtils;
import com.hmwg.utils.okhttp.OkHttpUtils;
import com.hmwg.utils.okhttp.callback.Callback;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 注册画面
 */
public class RegisterActivity extends BaseAppCompatActivity {

    @Bind(R.id.register_tv_storename)
    AutoCompleteTextView registerTvStorename;
    @Bind(R.id.register_tv_username)
    AutoCompleteTextView registerTvUsername;
    @Bind(R.id.register_tv_account)
    AutoCompleteTextView registerTvAccount;
    @Bind(R.id.register_tv_password)
    EditText registerTvPassword;
    @Bind(R.id.register_tv_phone)
    AutoCompleteTextView registerTvPhone;
    @Bind(R.id.register_tv_identify)
    AutoCompleteTextView registerTvIdentify;
    @Bind(R.id.email_register_form)
    LinearLayout emailRegisterForm;
    @Bind(R.id.register_form)
    ScrollView registerForm;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.register_progress)
    ProgressBar registerProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initCompatView();
        initBack();
        initView();
        initAction();
    }

    private void initView() {
        ArrayAdapter<String> countries = new ArrayAdapter<String>(this,
                                                                  R.layout.common_listitem,
                                                                  getResources().getStringArray(R.array.register_storename));

        registerTvStorename.setAdapter(countries);
    }

    private void initAction() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });

        registerTvIdentify.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == R.id.actionId_register) {
                    attemptRegister();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 注册方法
     */
    private void attemptRegister() {
        if (checkValidation()) {
            focusView.requestFocus();
        } else {
            showProgress(true,registerProgress,registerForm);
            okHttp();
        }
    }

    /**
     * 验证画面控件
     * @return
     */
    private boolean checkValidation(){
        //Reset errors
        ValidationUtils.resetErrorControls(registerForm);
        if (validation.isEmpty(registerTvStorename,validation.isEmptyMessage(R.string.register_tv_storename))) {
            focusView = registerTvStorename;
            return true;
        }
        if (validation.isEmpty(registerTvUsername,validation.isEmptyMessage(R.string.register_tv_username))) {
            focusView = registerTvUsername;
            return true;
        }
        if (validation.isEmpty(registerTvAccount,validation.isEmptyMessage(R.string.register_tv_account))) {
            focusView = registerTvAccount;
            return true;
        }
        if (validation.isEmpty(registerTvPassword,validation.isEmptyMessage(R.string.register_tv_password))) {
            focusView = registerTvPassword;
            return true;
        }
        if (validation.isEmpty(registerTvPhone,validation.isEmptyMessage(R.string.register_tv_phone))) {
            focusView = registerTvPhone;
            return true;
        }
        if (validation.isEmpty(registerTvIdentify,validation.isEmptyMessage(R.string.register_tv_identify))) {
            focusView = registerTvIdentify;
            return true;
        }

        return false;
    }

    public void okHttp()
    {
        OkHttpUtils
                .get()
                .url(Constant.HTTP_IP)
                .addParams("_Interface", "Matan.User_1")
                .addParams("_Method", "MBUserLogin")
                .addParams("deviceid", "123")
                .addParams("accountMobile", "13739146726")
                .addParams("password", "198756")
                .build()//
                .execute(new Callback<User>()
                {
                    @Override
                    public User parseNetworkResponse(Response response) throws Exception {
                        String string = response.body().string();
                        User user = new GSONUtils().fromJson(string, User.class);
                        return null;
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        showProgress(false,registerProgress,registerForm);
                        L.e(TAG,e.toString());
                    }

                    @Override
                    public void onResponse(User response) {
                        showProgress(false,registerProgress,registerForm);
                        T.showShort(getActivity(),"注册成功");
                    }
                });
    }
}

