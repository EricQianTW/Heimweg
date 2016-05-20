package com.hmwg.control.DateTimePicker;

import android.util.Pair;

import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;

/**
 * Created by eric_qiantw on 16/5/10.
 */
public class Tools {

    public static Pair<Boolean, SublimeOptions> getNormalOptions() {
        SublimeOptions options = new SublimeOptions();
        int displayOptions = 0;

        displayOptions |= SublimeOptions.ACTIVATE_DATE_PICKER;

        displayOptions |= SublimeOptions.ACTIVATE_TIME_PICKER;

        displayOptions |= SublimeOptions.ACTIVATE_RECURRENCE_PICKER;

        options.setPickerToShow(SublimeOptions.Picker.DATE_PICKER);

        options.setDisplayOptions(displayOptions);

        return new Pair<>(displayOptions != 0 ? Boolean.TRUE : Boolean.FALSE, options);
    }

    public static Pair<Boolean, SublimeOptions> getDateOptions() {
        SublimeOptions options = new SublimeOptions();
        int displayOptions = 0;

        displayOptions |= SublimeOptions.ACTIVATE_DATE_PICKER;

        options.setPickerToShow(SublimeOptions.Picker.DATE_PICKER);

        options.setDisplayOptions(displayOptions);

        options.setCanPickDateRange(false);

        return new Pair<>(displayOptions != 0 ? Boolean.TRUE : Boolean.FALSE, options);
    }

}
