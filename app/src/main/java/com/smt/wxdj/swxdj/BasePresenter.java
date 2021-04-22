package com.smt.wxdj.swxdj;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by gbh on 16/9/8.
 */
public abstract class BasePresenter<T> {
    protected Reference<T> mViewRef;


    public void attachView(T view){
        mViewRef = new WeakReference<T>(view);
    }

    protected  T getView(){
       return mViewRef.get();
    } 

    public boolean isViewAttached(){
        return  mViewRef!=null && mViewRef.get() !=null;
    }
    public  void detachView(){
        if(null !=mViewRef){
            mViewRef.clear();
            mViewRef = null;
        }
    }

}
