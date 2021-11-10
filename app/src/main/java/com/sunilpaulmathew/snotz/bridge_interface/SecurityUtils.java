package com.sunilpaulmathew.snotz.bridge_interface;

import android.app.Activity;
import android.content.Context;

import com.sunilpaulmathew.snotz.adapters.SettingsAdapter;
import com.sunilpaulmathew.snotz.utils.Utils;

import java.io.File;

public interface SecurityUtils {
    void setPIN(boolean verify, String title, SettingsAdapter adapter, Activity activity);

    void authenticate(SettingsAdapter adapter, int position, Activity activity);

    boolean isPINEnabled(Context context);

    String getPIN(Context context);

    void removePIN(Context context);

    boolean isBiometricEnabled(Context context);

    void setPIN(String pin, Context context);
}
