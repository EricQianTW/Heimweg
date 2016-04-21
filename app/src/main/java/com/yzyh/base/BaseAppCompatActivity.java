package com.yzyh.base;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.yzyh.eric.R;
import com.yzyh.utils.T;

/**
 * Created by Abaddon on 2016/2/20.
 */
public class BaseAppCompatActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    protected AppCompatActivity mContext = BaseAppCompatActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    protected void initCommonView(Toolbar toolbar,DrawerLayout drawer,NavigationView navView) {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    protected void initMenuAction(int id) {
        if (id == R.id.nav_newsread) {
            T.showLong(mContext, "nav_newsread");
        } else if (id == R.id.nav_dynamicread) {
            T.showLong(mContext,"nav_dynamicread");
        } else if (id == R.id.nav_messageread) {
            T.showLong(mContext,"nav_messageread");
        } else if (id == R.id.nav_searchnews) {
            T.showLong(mContext,"nav_searchnews");
        } else if (id == R.id.nav_searchcompany) {
            T.showLong(mContext,"nav_searchcompany");
        } else if (id == R.id.nav_searchgoods) {
            T.showLong(mContext,"nav_searchgoods");
        } else if (id == R.id.nav_searchcdeal) {
            T.showLong(mContext,"nav_searchcdeal");
        } else if (id == R.id.nav_searchcperson) {
            T.showLong(mContext,"nav_searchcperson");
        } else if (id == R.id.nav_jobcompany) {
            T.showLong(mContext,"nav_jobcompany");
        } else if (id == R.id.nav_jobcontacts) {
            T.showLong(mContext,"nav_jobcontacts");
        } else if (id == R.id.nav_jobproject) {
            T.showLong(mContext,"nav_jobproject");
        } else if (id == R.id.nav_jobactivity) {
            T.showLong(mContext,"nav_jobactivity");
        } else if (id == R.id.nav_jobothers) {
            T.showLong(mContext,"nav_jobothers");
        } else if (id == R.id.nav_minesigleinfo) {
            T.showLong(mContext,"nav_minesigleinfo");
        } else if (id == R.id.nav_minecompanyinfo) {
            T.showLong(mContext,"nav_minecompanyinfo");
        } else if (id == R.id.nav_mineprivacypolicy) {
            T.showLong(mContext,"nav_mineprivacypolicy");
        } else if (id == R.id.nav_mineset) {
            T.showLong(mContext,"nav_mineset");
        } else if (id == R.id.nav_mineabout) {
            T.showLong(mContext,"nav_mineabout");
        }
    }
}
