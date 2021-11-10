package com.sunilpaulmathew.snotz.bridge_interface;

import android.app.Activity;
import org.json.JSONArray;

public interface CheckListsUtils {
    void backupCheckList(Activity activity);
    JSONArray getChecklists(String checkListString);
    boolean isDone(String string);
}
