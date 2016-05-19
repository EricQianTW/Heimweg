package com.hmwg.main.choosestores;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.hmwg.base.BaseAppCompatActivity;
import com.hmwg.eric.R;
import com.hmwg.main.location.LocationActivity;
import com.hmwg.utils.ActivityUtils;
import com.hmwg.utils.IntentUtils;
import com.hmwg.utils.SPUtils;
import com.hmwg.utils.T;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A login screen that offers login via email/password.
 */
public class ChooseStoresActivity extends BaseAppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.contentFrame)
    FrameLayout contentFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosestore_act);
        ButterKnife.bind(this);

        initCompatView();
        initBack();

        ChooseStoresFragment loginFragment = (ChooseStoresFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (loginFragment == null) {
            loginFragment = ChooseStoresFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    loginFragment, R.id.contentFrame);
        }

        // Create the presenter
        new ChooseStoresPresenter(loginFragment);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_province:
                        IntentUtils.startActivity(getActivity(),LocationActivity.class);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ("".equals(SPUtils.get(getApplicationContext(), SPUtils.SP_LOCATION_INFO, ""))) {
            IntentUtils.startActivity(this, LocationActivity.class);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 為了讓 Toolbar 的 Menu 有作用，這邊的程式不可以拿掉
        getMenuInflater().inflate(R.menu.menu_choosestore, menu);
        return true;
    }

}

