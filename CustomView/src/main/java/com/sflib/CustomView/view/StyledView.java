package com.sflib.CustomView.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.sflib.CustomView.R;

/**
 * Created by xieningtao on 15-12-29.
 */
public class StyledView extends View {

    private final Paint mRectPaint;
    private final Paint mCirclePaint;
    private final int mRectCirclePadding;

    private final float mCircleRadio;
    private final float mRectWidth;
    private final float mRectHeight;

    private final int LL[] = {
            R.styleable.StyledView_circleColor,
            R.styleable.StyledView_rectColor
    };

    public StyledView(Context context) {
        this(context, null);
    }

    public StyledView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.styleViewColor);
    }

    public StyledView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


//        TypedArray def_a = context.obtainStyledAttributes(attrs, LL, defStyleAttr, 0);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StyledView, defStyleAttr, 0);

        int circleColor = a.getColor(R.styleable.StyledView_circleColor, Color.BLUE);
        int rectColor = a.getColor(R.styleable.StyledView_rectColor, Color.BLUE);
//        if (circleColor == Color.BLUE) {
//            circleColor = a.getColor(R.styleable.StyledView_circleColor, Color.BLUE);
//        }
//
//        if (rectColor == Color.BLUE) {
//            rectColor = a.getColor(R.styleable.StyledView_rectColor, Color.BLUE);
//        }
        int rect_circle_padding = a.getDimensionPixelSize(R.styleable.StyledView_rect_circle_padding, 0);

        int circleRadio = a.getDimensionPixelSize(R.styleable.StyledView_circle_radio, 0);

        int rectWidth = a.getDimensionPixelOffset(R.styleable.StyledView_rect_width, 0);
        int rectHeight = a.getDimensionPixelSize(R.styleable.StyledView_rect_height, 0);

        a.recycle();

        mRectPaint = new Paint();
        mCirclePaint = new Paint();

        mRectPaint.setColor(rectColor);
        mCirclePaint.setColor(circleColor);

        mRectCirclePadding = rect_circle_padding;
        mCircleRadio = circleRadio;
        mRectHeight = rectHeight;
        mRectWidth = rectWidth;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();

        float cx = 1.0f * width / 2;
        float cy = mCircleRadio;
        canvas.drawCircle(cx, cy, mCircleRadio, mCirclePaint);

        RectF rectF = new RectF();
        float top = 2 * mCircleRadio + mRectCirclePadding;
        rectF.set(0, top, mRectWidth, top + mRectHeight);
        canvas.drawRect(rectF, mRectPaint);

    }
}
