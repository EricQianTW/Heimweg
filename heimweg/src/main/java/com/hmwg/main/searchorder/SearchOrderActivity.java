package com.hmwg.main.searchorder;

import android.os.Bundle;

import com.hmwg.base.BaseAppCompatActivity;
import com.hmwg.eric.R;
import com.hmwg.utils.ActivityUtils;

public class SearchOrderActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchgoods_act);

        initCompatView();
        initBack();

        SearchOrderFragment searchOrderFragment = (SearchOrderFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (searchOrderFragment == null) {
            searchOrderFragment = SearchOrderFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    searchOrderFragment, R.id.contentFrame);
        }

        // Create the presenter
        new SearchOrderPresenter(searchOrderFragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
