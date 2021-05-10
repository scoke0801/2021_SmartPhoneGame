package kr.ac.kpu.s2015182034.cookierun.game;


import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.kpu.s2015182034.cookierun.framework.BoxCollidable;
import kr.ac.kpu.s2015182034.cookierun.framework.GameBitmap;
import kr.ac.kpu.s2015182034.cookierun.framework.GameObject;
import kr.ac.kpu.s2015182034.cookierun.framework.MainGame;

public class Player implements GameObject, BoxCollidable {

    private static final int BULLET_SPEED = 1500;
    private static final float FIRE_INTERVAL =  1.0f / 7.5f;
    private static final float LASER_DURATION =  FIRE_INTERVAL / 3.0f;

    private float x, y;   // 위치
    private float dx, dy; // 속도
    private float tx, ty; //target 위치

    private float speed;

    private GameBitmap planeBitmap;
    private GameBitmap fireBitmap;
    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        this.tx = x;
        this.ty = 0;
        this.speed = 800;
        //this.planeBitmap = new GameBitmap(R.mipmap.cookie);
    }

    public void moveTo(float x, float y){
        //Sound.play(R.raw.hadouken);

        this.tx = x;
        //this.ty = this.y;

    }
    @Override
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
    }

    @Override
    public void draw(Canvas canvas) {
        //planeBitmap.draw(canvas, x, y);
    }

    @Override
    public void getBoundingRect(RectF rect) {
        //planeBitmap.getBoundingRect(x,y, rect);
    }
}
