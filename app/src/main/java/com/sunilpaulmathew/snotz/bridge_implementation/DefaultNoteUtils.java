package com.sunilpaulmathew.snotz.bridge_implementation;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.text.Editable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ProgressBar;

import com.sunilpaulmathew.snotz.bridge_interface.NoteUtils;
import com.sunilpaulmathew.snotz.utils.AsyncTasks;
import com.sunilpaulmathew.snotz.utils.Common;
import com.sunilpaulmathew.snotz.utils.Utils;
import com.sunilpaulmathew.snotz.utils.sNotzData;
import com.sunilpaulmathew.snotz.utils.sNotzItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DefaultNoteUtils implements NoteUtils {
    private static int i;

    private static JSONObject mJSONObject;
    private static JSONArray mJSONArray;

    public int getMaxSize(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        if (Utils.getOrientation(activity) == Configuration.ORIENTATION_PORTRAIT) {
            return displayMetrics.widthPixels / 3;
        } else {
            return displayMetrics.heightPixels / 3;
        }
    }

    public boolean validBackup(String backupData) {
        return sNotzData.getsNotzItems(backupData) != null;
    }

    public void reOrganizeNotes(Context context) {
        mJSONObject = new JSONObject();
        mJSONArray = new JSONArray();
        i = 0;
        try {
            for (sNotzItems items : sNotzData.getRawData(context)) {
                JSONObject note = new JSONObject();
                note.put("note", items.getNote());
                note.put("date", items.getTimeStamp());
                note.put("image", items.getImageString());
                note.put("hidden", items.isHidden());
                note.put("colorBackground", items.getColorBackground());
                note.put("colorText", items.getColorText());
                note.put("noteID", i);
                i++;
                mJSONArray.put(note);
            }
            mJSONObject.put("sNotz", mJSONArray);
            Utils.create(mJSONObject.toString(), context.getFilesDir().getPath() + "/snotz");
        } catch (JSONException ignored) {
        }
    }

    public AsyncTasks hideNote(int noteID, boolean hidden, ProgressBar progressBar, Context context) {
        return new AsyncTasks() {
            @Override
            public void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
                Common.isWorking(true);
            }

            @Override
            public void doInBackground() {
                try {
                    mJSONObject = new JSONObject(Objects.requireNonNull(Utils.read(context.getFilesDir().getPath() + "/snotz")));
                    mJSONArray = mJSONObject.getJSONArray("sNotz");
                    JSONObject note = new JSONObject(mJSONArray.getJSONObject(noteID).toString());
                    note.put("hidden", hidden);
                    mJSONArray.remove(noteID);
                    mJSONArray.put(note);
                    Utils.create(mJSONObject.toString(), context.getFilesDir().getPath() + "/snotz");
                } catch (JSONException ignored) {
                }
            }

            @Override
            public void onPostExecute() {
                Utils.reloadUI(context);
                Common.isWorking(false);
                progressBar.setVisibility(View.GONE);
            }
        };
    }

    public AsyncTasks deleteNote(int noteID, ProgressBar progressBar, Context context) {
        return new AsyncTasks() {
            @Override
            public void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
                Common.isWorking(true);
            }

            @Override
            public void doInBackground() {
                try {
                    mJSONObject = new JSONObject(Objects.requireNonNull(Utils.read(context.getFilesDir().getPath() + "/snotz")));
                    mJSONArray = mJSONObject.getJSONArray("sNotz");
                    mJSONArray.remove(noteID);
                    Utils.create(mJSONObject.toString(), context.getFilesDir().getPath() + "/snotz");
                } catch (JSONException ignored) {
                }
            }

            @Override
            public void onPostExecute() {
                Utils.reloadUI(context);
                Common.isWorking(false);
                progressBar.setVisibility(View.GONE);
            }
        };
    }

    public AsyncTasks initializeNotes(Editable newNote, String image, int colorBg, int colorTxt,
                                             boolean hidden, ProgressBar progressBar, Context context) {
        return new AsyncTasks() {
            @Override
            public void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
                Common.isWorking(true);
                mJSONObject = new JSONObject();
                mJSONArray = new JSONArray();
            }

            @Override
            public void doInBackground() {
                try {
                    JSONObject note = new JSONObject();
                    note.put("note", newNote);
                    note.put("date", System.currentTimeMillis());
                    note.put("image", image);
                    note.put("hidden", hidden);
                    note.put("colorBackground", colorBg);
                    note.put("colorText", colorTxt);
                    note.put("noteID", 0);
                    mJSONArray.put(note);
                    mJSONObject.put("sNotz", mJSONArray);
                    Utils.create(mJSONObject.toString(), context.getFilesDir().getPath() + "/snotz");
                } catch (JSONException ignored) {
                }
            }

            @Override
            public void onPostExecute() {
                Utils.reloadUI(context);
                Common.isWorking(false);
                progressBar.setVisibility(View.GONE);
            }
        };
    }

    public AsyncTasks addNote(Editable newNote, String image, int colorBg, int colorTxt,
                                     boolean hidden, ProgressBar progressBar, Context context) {
        return new AsyncTasks() {
            @Override
            public void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
                Common.isWorking(true);
            }

            @Override
            public void doInBackground() {
                try {
                    mJSONObject = new JSONObject(Objects.requireNonNull(Utils.read(context.getFilesDir().getPath() + "/snotz")));
                    mJSONArray = mJSONObject.getJSONArray("sNotz");
                    JSONObject note = new JSONObject();
                    note.put("note", newNote);
                    note.put("date", System.currentTimeMillis());
                    note.put("image", image);
                    note.put("hidden", hidden);
                    note.put("colorBackground", colorBg);
                    note.put("colorText", colorTxt);
                    note.put("noteID", sNotzData.getData(context).size());
                    mJSONArray.put(note);
                    Utils.create(mJSONObject.toString(), context.getFilesDir().getPath() + "/snotz");
                } catch (JSONException ignored) {
                }
            }

            @Override
            public void onPostExecute() {
                Utils.reloadUI(context);
                Common.isWorking(false);
                progressBar.setVisibility(View.GONE);
            }
        };
    }


    public AsyncTasks restoreNotes(String backupData, ProgressBar progressBar, Context context) {
        return new AsyncTasks() {
            @Override
            public void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
                Common.isWorking(true);
                mJSONObject = new JSONObject();
                mJSONArray = new JSONArray();
                i = 0;
            }

            @Override
            public void doInBackground() {
                try {
                    if (Utils.exist(context.getFilesDir().getPath() + "/snotz")) {
                        for (sNotzItems items : sNotzData.getRawData(context)) {
                            JSONObject note = new JSONObject();
                            note.put("note", items.getNote());
                            note.put("date", items.getTimeStamp());
                            note.put("image", items.getImageString());
                            note.put("hidden", items.isHidden());
                            note.put("colorBackground", items.getColorBackground());
                            note.put("colorText", items.getColorText());
                            note.put("noteID", i);
                            i++;
                            mJSONArray.put(note);
                        }
                    }
                    if (validBackup(backupData)) {
                        for (sNotzItems items : getNotesFromBackup(backupData, context)) {
                            JSONObject note = new JSONObject();
                            note.put("note", items.getNote());
                            note.put("date", items.getTimeStamp());
                            note.put("image", items.getImageString());
                            note.put("hidden", items.isHidden());
                            note.put("colorBackground", items.getColorBackground());
                            note.put("colorText", items.getColorText());
                            note.put("noteID", i);
                            i++;
                            mJSONArray.put(note);
                        }
                    }
                    mJSONObject.put("sNotz", mJSONArray);
                    Utils.create(mJSONObject.toString(), context.getFilesDir().getPath() + "/snotz");
                } catch (JSONException ignored) {
                }
            }

            @Override
            public void onPostExecute() {
                Utils.reloadUI(context);
                Common.isWorking(false);
                progressBar.setVisibility(View.GONE);
            }
        };
    }

    public List<sNotzItems> getNotesFromBackup(String backupData, Context context) {
        List<sNotzItems> mRestoreData = new ArrayList<>();
        for (int i = 0; i < sNotzData.getsNotzItems(backupData).length(); i++) {
            try {
                JSONObject command = Objects.requireNonNull(sNotzData.getsNotzItems(backupData)).getJSONObject(i);
                mRestoreData.add(new sNotzItems(sNotzData.getNote(command.toString()), sNotzData.getDate(command.toString()), sNotzData.getImage(command.toString()), sNotzData.isHidden(command.toString()),
                        sNotzData.getBackgroundColor(command.toString(), context), sNotzData.getTextColor(command.toString(), context), i));
            } catch (JSONException ignored) {
            }
        }
        return mRestoreData;
    }

    public AsyncTasks updateNote(Editable newNote, String image, int noteID, int colorBg, int colorTxt,
                                        boolean hidden, ProgressBar progressBar, Context context) {
        return new AsyncTasks() {
            @Override
            public void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
                Common.isWorking(true);
            }

            @Override
            public void doInBackground() {
                try {
                    mJSONObject = new JSONObject(Objects.requireNonNull(Utils.read(context.getFilesDir().getPath() + "/snotz")));
                    mJSONArray = mJSONObject.getJSONArray("sNotz");
                    JSONObject note = new JSONObject(mJSONArray.getJSONObject(noteID).toString());
                    note.put("note", newNote);
                    note.put("date", System.currentTimeMillis());
                    note.put("image", image);
                    note.put("hidden", hidden);
                    note.put("colorBackground", colorBg);
                    note.put("colorText", colorTxt);
                    note.put("noteID", noteID);
                    mJSONArray.remove(noteID);
                    mJSONArray.put(note);
                    Utils.create(mJSONObject.toString(), context.getFilesDir().getPath() + "/snotz");
                } catch (JSONException ignored) {
                }
            }

            @Override
            public void onPostExecute() {
                Utils.reloadUI(context);
                Common.isWorking(false);
                progressBar.setVisibility(View.GONE);
            }


        };
    }
}
