package com.sunilpaulmathew.snotz.utils;

import android.content.Context;

import com.sunilpaulmathew.snotz.R;
import com.sunilpaulmathew.snotz.bridge_implementation.DefaultAccentColorUtils;
import com.sunilpaulmathew.snotz.bridge_interface.AccentColorUtils;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 01, 2021
 */
public class sNotzColor {
    private static final AccentColorUtils acu = new DefaultAccentColorUtils();

    public static int getAccentColor(Context context) {
        return acu.getAccentColor(context);
    }

    public static int getTextColor(Context context) {
        return acu.getTextColor(context);
    }

}