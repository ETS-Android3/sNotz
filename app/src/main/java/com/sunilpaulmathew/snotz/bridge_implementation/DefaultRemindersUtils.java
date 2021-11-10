package com.sunilpaulmathew.snotz.bridge_implementation;

import android.content.Context;

import com.sunilpaulmathew.snotz.bridge_interface.RemindersUtils;
import com.sunilpaulmathew.snotz.utils.ReminderItems;
import com.sunilpaulmathew.snotz.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class DefaultRemindersUtils implements RemindersUtils {
    private static int mPosition = -1;
    private static JSONObject mJSONObject;
    private static JSONArray mJSONArray;
    private static String mNote = null;

    private static double mYear = -1, mMonth = -1, mDay = -1;

    public String getNote(String string) {
        try {
            JSONObject obj = new JSONObject(string);
            return obj.getString("note");
        } catch (JSONException ignored) {
        }
        return null;
    }

    public int getNotificationID(Context context) {
        return Utils.getInt("notificationID", 0, context);
    }

    public JSONArray getReminders(Context context) {
        if (Utils.exist(new File(context.getExternalFilesDir("reminders"), "reminders").getAbsolutePath())) {
            try {
                JSONObject main = new JSONObject(Objects.requireNonNull(Utils.read(new File(context.getExternalFilesDir("reminders"), "reminders").getAbsolutePath())));
                return main.getJSONArray("reminders");
            } catch (JSONException ignored) {
            }
        }
        return null;
    }

    public String getReminderMessage(Context context) {
        Calendar mCalendar = Calendar.getInstance();
        int mYear = mCalendar.get(Calendar.YEAR);
        int mMonth = mCalendar.get(Calendar.MONTH);
        int mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        int mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int mMin = mCalendar.get(Calendar.MINUTE);
        for (ReminderItems items : getRawData(context)) {
            if (mYear == items.getYear() && mMonth == items.getMonth() && mDay == items.getDay()
                    && mHour == items.getHour() && mMin == items.getMin()) {
                mNote = items.getNote();
                mPosition = items.getNotificationID();
            }
        }
        delete(mPosition, context);
        return mNote;
    }

    public List<ReminderItems> getRawData(Context context) {
        List<ReminderItems> mData = new ArrayList<>();
        if (Utils.exist(new File(context.getExternalFilesDir("reminders"), "reminders").getAbsolutePath())) {
            for (int i = 0; i < Objects.requireNonNull(getReminders(context)).length(); i++) {
                try {
                    JSONObject command = Objects.requireNonNull(getReminders(context)).getJSONObject(i);
                    mData.add(new ReminderItems(getNote(command.toString()), getYear(command.toString()), getMonth(command.toString()),
                            getDay(command.toString()), getHour(command.toString()), getMin(command.toString()), getID(command.toString())));
                } catch (JSONException ignored) {
                }
            }
        }
        return mData;
    }

    public double getYear() {
        return mYear;
    }

    public double getMonth() {
        return mMonth;
    }

    public double getDay() {
        return mDay;
    }

    public void setYear(double year) {
        mYear = year;
    }

    public void setMonth(double month) {
        mMonth = month;
    }

    public void setDay(double day) {
        mDay = day;
    }

    public void add(String noteToAdd, double year, double month, double day,
                           int hour, int min, int id, Context context) {
        mJSONObject = new JSONObject();
        mJSONArray = new JSONArray();
        try {
            for (ReminderItems items : getRawData(context)) {
                JSONObject reminder = new JSONObject();
                reminder.put("note", items.getNote());
                reminder.put("year", items.getYear());
                reminder.put("month", items.getMonth());
                reminder.put("day", items.getDay());
                reminder.put("hour", items.getHour());
                reminder.put("min", items.getMin());
                reminder.put("id", items.getNotificationID());
                mJSONArray.put(reminder);
            }
            JSONObject reminder = new JSONObject();
            reminder.put("note", noteToAdd);
            reminder.put("year", year);
            reminder.put("month", month);
            reminder.put("day", day);
            reminder.put("hour", hour);
            reminder.put("min", min);
            reminder.put("id", id);
            mJSONArray.put(reminder);
            mJSONObject.put("reminders", mJSONArray);
            Utils.create(mJSONObject.toString(), new File(context.getExternalFilesDir("reminders"), "reminders").getAbsolutePath());
        } catch (JSONException ignored) {
        }
    }

    public void edit(String noteToEdit, double year, double month, double day,
                            int hour, int min, int id, Context context) {
        mJSONObject = new JSONObject();
        mJSONArray = new JSONArray();
        try {
            for (ReminderItems items : getRawData(context)) {
                if (items.getNotificationID() != id) {
                    JSONObject reminder = new JSONObject();
                    reminder.put("note", items.getNote());
                    reminder.put("year", items.getYear());
                    reminder.put("month", items.getMonth());
                    reminder.put("day", items.getDay());
                    reminder.put("hour", items.getHour());
                    reminder.put("min", items.getMin());
                    reminder.put("id", items.getNotificationID());
                    mJSONArray.put(reminder);
                }
            }
            JSONObject reminder = new JSONObject();
            reminder.put("note", noteToEdit);
            reminder.put("year", year);
            reminder.put("month", month);
            reminder.put("day", day);
            reminder.put("hour", hour);
            reminder.put("min", min);
            reminder.put("id", id);
            mJSONArray.put(reminder);
            mJSONObject.put("reminders", mJSONArray);
            Utils.create(mJSONObject.toString(), new File(context.getExternalFilesDir("reminders"), "reminders").getAbsolutePath());
        } catch (JSONException ignored) {
        }
    }

    public void delete(int id, Context context) {
        mJSONObject = new JSONObject();
        mJSONArray = new JSONArray();
        try {
            for (ReminderItems items : getRawData(context)) {
                if (items.getNotificationID() != id) {
                    JSONObject reminder = new JSONObject();
                    reminder.put("note", items.getNote());
                    reminder.put("year", items.getYear());
                    reminder.put("month", items.getMonth());
                    reminder.put("day", items.getDay());
                    reminder.put("hour", items.getHour());
                    reminder.put("min", items.getMin());
                    reminder.put("id", items.getNotificationID());
                    mJSONArray.put(reminder);
                }
            }
            mJSONObject.put("reminders", mJSONArray);
            Utils.create(mJSONObject.toString(), new File(context.getExternalFilesDir("reminders"), "reminders").getAbsolutePath());
        } catch (JSONException ignored) {
        }
    }

    public void initialize(String note, double year, double month, double day, int hour,
                                  int min, int id, Context context) {
        mJSONObject = new JSONObject();
        mJSONArray = new JSONArray();
        try {
            JSONObject reminder = new JSONObject();
            reminder.put("note", note);
            reminder.put("year", year);
            reminder.put("month", month);
            reminder.put("day", day);
            reminder.put("hour", hour);
            reminder.put("min", min);
            reminder.put("id", id);
            mJSONArray.put(reminder);
            mJSONObject.put("reminders", mJSONArray);
            Utils.create(mJSONObject.toString(), new File(context.getExternalFilesDir("reminders"), "reminders").getAbsolutePath());
        } catch (JSONException ignored) {
        }
    }

    private double getYear(String string) {
        try {
            JSONObject obj = new JSONObject(string);
            return obj.getDouble("year");
        } catch (JSONException ignored) {
        }
        return 0;
    }

    private double getMonth(String string) {
        try {
            JSONObject obj = new JSONObject(string);
            return obj.getDouble("month");
        } catch (JSONException ignored) {
        }
        return 0;
    }

    private double getDay(String string) {
        try {
            JSONObject obj = new JSONObject(string);
            return obj.getDouble("day");
        } catch (JSONException ignored) {
        }
        return 0;
    }

    private int getHour(String string) {
        try {
            JSONObject obj = new JSONObject(string);
            return obj.getInt("hour");
        } catch (JSONException ignored) {
        }
        return 0;
    }

    private int getMin(String string) {
        try {
            JSONObject obj = new JSONObject(string);
            return obj.getInt("min");
        } catch (JSONException ignored) {
        }
        return 0;
    }

    private int getID(String string) {
        try {
            JSONObject obj = new JSONObject(string);
            return obj.getInt("id");
        } catch (JSONException ignored) {
        }
        return 0;
    }

}
