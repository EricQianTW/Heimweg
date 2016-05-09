package com.hmwg.main.register;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
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
public class RegisterFragment extends BaseFragment implements RegisterContract.View {

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
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.register_progress)
    ProgressBar registerProgress;

    private RegisterContract.Presenter mPresenter;

    public RegisterFragment(){
        new RegisterPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_frg, container, false);
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
        ArrayAdapter<String> countries = new ArrayAdapter<String>(getActivity(),
                R.layout.common_listitem,
                getResources().getStringArray(R.array.register_storename));

        registerTvStorename.setAdapter(countries);
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

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    private void initAction(){
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
            mPresenter.loginTask();
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

    @Override
    public void setPresenter(@NonNull RegisterContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void loginSuccess() {
        T.showShort(getActivity(), "注册成功");
    }

    @Override
    public void loginFaild() {
        T.showShort(getActivity(),"注册失败");
    }
}
