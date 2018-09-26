package com.netban.edc.wallet.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by Evan on 2018/9/3.
 */

public class OutScrollView extends ScrollView {
    public OutScrollView(Context context) {
        super(context);
    }

    public OutScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OutScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
//        if (ev.getAction()==MotionEvent.ACTION_DOWN){
//            return false;
//        }else{
//            return true;
//        }

    }
}
