package com.smt.wxdj.swxdj.enums;

import com.smt.wxdj.swxdj.R;

/**
 * Created by gbh on 16/11/25.
 */

public enum ColorType {
    DEFAULT("WHITE", R.drawable.shapee_item),
    GREEN("GREEN", R.drawable.shapee_green),
    DARK_GREEN("DARK_GREEN", R.drawable.shapee_recommend),
    YELLOW("YELLOW", R.drawable.shapee_yellow),
    PINK("PINK", R.drawable.shapee_pink),
    RED("RED", R.drawable.shapee_red),
    GRAY("GRAY", R.drawable.shapee_gray);

    private String name;
    private int color;

    ColorType(String name, int color) {
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
