package com.sunilpaulmathew.snotz.bridge_interface;

import android.content.Context;

import com.sunilpaulmathew.snotz.utils.sNotzItems;

import org.json.JSONArray;

import java.util.List;

public interface DataUtils {
    List<sNotzItems> getData(Context context);
    JSONArray getsNotzItems(String json);
    boolean isHidden(String string);
    int getBackgroundColor(String string, Context context);
    String getNote(String string);
    long getDate(String string);
    String getImage(String string);
    int getTextColor(String string, Context context);
}
