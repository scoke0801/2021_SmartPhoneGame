package kr.ac.kpu.s2015182034.dragonflight.game;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.kpu.s2015182034.dragonflight.R;
import kr.ac.kpu.s2015182034.dragonflight.UI.View.GameView;
import kr.ac.kpu.s2015182034.dragonflight.framework.BoxCollidable;
import kr.ac.kpu.s2015182034.dragonflight.framework.GameBitmap;
import kr.ac.kpu.s2015182034.dragonflight.framework.GameObject;

public class Player implements GameObject, BoxCollidable {

    private static final int BULLET_SPEED = 1500;
    private static final float FIRE_INTERVAL =  1.0f / 7.5f;
    private float fireTime;
    private float x, y;   // 위치
    private float dx, dy; // 속도
    private float tx, ty; //target 위치

    private float speed;

    private GameBitmap bitmap;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        this.tx = x;
        this.ty = 0;
        this.speed = 800;
        this.bitmap = new GameBitmap(R.mipmap.fighter);
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
        Bullet bullet = new Bullet(this.x, this.y, BULLET_SPEED);
        MainGame.get().add(bullet);
    }
    @Override
    public void draw(Canvas canvas) {
        bitmap.draw(canvas, x, y);
    }


    @Override
    public RectF getBoundingRect() {
        return bitmap.geetBoundingRect(x,y);
    }
}
