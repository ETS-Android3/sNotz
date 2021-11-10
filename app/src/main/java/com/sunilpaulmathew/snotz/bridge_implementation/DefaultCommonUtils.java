package com.sunilpaulmathew.snotz.bridge_implementation;

import com.sunilpaulmathew.snotz.bridge_interface.CommonUtils;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class DefaultCommonUtils implements CommonUtils {
    private String mNote;

    public String getNote() {
        return mNote;
    }

    private String imageString;

    @Override
    public String getImageString() {
        return imageString;
    }

    @Override
    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public void setNote(String mNote) {
        this.mNote = mNote;
    }

    public int getColorBackground() {
        return colorBackground;
    }

    public void setColorBackground(int colorBackground) {
        this.colorBackground = colorBackground;
    }

    private int colorBackground;

    public String getAdjustedTime(double year, double month, double day, int hour, int min) {
        DateFormatSymbols dfs = new DateFormatSymbols(Locale.getDefault());
        List<String> months = new ArrayList<>();
        Collections.addAll(months, dfs.getMonths());
        String mMonth = months.get((int) month) + " ";
        String mTime;
        if (hour > 12) {
            mTime =  (hour - 12) + ":" + (min < 10 ? "0" + min : min) + " PM";
        } else {
            mTime = hour + ":" + (min < 10 ? "0" + min : min) + " AM";
        }
        return mMonth + " " + (int)day + ", " + (int)year + " " + mTime;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    private int id;

    boolean mReload;

    public void isReloading(boolean b) {
        mReload = b;
    }
    public boolean isReloading() {
        return mReload;
    }
}
