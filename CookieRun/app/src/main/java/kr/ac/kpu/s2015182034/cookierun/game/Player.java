package kr.ac.kpu.s2015182034.cookierun.game;


import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.kpu.s2015182034.cookierun.R;
import kr.ac.kpu.s2015182034.cookierun.framework.bitmap.GameBitmap;
import kr.ac.kpu.s2015182034.cookierun.framework.bitmap.IndexedAnimationGameBitmap;
import kr.ac.kpu.s2015182034.cookierun.framework.game.BaseGame;
import kr.ac.kpu.s2015182034.cookierun.framework.iface.BoxCollidable;
import kr.ac.kpu.s2015182034.cookierun.framework.iface.GameObject;

public class Player implements GameObject, BoxCollidable {
    private static final float FIRE_INTERVAL =  1.0f / 7.5f;

    private float x, y;   // 위치
    private float dx, dy; // 속도
    private float tx, ty; //target 위치

    private float speed;

    private IndexedAnimationGameBitmap charBitmap;
    private GameBitmap fireBitmap;
    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        this.tx = x;
        this.ty = 0;
        this.speed = 800;
        this.charBitmap = new IndexedAnimationGameBitmap(R.mipmap.cookie, 12.5f, 10);
        this.charBitmap.setIndices(100, 101, 102, 103);
    }

    public void moveTo(float x, float y){
        //Sound.play(R.raw.hadouken);

        this.tx = x;
    }
    @Override
    public void update() {
        BaseGame game = BaseGame.get();
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
    }

    @Override
    public void draw(Canvas canvas) {
        charBitmap.draw(canvas, x, y);
    }

    @Override
    public void getBoundingRect(RectF rect) {
        charBitmap.getBoundingRect(x,y, rect);
    }
}
