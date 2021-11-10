package com.sunilpaulmathew.snotz.utils;

import android.graphics.Bitmap;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.sunilpaulmathew.snotz.bridge_implementation.DefaultCommonUtils;
import com.sunilpaulmathew.snotz.bridge_interface.CommonUtils;


/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 01, 2021
 */
public class Common {

    private static Bitmap mReadModeImage = null;
    private static boolean mClearNote = false, mHiddenNotes = false, mWorking = false;
    private static int mColorText = -1;
    private static RecyclerView mRecyclerView;
    private static String mReadModeText = null, mSearchText = null;


    private static final CommonUtils u = new DefaultCommonUtils();

    public static Bitmap getReadModeImage() {
        return mReadModeImage;
    }

    public static boolean isClearingNotes() {
        return mClearNote;
    }

    public static boolean isHiddenNote() {
        return mHiddenNotes;
    }

    public static boolean isTextMatched(String note) {
        for (int a = 0; a < note.length() - mSearchText.length() + 1; a++) {
            if (mSearchText.equalsIgnoreCase(note.substring(a, a + mSearchText.length()))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isReloading() {
        return u.isReloading();
    }

    public static boolean isWorking() {
        return mWorking;
    }

    public static int getBackgroundColor() {
        return u.getColorBackground();
    }

    public static int getID() {
        return u.getID();
    }

    public static int getTextColor() {
        return mColorText;
    }

    public static RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public static RecyclerView initializeRecyclerView(int id, View view) {
        return mRecyclerView = view.findViewById(id);
    }

    public static String getAdjustedTime(double year, double month, double day, int hour, int min) {
        return u.getAdjustedTime(year, month, day, hour, min);
    }

    public static String getImageString() {
        return u.getImageString();
    }

    public static String getNote() {
        return u.getNote();
    }

    public static String getReadModeText() {
        return mReadModeText;
    }

    public static String getSearchText() {
        return mSearchText;
    }

    public static void isClearingNotes(boolean b) {
        mClearNote = b;
    }

    public static void isHiddenNote(boolean b) {
        mHiddenNotes = b;
    }

    public static void isReloading(boolean b) {
        u.isReloading(b);
    }

    public static void isWorking(boolean b) {
        mWorking = b;
    }

    public static void setBackgroundColor(int color) {
        u.setColorBackground(color);
    }

    public static void setID(int id) {
        u.setID(id);
    }

    public static void setImageString(String imageString) {
        u.setImageString(imageString);
    }

    public static void setNote(String note) {
        u.setNote(note);
    }

    public static void setReadModeImage(Bitmap bitmap) {
        mReadModeImage = bitmap;
    }

    public static void setReadModeText(String readModeText) {
        mReadModeText = readModeText;
    }

    public static void setSearchText(String searchText) {
        mSearchText = searchText;
    }

    public static void setTextColor(int color) {
        mColorText = color;
    }

}