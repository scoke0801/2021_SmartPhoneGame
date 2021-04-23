package kr.ac.kpu.s2015182034.dragonflight.game;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.kpu.s2015182034.dragonflight.R;
import kr.ac.kpu.s2015182034.dragonflight.framework.BoxCollidable;
import kr.ac.kpu.s2015182034.dragonflight.framework.GameBitmap;
import kr.ac.kpu.s2015182034.dragonflight.framework.GameObject;
import kr.ac.kpu.s2015182034.dragonflight.framework.Recyclable;

public class Bullet implements GameObject, BoxCollidable, Recyclable {

    private static final String TAG = Bullet.class.getSimpleName();
    private int speed;
    private final GameBitmap bitmap;
    private float x;
    private float y;

    private Bullet(float x, float y, int speed){
        this.x = x;
        this.y = y;
        this.speed = -speed;

        Log.d(TAG, "Loading BUllet Bitmap");
        this.bitmap = new GameBitmap(R.mipmap.laser_1);
    }
    @Override
    public void update() {
        MainGame game = MainGame.get();
        y += speed * game.frameTime;

        if(y < 0){
            game.remove(this);
            //doRecycle();
        }
    }
    public static Bullet get(float x, float y, int speed){
        MainGame game = MainGame.get();
        Bullet bullet  = (Bullet)game.get(Bullet.class);
        if(bullet == null){
            bullet = new Bullet(x,y,speed);
        }
        else{
            bullet.init(x,y,speed);
        }
        return bullet;
    }
    @Override
    public void draw(Canvas canvas) {
        bitmap.draw(canvas, x, y);
    }

    @Override
    public void getBoundingRect(RectF rect) {
        bitmap.getBoundingRect(x,y, rect);
    }

    private void init(float x, float y, int speed) {
        this.x = x;
        this.y = y;
        if(speed > 0) {
            speed = -speed;
        }
        this.speed = speed;
    }

    @Override
    public void recyle() {
        // 재활용통에 들어갈 시점에 할 일 ..
        // 현 시점에는 없어요
    }
}
