package com.netban.edc.wallet.view.widget;

import android.animation.ValueAnimator;
import android.app.Fragment;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;


/**
 * Created by Administrator on 2017/6/27.
 */

public class ReMoveLayout extends FrameLayout {


    private float srawx;
    private float lastx;
    private Scroller scroller;

    public ReMoveLayout(Context context) {
        super(context);
    }

    public ReMoveLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReMoveLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void init(Context context){
        scroller = new Scroller(context);
    }



    @Override
    public void computeScroll() {
        super.computeScroll();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                srawx = event.getRawX();
                lastx=srawx;
                break;

            case MotionEvent.ACTION_MOVE:
                float rawX = event.getRawX();
                float mx = rawX - lastx;
                if (mx<0){
                    if (getScrollX()>=(getChildAt(1).getMeasuredWidth())){
                        scrollTo(getChildAt(1).getMeasuredWidth(),0);
                        break;
                    }else {
                        scrollBy((int) -mx,0);
                        lastx=rawX;
                        return true;
                    }
                }else{
                    if (getScrollX()==0){
                        break;
                    }else{
                        scrollBy((int) -mx,0);
                        lastx=rawX;
                        return true;
                    }
                }
            case MotionEvent.ACTION_UP:
                if (getScrollX()>=(getChildAt(1).getMeasuredWidth())/2){
                    scrollTo(getChildAt(1).getMeasuredWidth(),0);
                    break;
                }else{
                    scrollTo(0,0);
                }
                break;
        }

        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i=0;i<getChildCount();i++){
            View childAt = getChildAt(i);

            if (((String) childAt.getTag()).equals("main")){
                childAt.layout(0,0,0,0);
            }else if (((String) childAt.getTag()).equals("slider")) {
               childAt.layout(getMeasuredWidth(),t,getMeasuredWidth()+childAt.getMeasuredWidth(),b);
            }
        }
    }
}
