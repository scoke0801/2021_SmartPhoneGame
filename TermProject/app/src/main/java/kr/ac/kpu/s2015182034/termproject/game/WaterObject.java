package kr.ac.kpu.s2015182034.termproject.game;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.kpu.s2015182034.termproject.animation.AnimationBitmap;
import kr.ac.kpu.s2015182034.termproject.framework.BoxCollidable;
import kr.ac.kpu.s2015182034.termproject.framework.GameObject;
import kr.ac.kpu.s2015182034.termproject.framework.Recyclable;

public class WaterObject implements GameObject, BoxCollidable, Recyclable {
    protected float x, y;   // 위치

    protected int sx, sy; // 크기

    protected boolean isLeft;
    protected AnimationBitmap bitmap;


    @Override
    public void getBoundingRect(RectF rect) {
        
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void fixCollision() {

    }

    @Override
    public void movePosition(float xMoved, float yMoved) {

    }

    @Override
    public void recyle() {

    }
}
