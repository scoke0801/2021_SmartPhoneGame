package kr.ac.kpu.s2015182034.termproject.game;

import android.graphics.Canvas;

import kr.ac.kpu.s2015182034.termproject.R;
import kr.ac.kpu.s2015182034.termproject.animation.AnimationBitmap;
import kr.ac.kpu.s2015182034.termproject.framework.BaseGame;
import kr.ac.kpu.s2015182034.termproject.framework.FiniteObject;
import kr.ac.kpu.s2015182034.termproject.framework.GameObject;
import kr.ac.kpu.s2015182034.termproject.framework.MainGame;

public class Effect implements GameObject, FiniteObject {


    public enum EffectType{
        WaterEffect,
        HitEffect
    }

    float lifeTime = 0.9f;
    float elapsedTime = 0.0f;
    boolean haveToDelete = false;
    protected float x, y;   // 위치
    protected AnimationBitmap bitmap;
    private float frameRate = 7.0f; // 1초당 7.0장의 속도로 애니메이션을 수행하도록
    public Effect(EffectType type, float x, float y)  {
        int resId = -1;
        int frameCount = -1;
        if(EffectType.WaterEffect == type){
            resId = R.mipmap.water_plop;
            frameCount = 7;
        }
        else if(EffectType.HitEffect == type){
            resId = R.mipmap.hit_effect;
            frameCount = 5;
            frameRate = 5.0f;
        }
        if (bitmap == null) {
            bitmap = new AnimationBitmap(resId, frameRate, frameCount);
        }
        this.x = x;
        this.y = y;
    }

    @Override
    public void update() {
        if (elapsedTime > lifeTime){
            haveToDelete = true;
        }
        elapsedTime += BaseGame.get().frameTime;
    }

    @Override
    public void draw(Canvas canvas) {
        bitmap.draw(canvas, x, y);
    }


    @Override
    public void movePosition(float xMoved, float yMoved) {

    }


    @Override
    public boolean IsHaveToDelete() {
        return haveToDelete;
    }
}
