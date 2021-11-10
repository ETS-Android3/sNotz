package com.sunilpaulmathew.snotz.bridge_implementation;

import android.content.Context;

import com.sunilpaulmathew.snotz.R;
import com.sunilpaulmathew.snotz.bridge_interface.AccentColorUtils;
import com.sunilpaulmathew.snotz.utils.Utils;
import com.sunilpaulmathew.snotz.utils.sNotzUtils;

public class DefaultAccentColorUtils implements AccentColorUtils {
    public int getAccentColor(Context context) {
        return Utils.getInt("accent_color", sNotzUtils.getColor(R.color.color_teal, context), context);
    }

    public int getTextColor(Context context) {
        return Utils.getInt("text_color", sNotzUtils.getColor(R.color.color_white, context), context);
    }
}
