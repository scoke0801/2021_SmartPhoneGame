package kr.ac.kpu.s2015182034.termproject.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.s2015182034.termproject.R;
import kr.ac.kpu.s2015182034.termproject.animation.GameBitmap;
import kr.ac.kpu.s2015182034.termproject.framework.BoxCollidable;
import kr.ac.kpu.s2015182034.termproject.framework.GameObject;
import kr.ac.kpu.s2015182034.termproject.ui.view.GameView;


public class ImageObject implements GameObject, BoxCollidable {
    protected GameBitmap bitmap;

    protected Rect srcRect = new Rect();
    protected RectF dstRect = new RectF();
    protected ImageObject() {}
    float x, y;
    public ImageObject(int resId, float x, float y) {
        init(resId, x, y);
    }
    protected void init(int resId, float x, float y) {
        bitmap = new GameBitmap(resId);
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        this.x = x;
        this.y = y;
        srcRect.set(0, 0, w, h);
        float l = x - w / 2 * GameView.MULTIPLIER;
        float t = y - h / 2 * GameView.MULTIPLIER;
        float r = x + w / 2 * GameView.MULTIPLIER;
        float b = y + h / 2 * GameView.MULTIPLIER;
        dstRect.set(l, t, r, b);
    }

    public float getRight() {
        return dstRect.right;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        bitmap.drawStretchedVerticaly(canvas, x, y);
        //canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }

    @Override
    public void movePosition(float xMoved, float yMoved) {

    }

    public float getDstWidth() {
        return dstRect.width();
    }
    public float getDstHeight() {
        return dstRect.height();
    }

    @Override
    public void getBoundingRect(RectF rect) {
        rect.set(dstRect);
    }

    public RectF getBoundingRect() {
        return dstRect;
    }
}
