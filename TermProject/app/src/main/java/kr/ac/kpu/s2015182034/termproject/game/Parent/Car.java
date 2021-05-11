package kr.ac.kpu.s2015182034.termproject.game.Parent;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import java.util.Random;

import kr.ac.kpu.s2015182034.termproject.R;
import kr.ac.kpu.s2015182034.termproject.animation.AnimationBitmap;
import kr.ac.kpu.s2015182034.termproject.framework.BoxCollidable;
import kr.ac.kpu.s2015182034.termproject.framework.GameObject;
import kr.ac.kpu.s2015182034.termproject.framework.MainGame;
import kr.ac.kpu.s2015182034.termproject.framework.Recyclable;
import kr.ac.kpu.s2015182034.termproject.ui.view.GameView;

public class Car implements GameObject, BoxCollidable, Recyclable {
    private static final String TAG = Car.class.getSimpleName();
    protected float x, y;   // 위치

    protected int sx, sy; // 크기

    protected boolean isLeft;
    protected AnimationBitmap bitmap;

    private static float FRAME_RATE = 8.5f; // 1초당 8.5장의 속도로 애니메이션을 수행하도록

    protected float remainStopTime = 0.0f;

    protected float speed;
    protected boolean isOnMove;

    protected float spriteIdx = 0;

    protected boolean isOnStop = false;
    private String[] CAR_TYPE = new String[]{
            "Car", "Ambulance", "PoliceCar", "Excavator", "Truck", "Bus"
    };
    private int[] CAR_SPEEDS_RANDOM = { 100, 300, 300, 50, 100, 150  };
    private int[] CAR_SPEEDS_MIN = { 50, 100, 150, 50, 50, 80  };
    private static int[] CAR_RESOURCES = new int[]{
            R.mipmap.car_right,         R.mipmap.car_left,
            R.mipmap.ambulance_right,  R.mipmap.ambulance_left,
            R.mipmap.police_car_right,R.mipmap.police_car_left,
            R.mipmap.excavator_right, R.mipmap.excavator_left,
            R.mipmap.truck_right, R.mipmap.truck_left,
            R.mipmap.bus_right, R.mipmap.bus_left
    };

    protected Car(String type, float x, float y, boolean isLeft) {
        this.x = x;
        this.y = y;
        this.isOnMove = false;
        Random r = new Random();
        this.isLeft = isLeft;
        if (bitmap == null) {
            Resources res = GameView.view.getResources();
            int resId = -1;
            int index = -1;
            for(int i = 0; i < CAR_TYPE.length; ++i){
                if (CAR_TYPE[i] == type){
                    index = i * 2;
                    this.speed = r.nextInt(CAR_SPEEDS_RANDOM[i]) + CAR_SPEEDS_MIN[i];
                    break;
                }
            }
            if(isLeft) {
                index += 1;
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
        this.isOnMove = false;
        Random r = new Random();
        //this.speed = r.nextInt(200) + 100;
        this.isLeft = isLeft;
        this.isLeft = isLeft;
        if (bitmap == null) {
            Resources res = GameView.view.getResources();
            int resId = -1;
            int index = -1;
            for(int i = 0; i < CAR_TYPE.length; ++i){
                if (CAR_TYPE[i] == type){
                    index = i * 2;
                    this.speed = r.nextInt(CAR_SPEEDS_RANDOM[i]) + CAR_SPEEDS_MIN[i];
                    break;
                }
            }
            if(isLeft) {
                index += 1;
            }
            resId = CAR_RESOURCES[index];
            bitmap = new AnimationBitmap(resId, FRAME_RATE, 1);
        }
        this.sx = bitmap.getWidth();
        this.sy = bitmap.getHeight();
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
        if(isOnStop){
            remainStopTime -= MainGame.get().frameTime;
            if(remainStopTime <= 0.0f){
                isOnStop = false;
            }
        }
        else {
            float dx = MainGame.get().frameTime * this.speed;
            if (isLeft) {
                dx = -dx;
            }
            x += dx;

            int w = GameView.view.getWidth();
            if (isLeft) {
                if (x < 0) {
                    this.x = w;
                }
            } else {
                if (x > w) {
                    this.x = 0;
                }
            }
        }
    }

    public void draw(Canvas canvas) {
        bitmap.draw(canvas, x, y);
        //bitmap.drawAABB(canvas, x, y);
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
    public void Stop(boolean stopInfo, float stopTime){
        this.isOnStop = stopInfo;
        this.remainStopTime = stopTime;
    }
}
