package com.hmwg.main.dial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hmwg.base.BaseFragment;
import com.hmwg.control.webview.MyWebView;
import com.hmwg.eric.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by eric_qiantw on 16/5/24.
 */
public class DialWvFragment extends BaseFragment {


    @Bind(R.id.goods_webview)
    MyWebView goodsWebview;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dial_frg_webview, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        goodsWebview.loadUrl("http://api.web.heimweg.com.cn/choujiang.html");
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public DialWvFragment() {

    }

    public static DialWvFragment newInstance() {
        return new DialWvFragment();
    }

}
