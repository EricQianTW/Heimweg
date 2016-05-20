package com.hmwg.control.DateTimePicker;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Pair;

import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.hmwg.utils.T;

/**
 * Created by eric_qiantw on 16/5/20.
 */
public class DateTimePickerUtils {

    public static void openDatePop(Context context, FragmentManager manager,Pair<Boolean, SublimeOptions> optionsPair, SublimePickerFragment.Callback mFragmentCallback) {
        // DialogFragment to host SublimePicker
        SublimePickerFragment pickerFrag = new SublimePickerFragment();
        pickerFrag.setCallback(mFragmentCallback);

        if (!optionsPair.first) { // If options are not valid
            T.showShort(context, "No pickers activated");
            return;
        }

        // Valid options
        Bundle bundle = new Bundle();
        bundle.putParcelable("SUBLIME_OPTIONS", optionsPair.second);
        pickerFrag.setArguments(bundle);

        pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        pickerFrag.show(manager, "SUBLIME_PICKER");


    }

}
