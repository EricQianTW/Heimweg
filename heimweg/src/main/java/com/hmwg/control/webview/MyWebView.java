package com.hmwg.control.webview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

public class MyWebView extends WebView {
	public MyWebView(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.requestDisallowInterceptTouchEvent(false);
		this.getSettings().setJavaScriptEnabled(true);
        this.setWebViewClient(new WebViewClientUtil(context,this));
	}

	public MyWebView(Context context) {
		super(context);

		this.requestDisallowInterceptTouchEvent(false);
		this.getSettings().setJavaScriptEnabled(true);
		this.setWebViewClient(new WebViewClientUtil(context,this));
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		requestDisallowInterceptTouchEvent(false);
		return super.onTouchEvent(event);
	}

}
