package kr.ac.kpu.s2015182034.termproject.framework;


import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import kr.ac.kpu.s2015182034.termproject.R;
import kr.ac.kpu.s2015182034.termproject.game.Barrier;
import kr.ac.kpu.s2015182034.termproject.game.Blinker;
import kr.ac.kpu.s2015182034.termproject.game.Coin;
import kr.ac.kpu.s2015182034.termproject.game.Effect;
import kr.ac.kpu.s2015182034.termproject.game.GameOverBoard;
import kr.ac.kpu.s2015182034.termproject.game.Parent.Car;
import kr.ac.kpu.s2015182034.termproject.game.Parent.Item;
import kr.ac.kpu.s2015182034.termproject.game.Player;
import kr.ac.kpu.s2015182034.termproject.game.Score;
import kr.ac.kpu.s2015182034.termproject.game.Time;
import kr.ac.kpu.s2015182034.termproject.game.Tracer;
import kr.ac.kpu.s2015182034.termproject.game.VerticalScrollBackground;
import kr.ac.kpu.s2015182034.termproject.game.WaterObject;
import kr.ac.kpu.s2015182034.termproject.game.WoodPlatform;
import kr.ac.kpu.s2015182034.termproject.ui.view.GameView;
import kr.ac.kpu.s2015182034.termproject.utils.CollisionHelper;

public class MainGame {
    private static final String TAG = MainGame.class.getSimpleName();
    public static float remainBlinkTime = 0.0f;

    private boolean initialized = false;

    public enum Layer{
        map, water, car, tracer, platform, item,  player, effect, ui, COUNT
    }
    private ArrayList<ArrayList<GameObject>> layers;
    private Player player;
    private Score score;
    private Time time;
    VerticalScrollBackground bg;

    int obstacleCreatePos = 0;
    int viewW, viewH;

    private static HashMap<Class, ArrayList<GameObject>> reclycleBin = new HashMap<>();

    // singleton 패턴
    private static MainGame instance;
    public static MainGame get(){
        if(instance == null){
            instance = new MainGame();
        }
        return instance;
    }
    public float frameTime;
    private float lastEffectCreateTime = 0.0f;
    public GameObject get(Class className){
        ArrayList<GameObject> array = reclycleBin.get(className);
        if(array == null) return null;
        if(array.isEmpty()) return null;
        return array.remove(0);
    }

    public void recycle(GameObject object){
        Class className = object.getClass();
        ArrayList<GameObject> array = reclycleBin.get(className);
        if(array == null){
            // 첫 번째 호출인 경우.
            array = new ArrayList<>();
            reclycleBin.put(className, array);
        }
        array.add(object);
    }

    String[] CAR_TYPE = new String[]{
            "Car", "Ambulance", "PoliceCar", "Excavator", "Truck", "Bus"
    };
    int carSizeH = 170;
    public boolean InitResources() {
        if(this.initialized){
            return false;
        }
        initLayers(Layer.COUNT.ordinal());

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

//        add(Layer.platform, WoodPlatform.get("LongWood", 300, viewH -2100 - 40));
//        add(Layer.platform, WoodPlatform.get("ShortWood", 300, viewH -2250- 40));
//        add(Layer.platform, WoodPlatform.get("LongWood", 300, viewH - 2400- 40));
//        add(Layer.water, WaterObject.get(viewH - 2100 - 192));
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
        this.initialized = true;
        return true;
    }

