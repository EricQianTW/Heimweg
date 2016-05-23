package com.hmwg.control.webview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewClientUtil extends WebViewClient {
	private Context mContext;
	private MyWebView mWebView;
	
	public WebViewClientUtil(Context context,MyWebView webView) {
		mContext = context;
		mWebView = webView;
	}
	
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		view.loadUrl(url);
		return super.shouldOverrideUrlLoading(view, url);
	}

	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		super.onPageStarted(view, url, favicon);
	}

	@Override
	public void onPageFinished(WebView view, String url) {
		super.onPageFinished(view, url);
		mWebView.setVisibility(View.VISIBLE);
	}

}