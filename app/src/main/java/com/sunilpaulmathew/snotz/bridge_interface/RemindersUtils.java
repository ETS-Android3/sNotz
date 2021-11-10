package com.sunilpaulmathew.snotz.bridge_interface;

import android.content.Context;

import com.sunilpaulmathew.snotz.utils.ReminderItems;

import org.json.JSONArray;

import java.util.List;

public interface RemindersUtils {
    int getNotificationID(Context context);
    JSONArray getReminders(Context context);
    String getNote(String string);
    String getReminderMessage(Context context);
    List<ReminderItems> getRawData(Context context);
    void add(String noteToAdd, double year, double month, double day,
        int hour, int min, int id, Context context);
    void edit(String noteToEdit, double year, double month, double day,
              int hour, int min, int id, Context context);
    void delete(int id, Context context);
    void initialize(String note, double year, double month, double day, int hour,
               int min, int id, Context context);
    double getYear();

    double getMonth();

    double getDay();

    void setYear(double year);

    void setMonth(double month);

    void setDay(double day);

}
