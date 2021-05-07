package kr.ac.kpu.s2015182034.termproject.framework;


import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.kpu.s2015182034.termproject.R;
import kr.ac.kpu.s2015182034.termproject.game.Barrier;
import kr.ac.kpu.s2015182034.termproject.game.Blinker;
import kr.ac.kpu.s2015182034.termproject.game.Coin;
import kr.ac.kpu.s2015182034.termproject.game.Parent.Car;
import kr.ac.kpu.s2015182034.termproject.game.Player;
import kr.ac.kpu.s2015182034.termproject.game.Score;
import kr.ac.kpu.s2015182034.termproject.game.VerticalScrollBackground;
import kr.ac.kpu.s2015182034.termproject.ui.view.GameView;
import kr.ac.kpu.s2015182034.termproject.utils.CollisionHelper;

public class MainGame {
    private static final String TAG = MainGame.class.getSimpleName();
    public static float remainBlinkTime = 0.0f;

    private boolean initialized = false;

    public enum Layer{
        map, car, bullet, item, player, ui, controller, COUNT
    }
    private ArrayList<ArrayList<GameObject>> layers;
    private Player player;
    private Score score;

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
    public boolean InitResources() {
        if(this.initialized){
            return false;
        }
        initLayers(Layer.COUNT.ordinal());

        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();

        // test codes
        int carSizeH = 170;
        for(int i = 0; i < 10; i += 2){
            Car car = Car.get("Car",0,carSizeH * (i+1), false);
            add(Layer.car, car);
        }
        for(int i = 1; i < 10; i += 2){
            Car car = Car.get("Car",w, carSizeH * (i+1), true);
            add(Layer.car, car);
        }

        player = new Player(w/ 2, h/2, 0,0);
        add(Layer.player, player);

        int margin =  (int)(20 * GameView.MULTIPLIER);
        score = new Score(w - margin, margin);
        score.setScore(0);
        add(Layer.ui, score);

        VerticalScrollBackground bg = new VerticalScrollBackground(R.mipmap.map_1, 10);
        add(Layer.map, bg);

        Coin coin = Coin.get("Coin", 60, 60);
        add(Layer.item, coin);
        Barrier barrier = Barrier.get("Barrier", 60, 120);
        add(Layer.item, barrier);
        Blinker blinker = Blinker.get("Blinker", 60, 180);
        add(Layer.item, blinker);
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
        //if(this.initialized == false) return;

        for (ArrayList<GameObject> objects: layers) {
            for (GameObject o : objects) {
                o.update();
            }
        }
        ArrayList<GameObject> enemies = layers.get(Layer.car.ordinal());
        for(GameObject o1 : enemies){
            Car enemy = (Car) o1;
            if(CollisionHelper.collides((BoxCollidable)o1, player)){
                Log.d(TAG, "Collision!! Enemy - player" );
            }
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

    // 아이템 - Coin을 획득한 경우 점수를 증가시킨다
    public void IncreatePoint(int score){
        this.score.addScore(score);
    }

    // 아이템 - Blinker을 획득한 경우, 특정 시간 만큼 장애물(차 종류)들의 이동을 멈춘다
    public void StopCars(float time){
        this.remainBlinkTime += time;
    }
}
