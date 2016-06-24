package com.hmwg.control;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hmwg.eric.R;


public class DialogViewUtils {
	
	public static Dialog dialog;
	
	@SuppressWarnings("deprecation")
	public static void showNoneView(Context context,OnClickListener leftClickListener,OnClickListener righClickListener,String leftString,String rightString,String title) {
		dialog = new Dialog(context, R.style.MyDialogStyleTheme);
		// 设置它的ContentView
		dialog.setContentView(R.layout.common_prompt_noneview);
		TextView prompt_title = (TextView) dialog.findViewById(R.id.prompt_title);
		prompt_title.setText(title);
		if(dialog != null){
			dialog.show();
		}
		WindowManager windowManager = ((Activity) context).getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.width = display.getWidth(); // 设置宽度
		dialog.getWindow().setAttributes(lp);
		Window window = dialog.getWindow();
		Button cancel = (Button) window.findViewById(R.id.cancel);
		cancel.setText(leftString);
		cancel.setOnClickListener(leftClickListener);
		Button confirm = (Button) window.findViewById(R.id.confirm);
		confirm.setText(rightString);
		confirm.setOnClickListener(righClickListener);
	}

	public void showTvView(Context context,final OnDigBtnClickListening leftClickListener
			,final OnDigBtnClickListening righClickListener,String leftString,String rightString,String title,String tvStr) {
		dialog = new Dialog(context, R.style.MyDialogStyleTheme);
		// 设置它的ContentView
		dialog.setContentView(R.layout.common_prompt_textview);
		TextView prompt_title = (TextView) dialog.findViewById(R.id.prompt_title);
		final EditText common_et = (EditText) dialog.findViewById(R.id.common_et);
		TextView common_tv = (TextView) dialog.findViewById(R.id.common_tv);
		prompt_title.setText(title);
		common_tv.setText(tvStr);
		if(dialog != null){
			dialog.show();
		}
		WindowManager windowManager = ((Activity) context).getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.width = display.getWidth(); // 设置宽度
		dialog.getWindow().setAttributes(lp);
		Window window = dialog.getWindow();
		Button cancel = (Button) window.findViewById(R.id.cancel);
		cancel.setText(leftString);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				leftClickListener.DigBtnClickListening(common_et);
			}
		});
		Button confirm = (Button) window.findViewById(R.id.confirm);
		confirm.setText(rightString);
		confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				righClickListener.DigBtnClickListening(common_et);
			}
		});
	}

	public interface OnDigBtnClickListening{
		void DigBtnClickListening(TextView tv);
	}

	OnDigBtnClickListening mOnDigBtnClickListen=null;

	public void setOnDigBtnClickListen(OnDigBtnClickListening e){
		mOnDigBtnClickListen=e;
	}

	@SuppressWarnings("deprecation")
	public static void showTimePickerView(Context context,OnClickListener leftClickListener,OnClickListener righClickListener,String leftString,String rightString,String title) {

	}

	
}
