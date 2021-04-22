package com.smt.wxdj.swxdj.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by gbh on 16/8/4.
 */
public class CustomVScrollView extends ScrollView {
    private GestureDetector mGestureDetector;
    private OnTouchListener mGestureListener;

    private static final String TAG = "CustomHScrollView";


    /**
     * @function CustomHScrollView constructor
     * @param context  Interface to global information about an application environment.
     *
     */
    public CustomVScrollView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        mGestureDetector = new GestureDetector(new HScrollDetector());
        setFadingEdgeLength(0);
    }


    /**
     * @function CustomHScrollView constructor
     * @param context Interface to global information about an application environment.
     * @param attrs A collection of attributes, as found associated with a tag in an XML document.
     */
    public CustomVScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        mGestureDetector = new GestureDetector(new HScrollDetector());
        setFadingEdgeLength(0);
    }

    /**
     * @function  CustomHScrollView constructor
     * @param context Interface to global information about an application environment.
     * @param attrs A collection of attributes, as found associated with a tag in an XML document.
     * @param defStyle style of view
     */
    public CustomVScrollView(Context context, AttributeSet attrs,
                             int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        mGestureDetector = new GestureDetector(new HScrollDetector());
        setFadingEdgeLength(0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        System.out.println("====dispatchHoverEvent=========");
        return super.onInterceptTouchEvent(ev) && mGestureDetector.onTouchEvent(ev);
    }


    // Return false if we're scrolling in the y direction
    class HScrollDetector implements GestureDetector.OnGestureListener {
        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if(Math.abs(distanceX) < Math.abs(distanceY)) {
                System.out.println("====dispatchHoverEvent======true===");
                return true;
            }
            System.out.println("====dispatchHoverEvent===false======");
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }
    }

}
