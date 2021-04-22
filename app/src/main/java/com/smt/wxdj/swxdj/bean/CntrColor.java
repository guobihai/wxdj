package com.smt.wxdj.swxdj.bean;

/**
 * Created by gbh on 16/12/30.
 */

public class CntrColor {
    private int R;
    private int G;
    private int B;
    private int A;
    private boolean IsKnownColor;
    private boolean IsEmpty;
    private boolean IsNamedColor;
    private boolean IsSystemColor;
    private String Name;

    public int getR() {
        return R;
    }

    public void setR(int r) {
        R = r;
    }

    public int getG() {
        return G;
    }

    public void setG(int g) {
        G = g;
    }

    public int getB() {
        return B;
    }

    public void setB(int b) {
        B = b;
    }

    public int getA() {
        return A;
    }

    public void setA(int a) {
        A = a;
    }

    public boolean isKnownColor() {
        return IsKnownColor;
    }

    public void setKnownColor(boolean knownColor) {
        IsKnownColor = knownColor;
    }

    public boolean isEmpty() {
        return IsEmpty;
    }

    public void setEmpty(boolean empty) {
        IsEmpty = empty;
    }

    public boolean isNamedColor() {
        return IsNamedColor;
    }

    public void setNamedColor(boolean namedColor) {
        IsNamedColor = namedColor;
    }

    public boolean isSystemColor() {
        return IsSystemColor;
    }

    public void setSystemColor(boolean systemColor) {
        IsSystemColor = systemColor;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
