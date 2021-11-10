package com.sunilpaulmathew.snotz.utils;


import android.app.Activity;

import android.content.Context;

import com.sunilpaulmathew.snotz.bridge_implementation.DefaultCheckListsUtils;
import com.sunilpaulmathew.snotz.bridge_interface.CheckListsUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 10, 2021
 */
public class CheckLists implements Serializable {

    private static String mCheckListName;
    private static final CheckListsUtils clu = new DefaultCheckListsUtils();

    private static boolean isDone(String string) {
        return clu.isDone(string);
    }

    public static boolean isValidCheckList(String checkListString) {
        return getChecklists(checkListString) != null;
    }

    public static JSONArray getChecklists(String checkListString) {
        return clu.getChecklists(checkListString);
    }

    public static List<CheckListItems> getData(Context context) {
        List<CheckListItems> mSavedData = new ArrayList<>();
        for (int i = 0; i < Objects.requireNonNull(getChecklists(Utils.read(context.getExternalFilesDir("checklists") + "/" + getCheckListName()))).length(); i++) {
            try {
                JSONObject command = Objects.requireNonNull(getChecklists(Utils.read(context.getExternalFilesDir("checklists") + "/" + getCheckListName()))).getJSONObject(i);
                mSavedData.add(new CheckListItems(getTitle(command.toString()), isDone(command.toString())));
            } catch (JSONException ignored) {
            }
        }
        return mSavedData;
    }

    public static String getCheckListName() {
        return mCheckListName;
    }

    private static String getTitle(String string) {
        try {
            JSONObject obj = new JSONObject(string);
            return obj.getString("title");
        } catch (JSONException ignored) {
        }
        return null;
    }

    public static void backupCheckList(Activity activity) {
        clu.backupCheckList(activity);
    }

    public static void setCheckListName(String name) {
        mCheckListName = name;
    }

}