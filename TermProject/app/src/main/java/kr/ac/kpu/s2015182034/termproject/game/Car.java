package kr.ac.kpu.s2015182034.termproject.game;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.Random;

import kr.ac.kpu.s2015182034.termproject.R;
import kr.ac.kpu.s2015182034.termproject.animation.AnimationBitmap;
import kr.ac.kpu.s2015182034.termproject.framework.CalculateFunctions;
import kr.ac.kpu.s2015182034.termproject.framework.GameObject;
import kr.ac.kpu.s2015182034.termproject.framework.MainGame;
import kr.ac.kpu.s2015182034.termproject.ui.view.GameView;

public class Car implements GameObject {
    private float x, y;   // 위치

    private int sx, sy; // 크기

    private boolean isLeft;
    private AnimationBitmap bitmap;

    private static float FRAME_RATE = 8.5f; // 1초당 8.5장의 속도로 애니메이션을 수행하도록

    private float speed;
    private boolean isOnMove;

    private float spriteIdx = 0;

    public Car(float x, float y, boolean isLeft) {
        this.x = x;
        this.y = y;
        this.isOnMove = false;
        Random r = new Random();
        this.speed = r.nextInt(200) + 100;
        this.isLeft = isLeft;
        if(bitmap == null) {
            Resources res = GameView.view.getResources();
            if(isLeft) {
                bitmap = new AnimationBitmap(R.mipmap.car_digger_left, FRAME_RATE, 1);
            }
            else{
                bitmap = new AnimationBitmap(R.mipmap.car_digger_right, FRAME_RATE, 1);
            }
            this.sx = bitmap.getWidth();
            this.sy = bitmap.getHeight();
        }
    }

    public void update() {
        if (isOnMove){
        }
        else{
        }
        float dx = MainGame.get().frameTime * this.speed;
        if(isLeft){
            dx = -dx;
        }
        x += dx;

        int w = GameView.view.getWidth();
        if(isLeft){
            if( x < 0) {
                this.x = w;
            }
        }
        else{
            if(x > w){
                this.x = 0;
            }
        }
    }

    public void draw(Canvas canvas) {
        bitmap.draw(canvas, x, y);
        bitmap.drawAABB(canvas, x, y);
    }

    public RectF getAABB(){
        return bitmap.getAABB(x,y);
    }
    public void fixCollision(){

    }
}
