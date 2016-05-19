package com.hmwg.main.searchdetail;

import android.os.Bundle;

import com.hmwg.base.BaseAppCompatActivity;
import com.hmwg.bean.OrderInfoAPI;
import com.hmwg.eric.R;
import com.hmwg.utils.ActivityUtils;

public class SearchDetailActivity extends BaseAppCompatActivity {

    public final static String INTENTNAME_SEARCHID = "searchId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchdetail_act);

        initCompatView();
        initBack();

        int id = getIntent().getIntExtra(INTENTNAME_SEARCHID,-1);

        SearchDetailFragment searchListFragment = (SearchDetailFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (searchListFragment == null) {
            searchListFragment = SearchDetailFragment.newInstance();

            Bundle data = new Bundle();
            data.putInt(INTENTNAME_SEARCHID,id);
            searchListFragment.setArguments(data);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    searchListFragment, R.id.contentFrame);
        }

        // Create the presenter
        new SearchDetailPresenter(searchListFragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
