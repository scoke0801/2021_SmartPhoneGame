package kr.ac.kpu.s2015182034.dragonflight.game;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.kpu.s2015182034.dragonflight.R;
import kr.ac.kpu.s2015182034.dragonflight.UI.View.GameView;
import kr.ac.kpu.s2015182034.dragonflight.framework.AnimationGameBitmap;
import kr.ac.kpu.s2015182034.dragonflight.framework.BoxCollidable;
import kr.ac.kpu.s2015182034.dragonflight.framework.GameObject;
import kr.ac.kpu.s2015182034.dragonflight.framework.Recyclable;

public class Enemy implements GameObject, BoxCollidable, Recyclable {
    private static final float FRMAES_PRE_SECOND = 20.0f;
    private static final int[] RESOURCE_IDS = {
            R.mipmap.enemy_01, R.mipmap.enemy_02, R.mipmap.enemy_03, R.mipmap.enemy_04, R.mipmap.enemy_05,
            R.mipmap.enemy_06, R.mipmap.enemy_07, R.mipmap.enemy_08, R.mipmap.enemy_09, R.mipmap.enemy_10,
            R.mipmap.enemy_11, R.mipmap.enemy_12, R.mipmap.enemy_13, R.mipmap.enemy_14, R.mipmap.enemy_15,
            R.mipmap.enemy_16, R.mipmap.enemy_17, R.mipmap.enemy_18, R.mipmap.enemy_19, R.mipmap.enemy_20,
    };
    private int speed;
    private AnimationGameBitmap bitmap;
    private float x;
    private float y;
    private int level;
    private Enemy(){
    }
    public static Enemy get(int level, float x, float y, int speed){
        MainGame game = MainGame.get();
        Enemy enemy  = (Enemy)game.get(Enemy.class);
        if(speed < 0) speed = -speed;
        if(enemy == null){
            enemy = new Enemy();
        }
        enemy.init(level, x,y,speed);

        return enemy;
    }

    @Override
    public void update() {
        MainGame game = MainGame.get();
        y += speed * game.frameTime;

        if(y > GameView.view.getHeight()){
            game.remove(this);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        bitmap.draw(canvas, x, y);
    }

    @Override
    public void getBoundingRect(RectF rect) {
        bitmap.getBoundingRect(x,y, rect);
    }

    private void init(int level, float x, float y, int speed) {
        this.x = x;
        this.y = y;
        if(speed < 0){
            speed = -speed;
        }
        this.speed = speed;
        this.level = level;
        int resId =  RESOURCE_IDS[level - 1];
        this.bitmap = new AnimationGameBitmap(resId,
                FRMAES_PRE_SECOND, 0);
    }
    @Override
    public void recyle() {

    }
}
