package kr.ac.kpu.s2015182034.termproject.game;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.Random;

import kr.ac.kpu.s2015182034.termproject.R;
import kr.ac.kpu.s2015182034.termproject.animation.AnimationBitmap;
import kr.ac.kpu.s2015182034.termproject.animation.GameBitmap;
import kr.ac.kpu.s2015182034.termproject.framework.BoxCollidable;
import kr.ac.kpu.s2015182034.termproject.framework.GameObject;
import kr.ac.kpu.s2015182034.termproject.framework.MainGame;
import kr.ac.kpu.s2015182034.termproject.framework.Recyclable;
import kr.ac.kpu.s2015182034.termproject.game.Parent.Car;
import kr.ac.kpu.s2015182034.termproject.ui.view.GameView;

public class WaterObject implements GameObject, BoxCollidable, Recyclable {
    protected float x, y;   // 위치

    protected int sx, sy; // 크기

    protected boolean isLeft;
    protected GameBitmap bitmap;

    protected WaterObject(float y) {
        this.x = 0;
        //this.x = GameView.view.getWidth() * 0.5f;
        this.y = y;
        if (bitmap == null) {
            Resources res = GameView.view.getResources();
            bitmap = new GameBitmap(R.mipmap.water);
        }
        this.sx = GameView.view.getWidth();
        this.sy = bitmap.getHeight();
    }

    private void init(float y) {
        this.x = 0;
        //this.x = GameView.view.getWidth() * 0.5f;
        this.y = y;
        if (bitmap == null) {
            Resources res = GameView.view.getResources();
            bitmap = new GameBitmap(R.mipmap.water);
        }
        this.sx = GameView.view.getWidth();
        this.sy = bitmap.getHeight();
    }
    public static WaterObject get(float y){
        MainGame game = MainGame.get();
        WaterObject waterObject  = (WaterObject)game.get(WaterObject.class);
        if(waterObject == null){
            waterObject = new WaterObject(y);
        }
        else{
            waterObject.init(y);
        }
        return waterObject;
    }

    @Override
    public void getBoundingRect(RectF rect) {
        bitmap.getStretchedBoundingRect(x,y,rect);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        bitmap.drawStretched(canvas, x, y);
    }


    @Override
    public void movePosition(float xMoved, float yMoved) {
        this.x += xMoved;
        this.y += yMoved;
    }

    @Override
    public void recyle() {

    }
}
