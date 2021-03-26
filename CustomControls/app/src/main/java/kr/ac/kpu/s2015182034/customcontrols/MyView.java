package kr.ac.kpu.s2015182034.customcontrols;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MyView  extends View {
    private static final String TAG = MyView.class.getSimpleName();
    private Paint paint = new Paint();
    private Rect rect = new Rect();

    public MyView(Context context, AttributeSet set) {
        super(context, set);

        paint.setColor(0xff0044ff);
    }
    // 방법 2
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouch: = "  + event);
        //if(event.getAction() == MotionEvent.ACTION_MOVE)
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int l = 0 + getPaddingLeft();
        int t = 0 + getPaddingTop();
        int w = getWidth() - getPaddingRight();
        int h = getHeight()- getPaddingBottom();
        rect.set(l, t, w, h);
        Log.d(TAG, "drawing " + rect);
        canvas.drawRect(rect, paint);
    }
}
