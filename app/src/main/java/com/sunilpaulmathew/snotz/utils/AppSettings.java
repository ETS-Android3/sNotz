package com.sunilpaulmathew.snotz.utils;

import android.content.Context;

import com.sunilpaulmathew.snotz.adapters.SettingsAdapter;
import com.sunilpaulmathew.snotz.bridge_implementation.DefaultFontSetting;
import com.sunilpaulmathew.snotz.bridge_interface.FontSetting;

import java.util.List;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 14, 2021
 */
public class AppSettings {
    private static final FontSetting fs = new DefaultFontSetting();

    public static int getStyle(Context context) {
        return fs.getStyle(context);
    }

    public static String getFontStyle(Context context) {
        return fs.getFontStyle(context);
    }

    public static void setFontSize(int position, List<SettingsItems> items, SettingsAdapter adapter, Context context) {
        fs.setFontSize(position, items, adapter, context);
    }

    public static void setFontStyle(int position, List<SettingsItems> items, SettingsAdapter adapter, Context context) {
        fs.setFontStyle(position, items, adapter, context);
    }

}