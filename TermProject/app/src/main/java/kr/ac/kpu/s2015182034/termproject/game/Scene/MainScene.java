package kr.ac.kpu.s2015182034.termproject.game.Scene;


import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Random;

import kr.ac.kpu.s2015182034.termproject.R;
import kr.ac.kpu.s2015182034.termproject.framework.GameObject;
import kr.ac.kpu.s2015182034.termproject.framework.MainGame;
import kr.ac.kpu.s2015182034.termproject.game.Barrier;
import kr.ac.kpu.s2015182034.termproject.game.Blinker;
import kr.ac.kpu.s2015182034.termproject.game.Coin;
import kr.ac.kpu.s2015182034.termproject.game.Parent.Car;
import kr.ac.kpu.s2015182034.termproject.game.Player;
import kr.ac.kpu.s2015182034.termproject.game.Score;
import kr.ac.kpu.s2015182034.termproject.game.Time;
import kr.ac.kpu.s2015182034.termproject.game.Tracer;
import kr.ac.kpu.s2015182034.termproject.game.VerticalScrollBackground;
import kr.ac.kpu.s2015182034.termproject.game.WaterObject;
import kr.ac.kpu.s2015182034.termproject.game.WoodPlatform;
import kr.ac.kpu.s2015182034.termproject.ui.view.GameView;

public class MainScene extends Scene {
    private Player player;
    private Score score;
    private Time time;
    VerticalScrollBackground bg;

    int obstacleCreatePos = 0;
    int viewW, viewH;
    String[] CAR_TYPE = new String[]{
            "Car", "Ambulance", "PoliceCar", "Excavator", "Truck", "Bus"
    };
    int carSizeH = 170;

    public enum Layer{
        map, water, car, tracer, platform, item,  player, effect, ui, COUNT
    }

    public static kr.ac.kpu.s2015182034.termproject.game.Scene.MainScene scene;
    public void add(Layer layer, GameObject obj) {
        add(layer.ordinal(), obj);
    }
    public ArrayList<GameObject> objectsAt(Layer layer) {
        return objectsAt(layer.ordinal());
    }

    @Override
    public void start() {
        scene = this;
        super.start();
        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();

        initLayers(Layer.COUNT.ordinal());

        //add(Layer.controller, new StageMap("stage_01.txt"));

        viewW = GameView.view.getWidth();
        viewH = GameView.view.getHeight();


        player = new Player(viewW/ 2, viewH - 300, 0,0);
        add(Layer.player, player);

        int tracerPos = 120;
        while(tracerPos < viewW){
            add(Layer.tracer, new Tracer(tracerPos, viewH));
            tracerPos += viewW / 5;
        }

        Random r = new Random();
        // test codes
        for(int i = 0; i < 10; i += 2){
            Car car = Car.get(CAR_TYPE[r.nextInt(5)],0,viewH - 300 + -carSizeH * (i+1), false);
            add(Layer.car, car);
        }
        for(int i = 1; i < 10; i += 2){
            Car car = Car.get(CAR_TYPE[r.nextInt(5)],viewW, viewH - 300 + -carSizeH * (i+1), true);
            add(Layer.car, car);
        }
        obstacleCreatePos = -2100 + viewH;

        int margin =  (int)(40 * GameView.MULTIPLIER);
        score = new Score(viewW - margin, margin);
        score.setScore(0);
        add(Layer.ui, score);

        margin = (int)(40 * GameView.MULTIPLIER);
        time = new Time( margin, margin);
        add(Layer.ui, time);

        this.bg = new VerticalScrollBackground(R.mipmap.map_1, 0);
        add(Layer.map, this.bg );

        int coinSize = 180;
        for(int i = 0 ; i < 10; ++i){
            add(Layer.item, Coin.get("Coin", r.nextInt(viewW - 50) + 25, coinSize * i));
        }
        int barrierGap = 1800;
        for(int i = 0 ; i < 2; ++i){
            add(Layer.item, Barrier.get("Barrier", r.nextInt(viewW - 50) + 25, 800 - barrierGap * i));
        }
        int blinkerGap = 2000;
        for(int i = 0 ; i < 2; ++i){
            add(Layer.item, Blinker.get("Blinker", r.nextInt(viewW - 50) + 25, 700 - blinkerGap * i));
        }

        //add(Layer.ui, new GameOverBoard(w / 2, 0));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch(action){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                player.moveTo(event.getX(), event.getY());
                return true;
            case MotionEvent.ACTION_UP:
                return true;
            default:
                break;
        }
        return false;
    }


    public void ScrollMap(float xMoved, float yMoved) {
        //car, platform, item
        ArrayList<GameObject> carObjs = layers.get(Layer.car.ordinal());
        for (GameObject o : carObjs) {
            o.movePosition(xMoved, yMoved);
        }
        ArrayList<GameObject> platformObjs = layers.get(Layer.platform.ordinal());
        for (GameObject o : platformObjs) {
            o.movePosition(xMoved, yMoved);
        }
        ArrayList<GameObject> itemObjs = layers.get(Layer.item.ordinal());
        for (GameObject o : itemObjs) {
            o.movePosition(xMoved, yMoved);
        }
        ArrayList<GameObject> waterObjs = layers.get(Layer.water.ordinal());
        for (GameObject o : waterObjs) {
            o.movePosition(xMoved, yMoved);
        }

        ArrayList<GameObject> tracers = layers.get(Layer.tracer.ordinal());
        for (GameObject o : tracers) {
            o.movePosition(xMoved, yMoved);
        }
        bg.Scroll(xMoved, yMoved);
        obstacleCreatePos += yMoved;

        if (CheckHaveToCreateObstacle()) {
            CreateObstacles();
        }
    }

    // 아이템 - Blinker을 획득한 경우, 특정 시간 만큼 장애물(차 종류)들의 이동을 멈춘다
    public void StopCars(){
        ArrayList<GameObject> cars = layers.get(Layer.car.ordinal());
        for(GameObject o1 : cars){
            Car car = (Car) o1;
            car.Stop(true, 3.0f);
        }
    }

    public boolean CheckHaveToDelete(float y){
        float playerY = player.GetYPos();
        return ( y - 600.0f >= playerY);
    }

    public boolean CheckHaveToCreateObstacle() {
        float y = player.GetYPos();

        return obstacleCreatePos >= y - viewH ;
    }

    public void CreateObstacles() {
        Random r = new Random();
        int res = r.nextInt(6); // 0 ~ 5
        if (res == 0 || res == 1 || res == 2) {
            // CreateWater
            add(Layer.platform, WoodPlatform.get("LongWood", 300, obstacleCreatePos - 40));
            add(Layer.platform, WoodPlatform.get("ShortWood", 300, obstacleCreatePos - 150 - 40));
            add(Layer.platform, WoodPlatform.get("LongWood", 300, obstacleCreatePos - 300 - 40));
            add(Layer.water, WaterObject.get(obstacleCreatePos - 192));

            obstacleCreatePos += -370 - 192 + 75;
        }
        else {
            // CreateCars
            for (int i = 0; i < 10; i += 2) {
                Car car = Car.get(CAR_TYPE[r.nextInt(5)], 0, obstacleCreatePos + -carSizeH * (i + 1), false);
                add(Layer.car, car);
            }
            for (int i = 1; i < 10; i += 2) {
                Car car = Car.get(CAR_TYPE[r.nextInt(5)], viewW, obstacleCreatePos + -carSizeH * (i + 1), true);
                add(Layer.car, car);
            }
            obstacleCreatePos += -2100;
        }
        //obstacleCreatePos  += -2100 + viewH;
    }
}
