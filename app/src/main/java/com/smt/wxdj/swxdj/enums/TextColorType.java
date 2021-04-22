package com.smt.wxdj.swxdj.enums;

/**
 * Created by gbh on 16/11/25.
 */

public enum TextColorType {
    DEFAULT("黑色", android.R.color.black),
    GREEN("绿色", android.R.color.holo_green_dark),
    RED("红色", android.R.color.holo_red_dark),
    BLUE("蓝色", android.R.color.holo_blue_dark);

    private String name;
    private int color;

    TextColorType(String name, int color) {
        this.name = name;
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    }
