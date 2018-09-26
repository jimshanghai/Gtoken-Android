package com.netban.edc.wallet.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RelativeLayout;

import com.netban.edc.wallet.utils.MeasureUtil;

/**
 * Created by Evan on 2018/8/20.
 */

public class TradeLayout extends RelativeLayout {
    private Paint paint;
    private boolean ishow;
    private String msg;
    public TradeLayout(Context context) {
        super(context);
        init();
    }

    public TradeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setTextSize(MeasureUtil.sp2px(getContext(),20));
    }

    public TradeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public boolean isIshow() {
        return ishow;
    }

    public void setIshow(boolean ishow) {
        this.ishow = ishow;
        postInvalidate();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void commit(){
        postInvalidate();
    }


    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
        if (ishow){
            Path path = new Path();
            int sw = canvas.getWidth() /4;
            int sh = 3*canvas.getHeight()/4;

            int ew=canvas.getWidth()*3/4;
            int eh=canvas.getHeight()/4;


            path.moveTo(sw,sh);
            path.lineTo(ew,eh);
            canvas.drawTextOnPath(msg,path,0,0,paint);
        }


    }
}
