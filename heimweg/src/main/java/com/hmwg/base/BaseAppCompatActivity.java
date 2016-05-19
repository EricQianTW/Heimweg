package com.hmwg.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hmwg.main.choosestores.ChooseStoresActivity;
import com.hmwg.main.dial.DialActivity;
import com.hmwg.main.ordergoods.OrderGoodsActivity;
import com.hmwg.main.register.RegisterActivity;
import com.hmwg.main.searchorder.SearchOrderActivity;
import com.hmwg.bean.EmployeeInfo;
import com.hmwg.eric.R;
import com.hmwg.utils.GSONUtils;
import com.hmwg.utils.IntentUtils;
import com.hmwg.utils.SPUtils;
import com.hmwg.utils.ValidationUtils;
import com.hmwg.utils.ViewUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by Abaddon on 2016/2/20.
 */
public class BaseAppCompatActivity extends AppCompatActivity {

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
    // 设备ID
    public static String serialNumber = android.os.Build.SERIAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        validation = new ValidationUtils(this);
        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityManager.getInstance().setCurrentActivity(this);

        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        if(nav_view != null){
            View header = nav_view.getHeaderView(0);
            if(header != null){
                TextView tv_username = (TextView) header.findViewById(R.id.tv_username);
                if(tv_username != null){
                    tv_username.setText("欢迎  " + getUser().getName());
                }
                TextView tv_location = (TextView) header.findViewById(R.id.tv_location);
                if(tv_location != null){
                    tv_location.setText(String.valueOf(SPUtils.get(this,SPUtils.SP_STORE_INFO,"")));
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * 取得当前Activity的弱引用
     * @return
     */
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

    protected void initCompatView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if(toolbar != null){
            setSupportActionBar(toolbar);
        }

        if(toolbar != null && drawer != null){
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();
        }
    }

    protected void initBack(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
        }
    }

    protected void initMenuAction(int id) {
        if (id == R.id.nav_ordergoods) {
            IntentUtils.startActivityWithFinish(getActivity(), OrderGoodsActivity.class);
        } else if (id == R.id.nav_logout) {
            SPUtils.remove(this,SPUtils.SP_LOGIN_INFO);
            AppManager.getAppManager().AppExit(getActivity());
        } else if (id == R.id.nav_choicestore) {
            IntentUtils.startActivity(getActivity(), ChooseStoresActivity.class);
        }else if (id == R.id.nav_searchorder) {
            IntentUtils.startActivity(getActivity(), SearchOrderActivity.class);
        } else if (id == R.id.nav_reel) {
            IntentUtils.startActivity(getActivity(), DialActivity.class);
        }
    }

    /**
     * Shows the progress UI and hides the register form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show,final ProgressBar progressBar,final ViewGroup viewGroup) {
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
            user = GSONUtils.fromJson(SPUtils.get(BaseAppCompatActivity.this, SPUtils.SP_LOGIN_INFO, "").toString(), EmployeeInfo.class);
        }
        return user;
    }
}
