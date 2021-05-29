package kr.ac.kpu.s2015182034.termproject.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.Random;

import kr.ac.kpu.s2015182034.termproject.R;
import kr.ac.kpu.s2015182034.termproject.animation.GameBitmap;
import kr.ac.kpu.s2015182034.termproject.framework.BoxCollidable;
import kr.ac.kpu.s2015182034.termproject.framework.GameObject;
import kr.ac.kpu.s2015182034.termproject.framework.MainGame;

public class Tracer implements GameObject, BoxCollidable {
    private static final String TAG = Tracer.class.getSimpleName();
    protected float x, y;   // 위치

    protected GameBitmap bitmap;

    protected float remainStopTime = 0.0f;

    protected float speed;
    protected boolean isOnStop = false;

    public Tracer(float x, float y) {
        this.x = x;
        this.y = y;
        this.speed = -30.0f;
        if (bitmap == null) {
            bitmap = new GameBitmap(R.mipmap.tracer);
        }
    }

    public void update() {
        if(isOnStop){
            remainStopTime -= MainGame.get().frameTime;
            if(remainStopTime <= 0.0f){
                isOnStop = false;
            }
        }
        else {
            float dy = MainGame.get().frameTime * this.speed;
            this.y += dy;
        }
    }

    public void draw(Canvas canvas) {
        bitmap.draw(canvas, x, y);
        //bitmap.drawAABB(canvas, x, y);
    }

    @Override
    public void getBoundingRect(RectF rect) {
        bitmap.getBoundingRect(x,y,rect);
    }

    public void Stop(boolean stopInfo, float stopTime){
        this.isOnStop = stopInfo;
        this.remainStopTime = stopTime;
    }

    @Override
    public void movePosition(float xMoved, float yMoved) {
        this.x += xMoved;
        this.y += yMoved;
    }
}
