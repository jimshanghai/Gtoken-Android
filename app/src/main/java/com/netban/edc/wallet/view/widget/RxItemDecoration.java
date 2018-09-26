package com.netban.edc.wallet.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.view.View;

import com.netban.edc.wallet.utils.MeasureUtil;

/**
 * Created by Evan on 2018/8/22.
 */

public class RxItemDecoration extends ItemDecoration {
    private final Paint paint;
    private Context context;

    public RxItemDecoration(Context context,int color){
        this.context=context;
        paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);


    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
//        outRect.left= MeasureUtil.dp2px(context,left);
//        outRect.top= MeasureUtil.dp2px(context,top);
//        outRect.right= MeasureUtil.dp2px(context,right);
        outRect.bottom= MeasureUtil.dp2px(context,1);

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        for (int i=0;i<parent.getChildCount();i++){
            View child = parent.getChildAt(i);

            float bottom = MeasureUtil.dp2pxf(context, 1);
            float left = MeasureUtil.dp2pxf(context, 20);
            float right = MeasureUtil.dp2pxf(context, 20);

            RectF rectF=new RectF(left,child.getBottom(),parent.getWidth()-right,child.getBottom()+bottom);
            c.drawRect(rectF,paint);
        }

    }
}
