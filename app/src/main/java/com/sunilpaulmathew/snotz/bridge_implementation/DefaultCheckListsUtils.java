package com.sunilpaulmathew.snotz.bridge_implementation;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.core.app.ActivityCompat;

import com.sunilpaulmathew.snotz.R;
import com.sunilpaulmathew.snotz.bridge_interface.CheckListsUtils;
import com.sunilpaulmathew.snotz.utils.CheckLists;
import com.sunilpaulmathew.snotz.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class DefaultCheckListsUtils implements CheckListsUtils {
    public boolean isDone(String string) {
        try {
            JSONObject obj = new JSONObject(string);
            return obj.getBoolean("done");
        } catch (JSONException ignored) {
        }
        return false;
    }

    public JSONArray getChecklists(String checkListString) {
        try {
            JSONObject main = new JSONObject(Objects.requireNonNull(checkListString));
            return main.getJSONArray("checklist");
        } catch (JSONException ignored) {
        }
        return null;
    }

    public void backupCheckList(Activity activity) {
        if (Build.VERSION.SDK_INT < 30 && Utils.isPermissionDenied(activity)) {
            ActivityCompat.requestPermissions(activity, new String[] {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return;
        }
        Utils.dialogEditText(null, activity.getString(R.string.check_list_backup_question, CheckLists.getCheckListName()),
                (dialogInterface, i) -> {
                }, text -> {
                    if (text.isEmpty()) {
                        Utils.showSnackbar(activity.findViewById(android.R.id.content), activity.getString(R.string.text_empty));
                        return;
                    }
                    if (!text.endsWith(".checklist")) {
                        text += ".checklist";
                    }
                    if (Build.VERSION.SDK_INT >= 30) {
                        try {
                            ContentValues values = new ContentValues();
                            values.put(MediaStore.MediaColumns.DISPLAY_NAME, text);
                            values.put(MediaStore.MediaColumns.MIME_TYPE, "*/*");
                            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);
                            Uri uri = activity.getContentResolver().insert(MediaStore.Files.getContentUri("external"), values);
                            OutputStream outputStream = activity.getContentResolver().openOutputStream(uri);
                            outputStream.write(Objects.requireNonNull(Utils.read(activity.getExternalFilesDir("checklists") + "/" + CheckLists.getCheckListName())).getBytes());
                            outputStream.close();
                        } catch (IOException ignored) {
                        }
                    } else {
                        Utils.create(Utils.read(activity.getExternalFilesDir("checklists") + "/" + CheckLists.getCheckListName()), Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + text);
                    }
                    Utils.showSnackbar(activity.findViewById(android.R.id.content), activity.getString(R.string.backup_notes_message, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + text));
                }, -1, activity).setOnDismissListener(dialogInterface -> {
        }).show();
    }
}
