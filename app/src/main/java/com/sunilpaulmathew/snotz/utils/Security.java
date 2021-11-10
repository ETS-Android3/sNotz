package com.sunilpaulmathew.snotz.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.InputType;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.sunilpaulmathew.snotz.MainActivity;
import com.sunilpaulmathew.snotz.R;
import com.sunilpaulmathew.snotz.adapters.SettingsAdapter;
import com.sunilpaulmathew.snotz.bridge_implementation.DefaultSecurityUtils;
import com.sunilpaulmathew.snotz.bridge_interface.SecurityUtils;

import java.io.File;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 07, 2021
 */
public class Security {

    private static final SecurityUtils su = new DefaultSecurityUtils();

    public static boolean isBiometricEnabled(Context context) {
        return su.isBiometricEnabled(context);
    }

    public static boolean isHiddenNotesUnlocked(Context context) {
        return Utils.getBoolean("hidden_note",false, context);
    }

    public static boolean isPINEnabled(Context context) {
        return su.isPINEnabled(context);
    }

    public static boolean isScreenLocked(Context context) {
        return isBiometricEnabled(context) || isPINEnabled(context);
    }

    public static String getPIN(Context context) {
        return su.getPIN(context);
    }

    public static void removePIN(Context context) {
        su.removePIN(context);
    }

    public static void setPIN(String pin, Context context) {
       su.setPIN(pin, context);}

    public static void setPIN(boolean verify, String title, SettingsAdapter adapter, Activity activity) {
       su.setPIN(verify, title, adapter, activity);
    }

    public static void authenticate(SettingsAdapter adapter, int position, Activity activity) {
        su.authenticate(adapter, position, activity);
    }

    public static void authenticate(boolean remove, SettingsAdapter adapter, Activity activity) {
        Utils.dialogEditText(null, activity.getString(R.string.authenticate),
                (dialogInterface, i) -> {
                }, text -> {
                    if (!text.equals(getPIN(activity))) {
                        new MaterialAlertDialogBuilder(activity)
                                .setMessage(activity.getString(R.string.pin_mismatch_message))
                                .setCancelable(false)
                                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                                })
                                .setPositiveButton(R.string.try_again, (dialog, which) -> authenticate(remove, adapter, activity)).show();
                    } else {
                        if (remove) {
                            removePIN(activity);
                            adapter.notifyItemChanged(2);
                            Utils.showSnackbar(activity.findViewById(android.R.id.content), activity.getString(R.string.pin_protection_status,
                                    activity.getString(R.string.deactivated)));
                        } else {
                            // Launch MainActivity
                            Intent mainActivity = new Intent(activity, MainActivity.class);
                            activity.startActivity(mainActivity);
                            activity.finish();
                        }
                    }
                }, InputType.TYPE_CLASS_NUMBER,activity).setOnDismissListener(dialogInterface -> {
                    if (!remove) {
                        activity.finish();
                    }
        }).show();
    }

}