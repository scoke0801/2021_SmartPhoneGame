package kr.ac.kpu.s2015182034.cookierun.framework.bitmap;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.ArrayList;

import kr.ac.kpu.s2015182034.cookierun.framework.View.GameView;

public class IndexedGameBitmap extends GameBitmap {

    private static final String TAG = IndexedGameBitmap.class.getSimpleName();
    private int width, height, xCount, border, spacing;

    public IndexedGameBitmap(int resId, int width, int height, int xCount, int border, int spacing) {
        super(resId);

        this.width = width;
        this.height = height;
        this.xCount = xCount;
        this.border = border;
        this.spacing = spacing;
    }

    protected Rect srcRect;
    // jelly index range : 0 ~ 59
    public void setIndex(int index) {
        int x = index % xCount;
        int y = index / xCount;
        int l = border + x * (width + spacing);
        int t = border + y * (height + spacing);
        int r = l + width;
        int b = t + height;
        Rect rect = new Rect(l, t, r, b);
        srcRect.set(rect);
    }

    @Override
    public void draw(Canvas canvas, float x, float y) {
        float w = width / 2 * GameView.MULTIPLIER;
        float h = height / 2 * GameView.MULTIPLIER;
        dstRect.set(x - w, y - h, x + w, y + h);
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }

    public void draw(Canvas canvas, RectF dstRect){
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }
}