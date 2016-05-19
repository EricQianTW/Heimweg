package com.hmwg.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.hmwg.eric.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by eric on 16-2-22.
 */
public class ValidationUtils {
    private Context mContext;

    public ValidationUtils(Context context){
        mContext = context;
    }

    /**
     * 遍历布局，并禁用所有子控件
     *
     * @param viewGroup
     *            布局对象
     */
    public static void resetErrorControls(ViewGroup viewGroup) {
        String TAG = "resetErrorControls";
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View v = viewGroup.getChildAt(i);
            if (v instanceof EditText) {
                ((EditText) v).setError(null);
            } else if (v instanceof Button) {
                ((Button) v).setError(null);
            }
        }
    }

    /**
     * 控件非空Check
     */
    public boolean isEmpty(AutoCompleteTextView view,String message) {
        if(view != null){
            if(TextUtils.isEmpty(view.getText().toString())){
                view.setError(message);
                return true;
            }
        }
        return false;
    }

    /**
     * 控件非空Check
     */
    public boolean isEmpty(EditText view,String message) {
        if(view != null){
            if(TextUtils.isEmpty(view.getText().toString())){
                view.setError(message);
                return true;
            }
        }
        return false;
    }

    /**
     * 控件非空Check错误信息
     */
    public String isEmptyMessage(int resourceId){
        return mContext.getString(resourceId) + mContext.getString(R.string.error_isempty);
    }

    /**
     * 不是电话号码
     * @param view
     * @param message
     * @return
     */
    public boolean isNotMobileNumber(EditText view, String message) {
        Pattern p = Pattern.compile("^1\\d{10}$");
        Matcher m = p.matcher(view.getText().toString());
        if(!m.matches()){
            view.setError(message);
            return true;
        }
        return false;
    }

    /**
     * 控件不是电话号码错误信息
     */
    public String isNotMobileNumberMessage(int resourceId){
        return mContext.getString(resourceId) + mContext.getString(R.string.error_isnotmobile);
    }
}
