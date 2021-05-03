package kr.ac.kpu.s2015182034.dragonflight.game;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.kpu.s2015182034.dragonflight.UI.View.GameView;
import kr.ac.kpu.s2015182034.dragonflight.framework.BoxCollidable;
import kr.ac.kpu.s2015182034.dragonflight.framework.GameObject;
import kr.ac.kpu.s2015182034.dragonflight.framework.Recyclable;
import kr.ac.kpu.s2015182034.dragonflight.utils.CollisionHelper;

public class MainGame {
    private static final int BALL_COUNT = 10;
    private static final String TAG = MainGame.class.getSimpleName();
    // singleton
    private static MainGame instance;
    private Player player;
    private Score score;

    public static MainGame get() {
        if (instance == null) {
            instance = new MainGame();
        }
        return instance;
    }
    public float frameTime;
    private boolean initialized;

    ArrayList<ArrayList<GameObject>> layers;
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

    public enum Layer{
        enemy, bullet, player, ui, controller, COUNT
    }
    public boolean initResources() {
        if (initialized) {
            return false;
        }

        initLayers(Layer.COUNT.ordinal());

        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();

        player = new Player(w/2, h - 300);
        add(Layer.player, player);
        add(Layer.controller, new EnemyGenerator());

        int margin =  (int)(20 * GameView.MULTIPLIER);
        score = new Score(w - margin, margin);
        score.setScore(0);
        add(Layer.ui, score);
        initialized = true;
        return true;
    }
    private void initLayers(int layerCount) {
        layers = new ArrayList<>();
        for (int i = 0; i < layerCount; i++) {
            layers.add(new ArrayList<>());
        }
    }

    public void update() {
        //if (!initialized) return;
        for (ArrayList<GameObject> objects: layers) {
            for (GameObject o : objects) {
                o.update();
            }
        }
        ArrayList<GameObject> enemies = layers.get(Layer.enemy.ordinal());
        ArrayList<GameObject> bullets = layers.get(Layer.bullet.ordinal());
        for(GameObject o1 : enemies){
            Enemy enemy = (Enemy) o1;
            boolean isEnemyRemoved = false;
            for(GameObject o2: bullets){
                Bullet bullet = (Bullet)o2;
                if(CollisionHelper.collides(enemy, bullet)){
                    remove(bullet);
                    remove(enemy);
                    isEnemyRemoved = true;
                    score.addScore(100);
                    break;
                }
            }
            if(isEnemyRemoved){
                break;
            }
        }
    }
//            boolean removed = false;
//            for (GameObject o1 : objects) {
//                if(!(o1 instanceof Enemy)){
//                    continue;
//                }
//                Enemy enemy = (Enemy)o1;
//                for(GameObject o2 : objects){
//                    if(!(o2 instanceof  Bullet )) {
//                        continue;
//                    }
//                    Bullet bullet = (Bullet)o2;
//                    if(CollisionHelper.collides((BoxCollidable)o1, (BoxCollidable)o2)){
//                        Log.d(TAG, "Collision!! "  + o1 + " - " + o2);
//                        remove(enemy);
//                        remove(bullet);
//                        //bullet.doRecycle();
//                        removed = true;
//                        break;
//                    }
//                }
//                if(removed){
//                    continue;
//                }
//                if(CollisionHelper.collides((BoxCollidable)o1, player)){
//                    Log.d(TAG, "Collision!! Enemy - player" );
//                }
//            }

    public void draw(Canvas canvas) {
        if (!initialized) return;
        for(ArrayList<GameObject> objects : layers){
            for (GameObject o : objects) {
                o.draw(canvas);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            player.moveTo(event.getX(), event.getY());
            return true;
        }
        return false;
    }

    public void add(Layer layer, GameObject gameObject) {
        GameView.view.post(new Runnable(){
            @Override
            public void run(){
                ArrayList<GameObject> objects = layers.get(layer.ordinal());
                objects.add(gameObject);
            }
        });
        Log.d(TAG, "<A> object count = " + layers.size());
    }

    public void remove(GameObject gameObject) {
        if(gameObject instanceof Recyclable){
            ((Recyclable)gameObject).recyle();
            recycle(gameObject);
        }
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                for(ArrayList<GameObject> objects : layers){
                    boolean removed = objects.remove(gameObject);
                    if(removed){
                        break;
                    }
                }
                Log.d(TAG, "<R> object count = " + layers.size());
            }
        });
    }
}
