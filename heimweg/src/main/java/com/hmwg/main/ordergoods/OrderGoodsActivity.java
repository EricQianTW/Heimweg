package com.hmwg.main.ordergoods;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.hmwg.base.AppManager;
import com.hmwg.base.BaseAppCompatActivity;
import com.hmwg.eric.R;
import com.hmwg.utils.ActivityUtils;
import com.hmwg.utils.T;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OrderGoodsActivity extends BaseAppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.contentFrame)
    FrameLayout contentFrame;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ordergoods_act);
        ButterKnife.bind(this);
        initCompatView();
        initAction();

        OrderGoodsFragment orderGoodsFragment = (OrderGoodsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (orderGoodsFragment == null) {
            orderGoodsFragment = OrderGoodsFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    orderGoodsFragment, R.id.contentFrame);
        }

        // Create the presenter
        new OrderGoodsPresenter(orderGoodsFragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initAction() {
        navView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        initMenuAction(item.getItemId());
        drawerLayout.closeDrawer(GravityCompat.START);
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
