package kr.ac.kpu.s2015182034.samplegame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Player {
    private float x, y;   // 위치
    private float dx, dy; // 속도

    private int sx, sy; // 크기

    private Bitmap bitmap;

    public Player(float x, float y, float dx, float dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;

        if(bitmap == null) {
            Resources res = GameView.view.getResources();
            bitmap = BitmapFactory.decodeResource(res, R.mipmap.plane_240);
            sx = bitmap.getWidth();
            sy = bitmap.getHeight();
        }
    }

    public void moveTo(float x, float y){
        this.x = x;
        this.y = y;
    }
    public void update() {
        return;
    }

    public void draw(Canvas canvas) {
        float left = x - sx / 2;
        float top = y - sy / 2;
        canvas.drawBitmap(bitmap, left, top, null);
    }
}
