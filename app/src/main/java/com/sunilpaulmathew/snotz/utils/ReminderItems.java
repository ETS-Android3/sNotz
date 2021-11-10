package com.sunilpaulmathew.snotz.utils;

import com.sunilpaulmathew.snotz.bridge_implementation.DefaultUtilsID;
import com.sunilpaulmathew.snotz.bridge_interface.IDUtils;

import java.io.Serializable;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on October 02, 2021
 */
public class ReminderItems implements Serializable {

    private final double mYear, mMonth, mDay;
    private final int mHour, mMin;
    private final String mNote;

    private final IDUtils idu = new DefaultUtilsID();

    public ReminderItems(String note, double year, double month, double day, int hour, int min, int id) {
        this.mNote = note;
        this.mYear = year;
        this.mMonth = month;
        this.mDay = day;
        this.mHour = hour;
        this.mMin = min;

        idu.setID(id);
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

    public int getHour() {
        return mHour;
    }

    public int getNotificationID() {
        return idu.getID();
    }

    public int getMin() {
        return mMin;
    }

    public String getNote() {
        return mNote;
    }

}