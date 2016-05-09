package com.hmwg.main.dial;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hmwg.base.BaseFragment;
import com.hmwg.control.LuckyPanView;
import com.hmwg.eric.R;
import com.hmwg.main.ordergoods.OrderGoodsActivity;
import com.hmwg.utils.IntentUtils;
import com.hmwg.utils.T;
import com.hmwg.utils.ValidationUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by eric_qiantw on 16/4/20.
 */
public class DialFragment extends BaseFragment implements DialContract.View {

    @Bind(R.id.id_luckypan)
    LuckyPanView mLuckyPanView;
    @Bind(R.id.id_start_btn)
    ImageView mStartBtn;

    private DialContract.Presenter mPresenter;

    private int ramdon = 0;
    private int block = 0;

    public DialFragment(){
        new DialPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dial_frg, container, false);
        ButterKnife.bind(this, view);

        // 让Fragment中的onCreateOptionsMenu生效必须先调用setHasOptionsMenu方法
        setHasOptionsMenu(true);
        // 在 Activity 重绘时，Fragment 不会被重复绘制，也就是它会被“保留”
        setRetainInstance(true);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initAction();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public static DialFragment newInstance() {
        return new DialFragment();
    }

    private void initAction(){
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mLuckyPanView.isStart()) {

                    mStartBtn.setImageResource(R.drawable.stop);
                    ramdon = (int) (Math.random() * 1000);
                    if (ramdon == 0) {
                        block = 0;
                    } else if (ramdon > 0 && ramdon <= 100) {
                        block = 1;
                    } else if (ramdon > 100 && ramdon <= 200) {
                        block = 2;
                    } else if (ramdon > 200 && ramdon <= 300) {
                        block = 3;
                    } else if (ramdon > 300 && ramdon <= 400) {
                        block = 4;
                    } else {
                        block = 5;
                    }
                    mLuckyPanView.luckyStart(block);
                } else {
                    if (!mLuckyPanView.isShouldEnd()) {
                        mStartBtn.setImageResource(R.drawable.start);
                        mLuckyPanView.luckyEnd();
                    }
                }
            }
        });

        mLuckyPanView.setOnPanStoppedListening(new LuckyPanView.OnPanStoppedListening() {
            @Override
            public void PanStoppedListening() {
                mPresenter.dialTask();
            }
        });
    }

    @Override
    public void setPresenter(@NonNull DialContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void dialSuccess() {

    }

    @Override
    public void dialFaild() {

    }

}
