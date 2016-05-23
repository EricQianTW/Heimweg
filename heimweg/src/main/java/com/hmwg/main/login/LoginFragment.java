package com.hmwg.main.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hmwg.base.BaseFragment;
import com.hmwg.control.webview.MyWebView;
import com.hmwg.eric.R;
import com.hmwg.main.ordergoods.OrderGoodsActivity;
import com.hmwg.utils.IntentUtils;
import com.hmwg.utils.T;
import com.hmwg.utils.ValidationUtils;
import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by eric_qiantw on 16/4/20.
 */
public class LoginFragment extends BaseFragment implements LoginContract.View {

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
    @Bind(R.id.wv_login)
    MyWebView wvLogin;

    private LoginContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_frg, container, false);
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

        initAction();
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

    public LoginFragment() {
        new LoginPresenter(this);
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    private void initAction() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (checkValidation()) {
                        focusView.requestFocus();
                    } else {
                        showProgress(true, loginProgress, loginForm);
                        mPresenter.loginTask(account.getText().toString(), password.getText().toString(), getActivity());
                    }
                } catch (Exception e) {
                    Logger.e(e, TAG);
                }
            }
        });
    }

    /**
     * 验证画面控件
     *
     * @return
     */
    private boolean checkValidation() throws Exception {
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

    @Override
    public void setPresenter(@NonNull LoginContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void loginSuccess(int userId) {
        wvLogin.loadUrl("http://api.web.heimweg.com.cn/applogin.aspx?userid="+ userId +"&verify=123");
        T.showShort(getActivity(), "登录成功");
        showProgress(false, loginProgress, loginForm);
        IntentUtils.startActivityWithFinish(getActivity(), OrderGoodsActivity.class);
    }

    @Override
    public void loginFaild() {
        T.showShort(getActivity(), "登陆失败");
        showProgress(false, loginProgress, loginForm);
    }
}
