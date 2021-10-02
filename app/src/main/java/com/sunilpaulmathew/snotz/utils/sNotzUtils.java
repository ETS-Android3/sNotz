package com.sunilpaulmathew.snotz.utils;

import android.content.Context;
import android.text.Editable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 01, 2020
 */
public class sNotzUtils {

    private static JSONObject mJSONObject;
    private static JSONArray mJSONArray;

    private static List<sNotzItems> getNotesFromBackup(String backupData) {
        List<sNotzItems> mRestoreData = new ArrayList<>();
        for (int i = 0; i < sNotzData.getsNotzItems(backupData).length(); i++) {
            try {
                JSONObject command = Objects.requireNonNull(sNotzData.getsNotzItems(backupData)).getJSONObject(i);
                mRestoreData.add(new sNotzItems(sNotzData.getNote(command.toString()), sNotzData.getDate(command.toString()), sNotzData.isHidden(command.toString())));
            } catch (JSONException ignored) {
            }
        }
        return mRestoreData;
    }

    public static String sNotzToText(Context context) {
        StringBuilder sb = new StringBuilder();
        for (int note = 0; note < Objects.requireNonNull(sNotzData.getsNotzItems(Utils.read(context.getFilesDir().getPath() + "/snotz"))).length(); note++) {
            try {
                JSONObject command = Objects.requireNonNull(sNotzData.getsNotzItems(Utils.read(context.getFilesDir().getPath() + "/snotz"))).getJSONObject(note);
                sb.append(sNotzData.getNote(command.toString())).append("\n... ... ... ... ...\n\n");
            } catch (JSONException ignored) {
            }
        }
        return sb.toString();
    }

    public static boolean validBackup(String backupData) {
        return sNotzData.getsNotzItems(backupData) != null;
    }

    public static void addNote(Editable newNote, Context context) {
        mJSONObject = new JSONObject();
        mJSONArray = new JSONArray();
        try {
            for (sNotzItems items : sNotzData.getRawData(context)) {
                JSONObject note = new JSONObject();
                note.put("note", items.getNote());
                note.put("date", items.getTimeStamp());
                note.put("hidden", items.isHidden());
                mJSONArray.put(note);
            }
            JSONObject note = new JSONObject();
            note.put("note", newNote);
            note.put("date", DateFormat.getDateTimeInstance().format(System.currentTimeMillis()));
            note.put("hidden", false);
            mJSONArray.put(note);
            mJSONObject.put("sNotz", mJSONArray);
            Utils.create(mJSONObject.toString(), context.getFilesDir().getPath() + "/snotz");
        } catch (JSONException ignored) {
        }
    }

    public static void deleteNote(String noteToDelete, Context context) {
        mJSONObject = new JSONObject();
        mJSONArray = new JSONArray();
        try {
            for (sNotzItems items : sNotzData.getRawData(context)) {
                if (!items.getNote().equals(noteToDelete)) {
                    JSONObject note = new JSONObject();
                    note.put("note", items.getNote());
                    note.put("date", items.getTimeStamp());
                    note.put("hidden", items.isHidden());
                    mJSONArray.put(note);
                }
            }
            mJSONObject.put("sNotz", mJSONArray);
            Utils.create(mJSONObject.toString(), context.getFilesDir().getPath() + "/snotz");
        } catch (JSONException ignored) {
        }
    }

    public static void hideNote(String noteToHide, boolean hide, Context context) {
        mJSONObject = new JSONObject();
        mJSONArray = new JSONArray();
        try {
            for (sNotzItems items : sNotzData.getRawData(context)) {
                if (!items.getNote().equals(noteToHide)) {
                    JSONObject note = new JSONObject();
                    note.put("note", items.getNote());
                    note.put("date", items.getTimeStamp());
                    note.put("hidden", items.isHidden());
                    mJSONArray.put(note);
                } else {
                    JSONObject note = new JSONObject();
                    note.put("note", items.getNote());
                    note.put("date", items.getTimeStamp());
                    note.put("hidden", hide);
                    mJSONArray.put(note);
                }
            }
            mJSONObject.put("sNotz", mJSONArray);
            Utils.create(mJSONObject.toString(), context.getFilesDir().getPath() + "/snotz");
        } catch (JSONException ignored) {
        }
    }

    public static void initializeNotes(Editable newNote, Context context) {
        mJSONObject = new JSONObject();
        mJSONArray = new JSONArray();
        try {
            JSONObject note = new JSONObject();
            note.put("note", newNote);
            note.put("date", DateFormat.getDateTimeInstance().format(System.currentTimeMillis()));
            note.put("hidden", false);
            mJSONArray.put(note);
            mJSONObject.put("sNotz", mJSONArray);
            Utils.create(mJSONObject.toString(), context.getFilesDir().getPath() + "/snotz");
        } catch (JSONException ignored) {
        }
    }

    public static void restoreNotes(String backupData, Context context) {
        mJSONObject = new JSONObject();
        mJSONArray = new JSONArray();
        try {
            if (Utils.exist(context.getFilesDir().getPath() + "/snotz")) {
                for (sNotzItems items : sNotzData.getRawData(context)) {
                    JSONObject note = new JSONObject();
                    note.put("note", items.getNote());
                    note.put("date", items.getTimeStamp());
                    note.put("hidden", items.isHidden());
                    mJSONArray.put(note);
                }
            }
            if (validBackup(backupData)) {
                for (sNotzItems items : getNotesFromBackup(backupData)) {
                    JSONObject note = new JSONObject();
                    note.put("note", items.getNote());
                    note.put("date", items.getTimeStamp());
                    note.put("hidden", items.isHidden());
                    mJSONArray.put(note);
                }
            }
            mJSONObject.put("sNotz", mJSONArray);
            Utils.create(mJSONObject.toString(), context.getFilesDir().getPath() + "/snotz");
        } catch (JSONException ignored) {
        }
    }

    public static void updateNote(Editable newNote, String oldNote, Context context) {
        mJSONObject = new JSONObject();
        mJSONArray = new JSONArray();
        try {
            for (sNotzItems items : sNotzData.getRawData(context)) {
                JSONObject note = new JSONObject();
                if (items.getNote().equals(oldNote)) {
                    note.put("note", newNote);
                    note.put("date", DateFormat.getDateTimeInstance().format(System.currentTimeMillis()));
                } else {
                    note.put("note", items.getNote());
                    note.put("date", items.getTimeStamp());
                }
                note.put("hidden", items.isHidden());
                mJSONArray.put(note);
            }
            mJSONObject.put("sNotz", mJSONArray);
            Utils.create(mJSONObject.toString(), context.getFilesDir().getPath() + "/snotz");
        } catch (JSONException ignored) {
        }
    }

}