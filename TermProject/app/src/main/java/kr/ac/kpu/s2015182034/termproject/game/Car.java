package kr.ac.kpu.s2015182034.termproject.game;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.Random;

import kr.ac.kpu.s2015182034.termproject.R;
import kr.ac.kpu.s2015182034.termproject.animation.AnimationBitmap;
import kr.ac.kpu.s2015182034.termproject.framework.BoxCollidable;
import kr.ac.kpu.s2015182034.termproject.framework.CalculateFunctions;
import kr.ac.kpu.s2015182034.termproject.framework.GameObject;
import kr.ac.kpu.s2015182034.termproject.framework.MainGame;
import kr.ac.kpu.s2015182034.termproject.framework.Recyclable;
import kr.ac.kpu.s2015182034.termproject.ui.view.GameView;

public class Car implements GameObject, BoxCollidable, Recyclable {
    protected float x, y;   // 위치

    protected int sx, sy; // 크기

    protected boolean isLeft;
    protected AnimationBitmap bitmap;

    private static float FRAME_RATE = 8.5f; // 1초당 8.5장의 속도로 애니메이션을 수행하도록

    protected float speed;
    protected boolean isOnMove;

    protected float spriteIdx = 0;

    private String[] CAR_TYPE = new String[]{
            "Car", "Ambulance", "PoliceCar", "Excavator", "Truck"
    };
    private static int[] CAR_RESOURCES = new int[]{
            R.mipmap.car_digger_left, R.mipmap.car_digger_right
    };

    protected Car(String type, float x, float y, boolean isLeft) {
        this.x = x;
        this.y = y;
        this.isOnMove = false;
        Random r = new Random();
        this.speed = r.nextInt(200) + 100;
        this.isLeft = isLeft;
        if (bitmap == null) {
            Resources res = GameView.view.getResources();
            int resId = -1;
            int index = -1;
            for(int i = 0; i < CAR_TYPE.length; ++i){
                if (CAR_TYPE[i] == type){
                    index = i;
                    break;
                }
            }
            resId = CAR_RESOURCES[index];
            bitmap = new AnimationBitmap(resId, FRAME_RATE, 1);
        }
        this.sx = bitmap.getWidth();
        this.sy = bitmap.getHeight();
    }



    protected Car() {

    }

    private void init(String type, float x, float y, boolean isLeft) {
        this.x = x;
        this.y = y;
        if(speed < 0){
            speed = -speed;
        }
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
    public static Car get(String type, float x, float y, boolean isLef){
        MainGame game = MainGame.get();
        Car car  = (Car)game.get(Car.class);
        if(car == null){
            car = new Car(type, x,y,isLef);
        }
        else{
            car.init(type, x,y,isLef);
        }
        return car;
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

    public void fixCollision(){

    }

    @Override
    public void getBoundingRect(RectF rect) {
        bitmap.getBoundingRect(x,y,rect);
    }

    @Override
    public void recyle() {
        // To do
    }
}
