package kr.ac.kpu.s2015182034.termproject.animation;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.HashMap;

import kr.ac.kpu.s2015182034.termproject.ui.view.GameView;

public class GameBitmap {
    private static HashMap<Integer, Bitmap> bitmaps = new HashMap<Integer, Bitmap>();

    protected static float viewWidth;
    protected Paint paint;
    public static Bitmap load(int resId) {
        viewWidth = GameView.view.getWidth();
        Bitmap bitmap = bitmaps.get(resId);
        if (bitmap == null) {
            Resources res = GameView.view.getResources();

            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inScaled = false;

            bitmap = BitmapFactory.decodeResource(res, resId, opts);
            bitmaps.put(resId, bitmap);
        }
        return bitmap;
    }

    protected Bitmap bitmap;
    protected RectF dstRect = new RectF();
    public GameBitmap(int resId){
        bitmap = load(resId);

        paint = new Paint();
        paint.setColor(0xFFFF0000);
        paint.setStrokeWidth(10f);
        paint.setStyle(Paint.Style.STROKE);
    }
    public void draw(Canvas canvas, float x, float y){
        float hw = getWidth() / 2;
        float hh = getHeight() / 2;

        float dl = x - hw * GameView.MULTIPLIER;
        float dt = y - hh * GameView.MULTIPLIER;
        float dr = x + hw * GameView.MULTIPLIER;
        float db = y + hh * GameView.MULTIPLIER;

        dstRect.set(dl, dt, dr, db);
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }

    // for waterObj
    public void drawStretched(Canvas canvas, float x, float y){
        float hh = getHeight() / 2;

        float dl = 0;
        float dt = y - hh * GameView.MULTIPLIER;
        float dr = x + viewWidth;
        float db = y + hh * GameView.MULTIPLIER;

        dstRect.set(dl, dt, dr, db);
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }

    public int getWidth(){
        return bitmap.getWidth();
    }
    public int getHeight(){
        return bitmap.getHeight();
    }

    public void getBoundingRect(float x, float y, RectF rect) {
        float hw = getWidth() / 2;
        float hh = getHeight() / 2;

        float dl = x - hw * GameView.MULTIPLIER;
        float dt = y - hh * GameView.MULTIPLIER;
        float dr = x + hw * GameView.MULTIPLIER;
        float db = y + hh * GameView.MULTIPLIER;
        rect.set(dl,dt,dr,db);
    }

    public void getStretchedBoundingRect(float x, float y, RectF rect){
        float hh = getHeight() / 2;

        float dl = 0;
        float dt = y - hh * GameView.MULTIPLIER;
        float dr = x + viewWidth;
        float db = y + hh * GameView.MULTIPLIER;
        rect.set(dl,dt,dr,db);
    }
    private RectF dstToDrawAABB = new RectF();
    public void drawAABB(Canvas canvas, float x, float y){
        getBoundingRect(x,y, dstToDrawAABB);

        dstToDrawAABB.left += 40.0f;
        dstToDrawAABB.right -= 40.0f;
        dstToDrawAABB.top += 40.0f;
        dstToDrawAABB.bottom -=40.0f;
        canvas.drawRect(dstToDrawAABB, paint);
    }
}