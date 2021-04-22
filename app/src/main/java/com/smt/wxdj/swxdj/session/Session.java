package com.smt.wxdj.swxdj.session;

import java.util.Observable;
import java.util.Observer;


/**
 * Created by gbh on 16/8/26.
 */
public class Session extends Observable {
    private static Session session = null;

    public static Session getInstance() {
        if (session == null) {
            session = new Session();
        }
        return session;
    }

    /**
     * 刷选
     *
     * @param type
     */
    public void notifySelect(String type) {
        this.setChanged();
        this.notifyObservers(type);
    }


    /**
     * 刷选
     */
    public void notifySelect(SessionInfo info) {
        this.setChanged();
        this.notifyObservers(info);
    }


    public void clear(Observer observer) {
        deleteObserver(observer);
    }

}
