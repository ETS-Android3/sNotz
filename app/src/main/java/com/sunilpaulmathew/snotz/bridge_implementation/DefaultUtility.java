package com.sunilpaulmathew.snotz.bridge_implementation;

import android.content.Context;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

import com.sunilpaulmathew.snotz.bridge_interface.Utility;

public class DefaultUtility implements Utility {
    public boolean isDarkTheme(Context context) {
        int currentNightMode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES;
    }

    public String getString(String name, String defaults, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(name, defaults);
    }

    public void saveString(String name, String value, Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(name, value).apply();
    }

    public int getInt(String name, int defaults, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(name, defaults);
    }

    public void saveInt(String name, int value, Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(name, value).apply();
    }
}
