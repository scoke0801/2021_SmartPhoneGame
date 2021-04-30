package kr.ac.kpu.s2015182034.termproject.framework;


import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import kr.ac.kpu.s2015182034.termproject.game.Car;
import kr.ac.kpu.s2015182034.termproject.game.Player;
import kr.ac.kpu.s2015182034.termproject.ui.view.GameView;
import kr.ac.kpu.s2015182034.termproject.utils.CollisionHelper;

public class MainGame {
    private static final String TAG = MainGame.class.getSimpleName();
    // singleton 패턴
    private static MainGame instance;
    public static MainGame get(){
        if(instance == null){
            instance = new MainGame();
        }
        return instance;
    }
    public float frameTime;

    private boolean initialized = false;
    Player player;
    ArrayList<GameObject> objects = new ArrayList<>();

    private static HashMap<Class, ArrayList<GameObject>> reclycleBin = new HashMap<>();

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
    public void InitResources() {
        if(this.initialized){
            return;
        }
        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();

        // test codes
        int carSizeH = 170;
        for(int i = 0; i < 10; i += 2){
            Car car = Car.get("Car",0,carSizeH * (i+1), false);
            objects.add(car);
        }
        for(int i = 1; i < 10; i += 2){
            Car car = Car.get("Car",w, carSizeH * (i+1), true);
            objects.add(car);
        }

        player = new Player(w/ 2, h/2, 0,0);
        objects.add(player);

        this.initialized = true;
    }

    public void update() {
        if(this.initialized == false) return;

        for(GameObject o : objects){
            o.update();
        }

        boolean removed = false;
        for (GameObject o1 : objects) {
            if(!(o1 instanceof Car)){
                continue;
            }
            /*Car enemy = (Car)o1;
            for(GameObject o2 : objects){
                Bullet bullet = (Bullet)o2;
                if(CollisionHelper.collides((BoxCollidable)o1, (BoxCollidable)o2)){
                    Log.d(TAG, "Collision!! "  + o1 + " - " + o2);
                    remove(enemy);
                    remove(bullet);
                    //bullet.doRecycle();
                    removed = true;
                    break;
                }
            }
            if(removed){
                continue;
            }*/
            if(CollisionHelper.collides((BoxCollidable)o1, player)){
                Log.d(TAG, "Collision!! Enemy - player" );
            }
        }
    }

    public void draw(Canvas canvas) {
        if(this.initialized == false) return;

        for(GameObject object : objects) {
            object.draw(canvas);
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

    public void add(GameObject gameObject){
        GameView.view.post(new Runnable(){
            @Override
            public void run(){
                objects.add(gameObject);
            }
        });
        Log.d(TAG, "<A> object count = " + objects.size());
    }

    public void remove(GameObject gameObject) {
        if(gameObject instanceof Recyclable){
            ((Recyclable)gameObject).recyle();
            recycle(gameObject);
        }
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                objects.remove(gameObject);
                Log.d(TAG, "<R> object count = " + objects.size());
            }
        });
    }
}
