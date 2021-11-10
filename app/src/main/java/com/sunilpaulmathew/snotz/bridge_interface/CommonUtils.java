package com.sunilpaulmathew.snotz.bridge_interface;

import android.content.Context;

public interface CommonUtils {
    void isReloading(boolean b);
    boolean isReloading();
    String getNote();
    void setNote(String s);
    int getColorBackground();
    void setColorBackground(int s);
    int getID();
    void setID(int id);
    String getAdjustedTime(double year, double month, double day, int hour, int min);
    String getImageString();
    void setImageString(String s);
}
