package com.sunilpaulmathew.snotz.bridge_implementation;

import android.app.Activity;
import android.content.Context;
import android.text.InputType;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.sunilpaulmathew.snotz.R;
import com.sunilpaulmathew.snotz.adapters.SettingsAdapter;
import com.sunilpaulmathew.snotz.bridge_interface.SecurityUtils;
import com.sunilpaulmathew.snotz.utils.Utils;

import java.io.File;

public class DefaultSecurityUtils implements SecurityUtils {
    public boolean isBiometricEnabled(Context context) {
        return Utils.getBoolean("use_biometric", false, context);
    }

    public String getPIN(Context context) {
        return Utils.read(new File(context.getCacheDir(),"pin").getAbsolutePath());
    }

    public void removePIN(Context context) {
        Utils.delete(new File(context.getCacheDir(),"pin").getAbsolutePath());
        Utils.saveBoolean("use_pin", false, context);
    }

    public void setPIN(String pin, Context context) {
        Utils.create(pin, new File(context.getCacheDir(),"pin").getAbsolutePath());
    }


    public void setPIN(boolean verify, String title, SettingsAdapter adapter, Activity activity) {
        Utils.dialogEditText(null, title,
                (dialogInterface, i) -> {
                }, text -> {
                    if (text.length() != 4) {
                        if (verify) {
                            removePIN(activity);
                        }
                        Utils.showSnackbar(activity.findViewById(android.R.id.content), activity.getString(R.string.pin_length_warning));
                    } else if (!verify) {
                        setPIN(text, activity);
                        setPIN(true, activity.getString(R.string.pin_reenter), adapter, activity);
                    } else if (!text.equals(getPIN(activity))) {
                        new MaterialAlertDialogBuilder(activity)
                                .setMessage(activity.getString(R.string.pin_mismatch_message))
                                .setCancelable(false)
                                .setNegativeButton(R.string.cancel, (dialog, which) -> removePIN(activity))
                                .setPositiveButton(R.string.try_again, (dialog, which) -> setPIN(true,
                                        activity.getString(R.string.pin_reenter), adapter, activity)).show();
                    } else {
                        Utils.saveBoolean("use_pin", true, activity);
                        Utils.showSnackbar(activity.findViewById(android.R.id.content), activity.getString(R.string.pin_protection_status,
                                activity.getString(R.string.activated)));
                        adapter.notifyItemChanged(2);
                    }
                }, InputType.TYPE_CLASS_NUMBER,activity).setOnDismissListener(dialogInterface -> {
        }).show();
    }

    public void authenticate(SettingsAdapter adapter, int position, Activity activity) {
        Utils.dialogEditText(null, activity.getString(R.string.authenticate),
                (dialogInterface, i) -> {
                }, text -> {
                    if (!text.equals(getPIN(activity))) {
                        new MaterialAlertDialogBuilder(activity)
                                .setMessage(activity.getString(R.string.pin_mismatch_message))
                                .setCancelable(false)
                                .setNegativeButton(R.string.cancel, (dialog, which) -> activity.finish())
                                .setPositiveButton(R.string.try_again, (dialog, which) -> authenticate(adapter, position, activity)).show();
                    } else {
                        if (position == 3) {
                            Utils.saveBoolean("hidden_note", !Utils.getBoolean("hidden_note", false, activity), activity);
                        } else {
                            Utils.delete(activity.getFilesDir().getPath() + "/snotz");
                        }
                        Utils.reloadUI(activity);
                        if (adapter != null) {
                            adapter.notifyItemChanged(position);
                        }
                    }
                }, InputType.TYPE_CLASS_NUMBER,activity).setOnDismissListener(dialogInterface -> {
        }).show();
    }

    public boolean isPINEnabled(Context context) {
        return Utils.exist(new File(context.getCacheDir(),"pin").getAbsolutePath()) && Utils
                .getBoolean("use_pin", false, context);
    }
}
