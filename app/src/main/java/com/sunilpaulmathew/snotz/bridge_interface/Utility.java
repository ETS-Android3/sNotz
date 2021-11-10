package com.sunilpaulmathew.snotz.bridge_interface;

import android.content.Context;

public interface Utility {
    boolean isDarkTheme(Context context);
    String getString(String name, String defaults, Context context);
    void saveString(String name, String value, Context context);
    void saveInt(String name, int value, Context context);
    int getInt(String name, int defaults, Context context);
}
