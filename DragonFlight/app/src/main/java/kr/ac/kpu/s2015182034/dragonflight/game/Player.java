package kr.ac.kpu.s2015182034.dragonflight.game;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.kpu.s2015182034.dragonflight.R;
import kr.ac.kpu.s2015182034.dragonflight.framework.BoxCollidable;
import kr.ac.kpu.s2015182034.dragonflight.framework.GameBitmap;
import kr.ac.kpu.s2015182034.dragonflight.framework.GameObject;

public class Player implements GameObject, BoxCollidable {

    private static final int BULLET_SPEED = 1500;
    private static final float FIRE_INTERVAL =  1.0f / 7.5f;
    private static final float LASER_DURATION =  FIRE_INTERVAL / 3.0f;

    private float fireTime;
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
        this.planeBitmap = new GameBitmap(R.mipmap.fighter);
        this.fireBitmap = new GameBitmap(R.mipmap.laser_0);
        this.fireTime = 0.0f;   //
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
        fireTime += game.frameTime;
        if(fireTime >= FIRE_INTERVAL){
            fireBullet();
            fireTime -= FIRE_INTERVAL;
        }
    }

    private void fireBullet() {
        Bullet bullet = Bullet.get(this.x, this.y, BULLET_SPEED);
        MainGame.get().add(MainGame.Layer.bullet,bullet);
    }
    @Override
    public void draw(Canvas canvas) {
        planeBitmap.draw(canvas, x, y);
        if(fireTime < LASER_DURATION) {
            fireBitmap.draw(canvas, x, y - 50);
        }
    }

    @Override
    public void getBoundingRect(RectF rect) {
        planeBitmap.getBoundingRect(x,y, rect);
    }
}
