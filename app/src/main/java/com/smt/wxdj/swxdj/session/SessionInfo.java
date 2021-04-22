package com.smt.wxdj.swxdj.session;

/**
 * Created by gbh on 17/1/20.
 */

public class SessionInfo {
    private String sortType;
    private SortEt sortEt;

    public SessionInfo(String sortType, SortEt sortEt) {
        this.sortType = sortType;
        this.sortEt = sortEt;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public SortEt getSortEt() {
        return sortEt;
    }

    public void setSortEt(SortEt sortEt) {
        this.sortEt = sortEt;
    }
}
