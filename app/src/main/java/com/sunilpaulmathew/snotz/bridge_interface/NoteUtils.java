package com.sunilpaulmathew.snotz.bridge_interface;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.widget.ProgressBar;

import com.sunilpaulmathew.snotz.utils.AsyncTasks;
import com.sunilpaulmathew.snotz.utils.sNotzData;
import com.sunilpaulmathew.snotz.utils.sNotzItems;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public interface NoteUtils {
    int getMaxSize(Activity activity);
    AsyncTasks deleteNote(int noteID, ProgressBar progressBar, Context context);
    AsyncTasks hideNote(int noteID, boolean hidden, ProgressBar progressBar, Context context);
    AsyncTasks restoreNotes(String backupData, ProgressBar progressBar, Context context);
    void reOrganizeNotes(Context context);
    AsyncTasks addNote(Editable newNote, String image, int colorBg, int colorTxt,
                       boolean hidden, ProgressBar progressBar, Context context);
    AsyncTasks initializeNotes(Editable newNote, String image, int colorBg, int colorTxt,
                              boolean hidden, ProgressBar progressBar, Context context);
    AsyncTasks updateNote(Editable newNote, String image, int noteID, int colorBg, int colorTxt,
               boolean hidden, ProgressBar progressBar, Context context);
    boolean validBackup(String backupData);
    List<sNotzItems> getNotesFromBackup(String backupData, Context context);
}
