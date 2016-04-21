package com.yzyh.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yzyh.base.BaseActivity;
import com.yzyh.eric.R;
import com.yzyh.utils.IntentUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class WelcomeActivity extends BaseActivity {

    @Bind(R.id.welcome_tv_content)
    TextView welcome_tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        initAction();
    }

    private void initAction() {
        welcome_tv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startActivity(mContext,MainActivity.class);
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


}
