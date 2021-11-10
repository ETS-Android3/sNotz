package com.sunilpaulmathew.snotz.utils;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.sunilpaulmathew.snotz.R;
import com.sunilpaulmathew.snotz.bridge_implementation.DefaultRemindersUtils;
import com.sunilpaulmathew.snotz.bridge_interface.RemindersUtils;
import com.sunilpaulmathew.snotz.receivers.ReminderReceiver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 02, 2021
 */
public class sNotzReminders {

    private static final RemindersUtils ru = new DefaultRemindersUtils();

    public static void add(String noteToAdd, double year, double month, double day,
                           int hour, int min, int id, Context context) {
        ru.add(noteToAdd, year, month, day, hour, min, id, context);
    }

    public static void edit(String noteToEdit, double year, double month, double day,
                            int hour, int min, int id, Context context) {
        ru.edit(noteToEdit, year, month, day, hour, min, id, context);
    }

    public static void delete(int id, Context context) {
        ru.delete(id, context);
    }

    public static void initialize(String note, double year, double month, double day, int hour,
                                  int min, int id, Context context) {
        ru.initialize(note, year, month, day, hour, min, id, context);
    }

    public static List<ReminderItems> getRawData(Context context) {
        return ru.getRawData(context);
    }

    public static JSONArray getReminders(Context context) {
        return ru.getReminders(context);
    }

    public static String getReminderMessage(Context context) {
        return ru.getReminderMessage(context);
    }



    public static int getNotificationID(Context context) {
        return ru.getNotificationID(context);
    }

    private static double getYear() {
        return ru.getYear();
    }

    private static double getMonth() {
        return ru.getMonth();
    }

    private static double getDay() {
        return ru.getDay();
    }

    public static void setYear(double year) {
        ru.setYear(year);
    }

    public static void setMonth(double month) {
        ru.setMonth(month);
    }

    public static void setDay(double day) {
        ru.setDay(day);
    }

    public static void setReminder(double year, double month, double day, int hour, int min, int id,
                                   String note, Context context) {
        AlarmManager mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent mIntent = new Intent(context, ReminderReceiver.class);

        int mNotificationID = getNotificationID(context);

        @SuppressLint("UnspecifiedImmutableFlag")
        PendingIntent mPendingIntent = PendingIntent.getBroadcast(context, mNotificationID, mIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.HOUR_OF_DAY, hour);
        mCalendar.set(Calendar.MINUTE, min);
        mCalendar.set(Calendar.SECOND, 0);
        mIntent.putExtra("id", mNotificationID);
        mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), mPendingIntent);

        if (id != -1) {
            edit(note, year, month, day, hour, min, id, context);
        } else if (Utils.exist(new File(context.getExternalFilesDir("reminders"), "reminders").getAbsolutePath())) {
            add(note, year, month, day, hour, min, mNotificationID,context);
        } else {
            initialize(note, year, month, day, hour, min, mNotificationID, context);
        }
        Utils.saveInt("notificationID", mNotificationID + 1, context);
        new MaterialAlertDialogBuilder(context)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(R.string.app_name)
                .setMessage(context.getString(R.string.reminder_message, Common.getAdjustedTime(year, month, day, hour, min)))
                .setCancelable(false)
                .setPositiveButton(R.string.cancel, (dialogInterface, i) -> {
                }).show();
    }

    public static DatePickerDialog launchDatePicker(int id, String note, Context context) {
        Calendar mCalendar = Calendar.getInstance();
        return new DatePickerDialog(context,
                (view, year, month, dayOfMonth) -> {
                    setYear(year);
                    setMonth(month);
                    setDay(dayOfMonth);
                    if (getYear() != -1 && getMonth() != -1 && getDay() != -1) {
                        launchTimePicker(year, month, dayOfMonth, id, note, context).show();
                    }
                }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public static TimePickerDialog launchTimePicker(double year, double month, double day, int id,
                                                    String note, Context context) {
        Calendar mCalendar = Calendar.getInstance();
        return new TimePickerDialog(context,
                (view, hourOfDay, minute) -> setReminder(year, month,  day, hourOfDay, minute, id, note, context), mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), false);
    }

}