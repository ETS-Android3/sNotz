package com.sunilpaulmathew.snotz.utils;

import android.content.Context;

import com.sunilpaulmathew.snotz.bridge_implementation.DefaultDataUtils;
import com.sunilpaulmathew.snotz.bridge_interface.DataUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 17, 2020
 */
public class sNotzData {

    private static final DataUtils du = new DefaultDataUtils();

    public static List<sNotzItems> getData(Context context) {
        return du.getData(context);
    }

    public static List<sNotzItems> getRawData(Context context) {
        List<sNotzItems> mData = new ArrayList<>();
        String json = context.getFilesDir().getPath() + "/snotz";
        for (int i = 0; i < Objects.requireNonNull(getsNotzItems(Utils.read(json))).length(); i++) {
            try {
                JSONObject command = Objects.requireNonNull(getsNotzItems(Utils.read(json))).getJSONObject(i);
                mData.add(new sNotzItems(getNote(command.toString()), getDate(command.toString()), getImage(command.toString()), isHidden(command.toString()),
                        getBackgroundColor(command.toString(), context), getTextColor(command.toString(), context), getNoteD(command.toString())));
            } catch (JSONException ignored) {
            }
        }
        return mData;
    }

    public static JSONArray getsNotzItems(String json) {
        return du.getsNotzItems(json );
    }

    public static String getNote(String string) {
        return du.getNote(string);
    }

    public static long getDate(String string) {
        return du.getDate(string);
    }

    public static String getImage(String string) {
        return du.getImage(string);
    }

    public static boolean isHidden(String string) {
        return du.isHidden(string);
    }

    public static int getBackgroundColor(String string, Context context) {
        return du.getBackgroundColor(string, context);
    }

    public static int getTextColor(String string, Context context) {
        return du.getTextColor(string, context);
    }

    public static int getNoteD(String string) {
        try {
            JSONObject obj = new JSONObject(string);
            return obj.getInt("noteID");
        } catch (JSONException ignored) {
        }
        return -1;
    }

}