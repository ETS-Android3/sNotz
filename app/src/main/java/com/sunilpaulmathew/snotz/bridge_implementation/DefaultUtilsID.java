package com.sunilpaulmathew.snotz.bridge_implementation;

import com.sunilpaulmathew.snotz.bridge_interface.IDUtils;

public class DefaultUtilsID implements IDUtils {
    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    private int id;
}
