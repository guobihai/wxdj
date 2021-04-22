package com.smt.wxdj.swxdj.bean;

/**
 * Created by gbh on 17/4/24.
 */

public class Trk extends User {
    private String Trk;
    private String TrkSta;
    private boolean select;

    public String getTrk() {
        return Trk;
    }

    public String getTrkSta() {
        return TrkSta;
    }

    public void setTrkSta(String trkSta) {
        TrkSta = trkSta;
    }

    public void setTrk(String trk) {
        this.Trk = trk;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
