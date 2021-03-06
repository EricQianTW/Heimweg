package com.hmwg.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.hmwg.bean.EmployeeInfo;
import com.hmwg.control.DateTimePicker.SublimePickerFragment;
import com.hmwg.main.ordergoods.OrderGoodsActivity;
import com.hmwg.utils.GSONUtils;
import com.hmwg.utils.SPUtils;
import com.hmwg.utils.T;
import com.hmwg.utils.ValidationUtils;
import com.hmwg.utils.ViewUtils;

/**
 * Created by eric_qiantw on 16/4/20.
 */
public class BaseFragment extends Fragment {
    // 当前class名
    protected String TAG = this.getClass().getName();

    // 声明控件check对象
    protected ValidationUtils validation;

    // 声明焦点控件
    protected View focusView = null;

    // 双击退出时间
    public static long firstTime;
    // 登录者
    public static EmployeeInfo user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        validation = new ValidationUtils(getContext());
        user = ((BaseAppCompatActivity) getActivity()).getUser();
    }

    /**
     * Shows the progress UI and hides the register form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show, final ProgressBar progressBar, final ViewGroup viewGroup) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            ViewUtils.disableSubControls(viewGroup,show);
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            progressBar.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            ViewUtils.disableSubControls(viewGroup,show);
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }
}
