package com.sunilpaulmathew.snotz.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Editable;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ProgressBar;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.sunilpaulmathew.snotz.BuildConfig;
import com.sunilpaulmathew.snotz.R;
import com.sunilpaulmathew.snotz.bridge_implementation.DefaultNoteUtils;
import com.sunilpaulmathew.snotz.bridge_interface.NoteUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 01, 2021
 */
public class sNotzUtils {

    private static final NoteUtils nu = new DefaultNoteUtils();

    public static List<sNotzItems> getNotesFromBackup(String backupData, Context context) {
        return nu.getNotesFromBackup(backupData, context);
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
        return nu.validBackup(backupData);
    }
        
    public static int getMaxSize(Activity activity) {
        return nu.getMaxSize(activity);
    }

    public static int getColor(int color, Context context) {
        return ContextCompat.getColor(context, color);
    }

    public static Drawable getDrawable(int drawable, Context context) {
        return ContextCompat.getDrawable(context, drawable);
    }

    public static Drawable getColoredDrawable(int color, int drawable, Context context) {
        Drawable d = ContextCompat.getDrawable(context, drawable);
        if (d != null) {
            d.setTint(color);
        }
        return d;
    }

    public static String bitmapToBase64(Bitmap bitmap, Activity activity) {
        try {
            int size = getMaxSize(activity);
            float ratio = Math.min((float) size / bitmap.getWidth(), (float) size / bitmap.getHeight());
            int width = Math.round(ratio * bitmap.getWidth());
            int height = Math.round(ratio * bitmap.getHeight());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Bitmap.createScaledBitmap(bitmap, width, height, true).compress(Bitmap
                    .CompressFormat.PNG,100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        } catch (Exception ignored) {}
        return null;
    }

    public static Bitmap stringToBitmap(String string) {
        try {
            byte[] imageAsBytes = Base64.decode(string.getBytes(), Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        } catch (Exception ignored) {}
        return null;
    }

    private static void bitmapToPNG(Bitmap bitmap, File file) {
        try {
            OutputStream outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (IOException ignored) {}
    }

    public static void shareNote(String note, String imageString, Context context) {
        Intent share_note = new Intent();
        share_note.setAction(Intent.ACTION_SEND);
        share_note.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.shared_by, BuildConfig.VERSION_NAME));
        share_note.putExtra(Intent.EXTRA_TEXT, "\"" + note + "\"\n\n" +
                context.getString(R.string.shared_by_message, BuildConfig.VERSION_NAME));
        if (imageString != null) {
            new AsyncTasks() {
                private final File mImageFile = new File(context.getExternalCacheDir(), "photo.png");

                @Override
                public void onPreExecute() {
                    Common.isWorking(true);
                    if (Utils.exist(mImageFile.toString())) {
                        Utils.delete(mImageFile.toString());
                    }
                }

                @Override
                public void doInBackground() {
                    sNotzUtils.bitmapToPNG(Objects.requireNonNull(sNotzUtils.stringToBitmap(imageString)), mImageFile);
                    Uri uri = FileProvider.getUriForFile(context,BuildConfig.APPLICATION_ID + ".provider", mImageFile);
                    share_note.putExtra(Intent.EXTRA_STREAM, uri);
                    share_note.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }

                @Override
                public void onPostExecute() {
                    share_note.setType("image/png");
                    Intent shareIntent = Intent.createChooser(share_note, context.getString(R.string.share_with));
                    context.startActivity(shareIntent);
                    Common.isWorking(false);

                }
            }.execute();
        } else {
            share_note.setType("text/plain");
            Intent shareIntent = Intent.createChooser(share_note, context.getString(R.string.share_with));
            context.startActivity(shareIntent);
        }
    }

    public static AsyncTasks addNote(Editable newNote, String image, int colorBg, int colorTxt,
                               boolean hidden, ProgressBar progressBar, Context context) {
        return nu.addNote(newNote, image, colorBg, colorTxt, hidden, progressBar, context);
    }

    public static AsyncTasks deleteNote(int noteID, ProgressBar progressBar, Context context) {
        return nu.deleteNote(noteID, progressBar, context);
    }

    public static AsyncTasks hideNote(int noteID, boolean hidden, ProgressBar progressBar, Context context) {
        return nu.hideNote(noteID, hidden, progressBar, context);
    }

    public static AsyncTasks initializeNotes(Editable newNote, String image, int colorBg, int colorTxt,
                                             boolean hidden, ProgressBar progressBar, Context context) {
        return nu.initializeNotes(newNote, image, colorBg, colorTxt, hidden, progressBar, context);
    }

    public static AsyncTasks restoreNotes(String backupData, ProgressBar progressBar, Context context) {
        return nu.restoreNotes(backupData, progressBar, context);
    }

    public static AsyncTasks updateNote(Editable newNote, String image, int noteID, int colorBg, int colorTxt,
                                        boolean hidden, ProgressBar progressBar, Context context) {
        return nu.updateNote(newNote, image, noteID, colorBg, colorTxt, hidden, progressBar, context);
    }

    public static void reOrganizeNotes(Context context) {
        nu.reOrganizeNotes(context);
    }

}