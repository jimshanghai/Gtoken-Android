package com.netban.edc.wallet.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.netban.edc.wallet.R;


import static android.graphics.Shader.TileMode.CLAMP;
import static android.graphics.Shader.TileMode.REPEAT;

/**
 * Created by Evan on 2018/8/3.
 */

public class RingImageView extends ImageView {

    private Paint imgpaint;
    private Paint ringpaint;
    private float def_width=10.0f;
    private float width_stroke;
    private int color_stroke;
    private float distance;
    private int color_bg=Color.TRANSPARENT;
    private Paint bg_paint;

    public RingImageView(Context context) {
        super(context);
        init();
    }

    public RingImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }


    private void obtainStyledAttrs(Context context, @Nullable AttributeSet attrs,int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RingImageView, defStyleAttr,0);
        width_stroke=typedArray.getDimension(R.styleable.RingImageView_strokewidth,def_width);
        color_stroke=typedArray.getColor(R.styleable.RingImageView_strokecolor,Color.WHITE);
        distance=typedArray.getDimension(R.styleable.RingImageView_distance,distance);
        color_bg=typedArray.getInt(R.styleable.RingImageView_color_bg,color_bg);

        typedArray.recycle();
        init();
    }
    public void init(){
        imgpaint = new Paint();
        bg_paint = new Paint();
        bg_paint.setColor(color_bg);
        ringpaint = new Paint();
        ringpaint.setStyle(Paint.Style.STROKE);
        ringpaint.setColor(color_stroke);
        ringpaint.setStrokeWidth(width_stroke);
    }

    public Paint getImgpaint() {
        return imgpaint;
    }

    public void setImgpaint(Paint imgpaint) {
        this.imgpaint = imgpaint;
        postInvalidate();
    }

    public Paint getRingpaint() {
        return ringpaint;
    }

    public void setRingpaint(Paint ringpaint) {
        this.ringpaint = ringpaint;
        postInvalidate();
    }

    public float getDef_width() {
        return def_width;
    }

    public void setDef_width(float def_width) {
        this.def_width = def_width;
        postInvalidate();
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
        postInvalidate();
    }

    public int getColor_bg() {
        return color_bg;
    }

    public void setColor_bg(int color_bg) {
        this.color_bg = color_bg;
        postInvalidate();
    }

    public Paint getBg_paint() {
        return bg_paint;
    }

    public void setBg_paint(Paint bg_paint) {
        this.bg_paint = bg_paint;
        postInvalidate();
    }

    public float getWidth_stroke() {
        return width_stroke;
    }

    public void setWidth_stroke(float width_stroke) {
        this.width_stroke = width_stroke;
        postInvalidate();
    }

    public int getColor_stroke() {
        return color_stroke;
    }

    public void setColor_stroke(int color_stroke) {
        this.color_stroke = color_stroke;
        postInvalidate();
    }

    public RingImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainStyledAttrs(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int result = Math.min(getMeasuredHeight(), getMeasuredWidth());
        setMeasuredDimension(result, result);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();
        if (drawable == null) {
            return; // couldn't resolve the URI
        }

        if (drawable.getIntrinsicWidth() == 0 || drawable.getIntrinsicHeight() == 0) {
            return;     // nothing to draw (empty bounds)
        }

        Bitmap fg_bitmap = drawable2Bitmap(drawable);

        imgpaint.setShader(new BitmapShader(fg_bitmap,CLAMP,CLAMP));
        canvas.drawCircle(canvas.getWidth()/2,canvas.getHeight()/2,canvas.getWidth()/2-width_stroke,bg_paint);

        canvas.drawCircle(canvas.getWidth()/2,canvas.getHeight()/2,canvas.getWidth()/2-width_stroke,ringpaint);
        canvas.drawCircle(canvas.getWidth()/2,canvas.getHeight()/2,canvas.getWidth()/2-distance-width_stroke,imgpaint);

    }


    /**
     * drawable转换成bitmap
     */
    private Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        //根据传递的scaletype获取matrix对象，设置给bitmap
        Matrix matrix = getImageMatrix();
        if (matrix != null) {
            canvas.concat(matrix);
        }
        drawable.draw(canvas);
        return bitmap;
    }
}
