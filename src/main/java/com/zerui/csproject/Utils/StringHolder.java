package com.zerui.csproject.Utils;

public class StringHolder {
    private String string;
    private final static StringHolder INSTANCE = new StringHolder();

    private StringHolder() {}

    public static StringHolder getInstance() {
        return INSTANCE;
    }

    public void setString(String u) {
        this.string = u;
    }

    public String getString() {
        return this.string;
    }
}
