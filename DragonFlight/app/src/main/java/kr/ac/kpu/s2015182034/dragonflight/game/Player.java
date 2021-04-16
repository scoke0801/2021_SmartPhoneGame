package kr.ac.kpu.s2015182034.dragonflight.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import kr.ac.kpu.s2015182034.dragonflight.R;
import kr.ac.kpu.s2015182034.dragonflight.framework.GameBitmap;
import kr.ac.kpu.s2015182034.dragonflight.framework.GameObject;

public class Player implements GameObject {

    private float x, y;   // 위치
    private float dx, dy; // 속도

    private int imageWidth, imageHeight; // 크기

    private float tx, ty; //target 위치

    private float speed;

    private Bitmap bitmap;

    public Player(float x, float y, float dx, float dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.tx = x;
        this.ty = 0;
        this.speed = 800;
        this.bitmap = GameBitmap.load(R.mipmap.plane_240);
        imageWidth = bitmap.getWidth();
        imageHeight = bitmap.getHeight();
    }

    public void moveTo(float x, float y){
        //Sound.play(R.raw.hadouken);

        this.tx = x;
        //this.ty = this.y;
    }
    public void update() {
        MainGame game = MainGame.get();
        float dx = speed * game.frameTime;
        if (tx < x){
            // Move Left
            dx = -dx;
        }
        x += dx;

        if((dx > 0 && this.x > tx)||(dx < 0 && this.x < tx)) {
            x = tx;
            dx = 0;
        }

        //y += dy;
        //if((dy > 0 && this.y > ty)||(dy < 0 && this.y < ty)) {
        //    y = ty;
        //    dy = 0;
        //}
    }

    public void draw(Canvas canvas) {
        float left = x - imageWidth / 2;
        float top = y - imageHeight / 2;
        //canvas.save();

        canvas.drawBitmap(bitmap, left, top, null);

        //canvas.restore();
    }
}
