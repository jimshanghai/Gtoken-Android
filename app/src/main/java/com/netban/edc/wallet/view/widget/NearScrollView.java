package com.netban.edc.wallet.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by Evan on 2018/9/3.
 */

public class NearScrollView extends ScrollView {
    private boolean isScrolledToTop;
    private boolean isScrolledToBottom;

    public NearScrollView(Context context) {
        super(context);
    }

    public NearScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NearScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:

                break;
        }
        return super.dispatchTouchEvent(ev);
    }

}
