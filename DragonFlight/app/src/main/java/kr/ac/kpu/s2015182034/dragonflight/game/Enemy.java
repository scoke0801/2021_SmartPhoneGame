package kr.ac.kpu.s2015182034.dragonflight.game;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.kpu.s2015182034.dragonflight.R;
import kr.ac.kpu.s2015182034.dragonflight.UI.View.GameView;
import kr.ac.kpu.s2015182034.dragonflight.framework.AnimationGameBitmap;
import kr.ac.kpu.s2015182034.dragonflight.framework.BoxCollidable;
import kr.ac.kpu.s2015182034.dragonflight.framework.GameObject;

public class Enemy implements GameObject, BoxCollidable {
    private static final float FRMAES_PRE_SECOND = 8.0f;
    private final int speed;
    private final AnimationGameBitmap bitmap;
    private float x;
    private float y;

    public Enemy(float x, float y, int speed){
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.bitmap = new AnimationGameBitmap(R.mipmap.enemy_01,
                FRMAES_PRE_SECOND, 0);
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
}
