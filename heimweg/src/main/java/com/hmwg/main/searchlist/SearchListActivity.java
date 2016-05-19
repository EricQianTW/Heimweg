package com.hmwg.main.searchlist;

import android.os.Bundle;

import com.hmwg.base.BaseAppCompatActivity;
import com.hmwg.bean.OrderInfoAPI;
import com.hmwg.eric.R;
import com.hmwg.utils.ActivityUtils;

public class SearchListActivity extends BaseAppCompatActivity {

    public final static String INTENTNAME_SEARCHINFO = "searchinfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchlist_act);

        initCompatView();
        initBack();

        OrderInfoAPI infoAPI = (OrderInfoAPI) getIntent().getSerializableExtra(INTENTNAME_SEARCHINFO);

        SearchListFragment searchListFragment = (SearchListFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (searchListFragment == null) {
            searchListFragment = SearchListFragment.newInstance();

            Bundle data = new Bundle();
            data.putSerializable(INTENTNAME_SEARCHINFO,infoAPI);
            searchListFragment.setArguments(data);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    searchListFragment, R.id.contentFrame);
        }

        // Create the presenter
        new SearchListPresenter(searchListFragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
