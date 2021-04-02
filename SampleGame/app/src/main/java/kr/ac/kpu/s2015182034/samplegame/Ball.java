package kr.ac.kpu.s2015182034.samplegame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Ball {
    private float x, y;   // 위치
    private float dx, dy; // 속도

    private static int sx, sy; // 크기

    private static Bitmap bitmap;

    public Ball(float x, float y, float dx, float dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;

        if(bitmap == null) {
            Resources res = GameView.view.getResources();
            bitmap = BitmapFactory.decodeResource(res, R.mipmap.soccer_ball_240);
            sx = bitmap.getWidth();
            sy = bitmap.getHeight();
        }
    }

    public void update() {
        this.x += this.dx * GameView.frameTime;
        this.y += this.dy * GameView.frameTime;

        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();

        if(x < 0 || x + sx > w){
            dx *= -1;
        }
        if(y < 0 || y + sy > h){
            dy *= -1;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, this.x, this.y, null);
    }
}