    private void initLayers(int layerCount) {
        layers = new ArrayList<>();
        for (int i = 0; i < layerCount; i++) {
            layers.add(new ArrayList<>());
        }
    }
    public void update() {
        for (ArrayList<GameObject> objects: layers) {
            for (GameObject o : objects) {
                o.update();
            }
        }
        for (ArrayList<GameObject> objects: layers) {
            for (GameObject o : objects) {
                if(false == (o instanceof FiniteObject)){
                    continue;
                }
                if(((FiniteObject) o).IsHaveToDelete()){
                    Log.d(TAG, "IsHaveToDelete - Remove" );
                    remove(o);
                }
            }
        }

        if( false == player.IsOnBarrier())
        {
            ArrayList<GameObject> cars = layers.get(Layer.car.ordinal());
            for (GameObject o1 : cars) {
                Car car = (Car) o1;
                if (CollisionHelper.collides((BoxCollidable) o1, player)) {
                    Log.d(TAG, "Collision!! Enemy - player" );
                    // to do
                    if(lastEffectCreateTime <= 0.0f){
                        add(Layer.effect, new Effect(Effect.EffectType.HitEffect, player.GetXPos(), player.GetYPos()));
                        lastEffectCreateTime = 1.0f;
                    }
                }
            }
        }
        ArrayList<GameObject> tracers = layers.get(Layer.tracer.ordinal());
        for (GameObject o1 : tracers) {
            Tracer tracer = (Tracer) o1;
            if (CollisionHelper.collides((BoxCollidable) o1, player)) {
                Log.d(TAG, "Collision!! Tracer - player" );
                // to do
                if(lastEffectCreateTime <= 0.0f) {
                    add(Layer.effect, new Effect(Effect.EffectType.HitEffect, player.GetXPos(), player.GetYPos()));
                    lastEffectCreateTime = 1.0f;
                }
            }
        }
        ArrayList<GameObject> platforms = layers.get(Layer.platform.ordinal());
        for(GameObject o1 : platforms){
            WoodPlatform platform = (WoodPlatform) o1;
            if(CollisionHelper.collidesIn((BoxCollidable)o1, player)){
                Log.d(TAG, "Collision!! platform - player" );
                platform.ConnectPlayer(player);
                player.SetOnPlatform(true);
                break;
            }
            else{
                player.SetOnPlatform(false);
                platform.ConnectPlayer(null);
            }
        }
        ArrayList<GameObject> waters = layers.get(Layer.water.ordinal());
        for(GameObject o1 : waters){
            WaterObject water = (WaterObject) o1;
            if(CollisionHelper.collidesIn((BoxCollidable)o1, player)){
                // 만약 플레이어가 플랫폼에 타 있지 않다면...
                // 충돌처리를 하도록..
                if(false == player.IsOnPlatform()){
                    Log.d(TAG, "Collision!! WaterObject - player" );
                    if(lastEffectCreateTime <= 0.0f) {
                        add(Layer.effect, new Effect(Effect.EffectType.WaterEffect, player.GetXPos(), player.GetYPos()));
                        lastEffectCreateTime = 1.0f;
                    }
                    break;
                }
            }
        }
        ArrayList<GameObject> items = layers.get(Layer.item.ordinal());
        for(GameObject o1 : items){
            Item item = (Item) o1;
            if(CollisionHelper.collides((BoxCollidable)o1, player)){
                String typeName = item.GetTypeName();
                if("Blinker" == typeName) {
                    Log.d(TAG, "Collision!! Blinker - player" );
                    remove(o1);
                    StopCars();
                }
                else if("Coin" == typeName) {
                    Log.d(TAG, "Collision!! Coin - player" );
                    remove(o1);
                    score.addScore(100);
                }
                else if("Barrier" == typeName) {
                    Log.d(TAG, "Collision!! Barrier - player" );
                    Barrier barrier = (Barrier)o1;
                    if(false == barrier.IsOnUse()){
                        float x = player.GetXPos();
                        float y = player.GetYPos();
                        player.SetBarrier(Barrier.LIFE_TIME);
                        barrier.SetPosition(x, y);
                        barrier.ConnectPlayer(player);
                        barrier.setUse(true);
                    }
                }
            }
        }

        lastEffectCreateTime -= frameTime;
        if(lastEffectCreateTime < 0.0f){
            lastEffectCreateTime = 0.0f;
        }
    }

    public void draw(Canvas canvas) {
        //if(this.initialized == false) return;

        for(ArrayList<GameObject> objects : layers){
            for (GameObject o : objects) {
                o.draw(canvas);
            }
        }
    }

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

    public void add(Layer layer, GameObject gameObject){
        GameView.view.post(new Runnable(){
            @Override
            public void run(){
                ArrayList<GameObject> objects = layers.get(layer.ordinal());
                objects.add(gameObject);
            }
        });
    }

    public void remove(GameObject gameObject) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (ArrayList<GameObject> objects : layers) {
                    boolean removed = objects.remove(gameObject);
                    if (removed) {
                        if (gameObject instanceof Recyclable) {
                            ((Recyclable) gameObject).recyle();
                            recycle(gameObject);
                        }
                        //Log.d(TAG, "Removed: " + gameObject);
                        break;
                    }
                }
            }
        };
        GameView.view.post(runnable);
    };

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

        if(CheckHaveToCreateObstacle()){
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

    private boolean CheckHaveToCreateObstacle() {
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
