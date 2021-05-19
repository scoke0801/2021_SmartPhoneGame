package kr.ac.kpu.s2015182034.termproject.game;

import android.content.res.Resources;
import android.graphics.Canvas;

import kr.ac.kpu.s2015182034.termproject.R;
import kr.ac.kpu.s2015182034.termproject.animation.AnimationBitmap;
import kr.ac.kpu.s2015182034.termproject.framework.GameObject;
import kr.ac.kpu.s2015182034.termproject.framework.MainGame;
import kr.ac.kpu.s2015182034.termproject.ui.view.GameView;

public class Effect implements GameObject {

    public enum EffectType{
        WaterEffect
    }

    float lifeTime = 1.0f;
    float elapsedTime = 0.0f;
    protected float x, y;   // 위치
    protected AnimationBitmap bitmap;
    private static float FRAME_RATE = 7.0f; // 1초당 7.0장의 속도로 애니메이션을 수행하도록
    public Effect(EffectType type, float x, float y) {
        int resId = -1;
        int frameCount = -1;
        if(EffectType.WaterEffect == type){
            resId = R.mipmap.water_plop;
            frameCount = 7;
        }

        if (bitmap == null) {
            bitmap = new AnimationBitmap(resId, FRAME_RATE, frameCount);
        }
        this.x = x;
        this.y = y;
    }

    @Override
    public void update() {
        if (elapsedTime > lifeTime){
            MainGame.get().remove(this);
        }
        elapsedTime += MainGame.get().frameTime;
    }

    @Override
    public void draw(Canvas canvas) {
        bitmap.draw(canvas, x, y);
    }


    @Override
    public void movePosition(float xMoved, float yMoved) {

    }
}
