package com.smt.wxdj.swxdj.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.smt.wxdj.swxdj.BR;
import java.io.Serializable;


/**
 * Created by gbh on 16/9/22.
 */

public class Dock extends BaseObservable implements Serializable, Comparable<Dock> {
    private int index;
    private String dock;
    private String majloc;
    private String subloc;
    private String webUrl;
    private transient boolean isSelect;

    @Bindable
    public String getDock() {
        return dock;
    }

    public void setDock(String dock) {
        this.dock = dock;
        notifyPropertyChanged(BR.dock);
    }

    public String getMajloc() {
        return majloc;
    }

    public void setMajloc(String majloc) {
        this.majloc = majloc;
    }

    public String getSubloc() {
        return subloc;
    }

    public void setSubloc(String subloc) {
        this.subloc = subloc;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Bindable
    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
        notifyPropertyChanged(BR.select);
    }

    @Override
    public int compareTo(Dock dock) {
        return this.dock.compareTo(dock.getDock());
    }
}
