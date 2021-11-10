package com.sunilpaulmathew.snotz.bridge_interface;

import android.content.Context;

import com.sunilpaulmathew.snotz.adapters.SettingsAdapter;
import com.sunilpaulmathew.snotz.utils.SettingsItems;

import java.util.List;

public interface FontSetting {
    void setFontStyle(int position, List<SettingsItems> items, SettingsAdapter adapter, Context context);
    String[] getFontStyles(Context context);
    String getFontStyle(Context context);
    void setFontSize(int position, List<SettingsItems> items, SettingsAdapter adapter, Context context);
    int getStyle(Context context);
}
