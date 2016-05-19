package com.hmwg.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.hmwg.bean.EmployeeInfo;
import com.hmwg.utils.GSONUtils;
import com.hmwg.utils.SPUtils;
import com.hmwg.utils.ValidationUtils;
import com.hmwg.utils.ViewUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by Abaddon on 2016/2/20.
 */
public class BaseActivity extends Activity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityManager.getInstance().setCurrentActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public static Activity getActivity() {
        Class activityThreadClass = null;
        try {
            activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            Map activities = (Map) activitiesField.get(activityThread);
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    Activity activity = (Activity) activityField.get(activityRecord);
                    return activity;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
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

    public EmployeeInfo getUser() {
        if (user == null) {
            user = GSONUtils.fromJson(SPUtils.get(BaseActivity.this, SPUtils.SP_LOGIN_INFO, "").toString(), EmployeeInfo.class);
        }
        return user;
    }

}
