package kr.ac.kpu.s2015182034.dragonflight.framework;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.HashMap;

import kr.ac.kpu.s2015182034.dragonflight.UI.View.GameView;

public class GameBitmap {
    private static HashMap<Integer, Bitmap> bitmaps = new HashMap<Integer, Bitmap>();

    public static Bitmap load(int resId) {
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
}
