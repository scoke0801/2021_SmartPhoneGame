package kr.ac.kpu.s2015182034.cookierun.framework;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.kpu.s2015182034.cookierun.R;
import kr.ac.kpu.s2015182034.cookierun.framework.View.GameView;
import kr.ac.kpu.s2015182034.cookierun.game.Player;
import kr.ac.kpu.s2015182034.cookierun.game.Score;

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
        bg,bg2,bg3, enemy, bullet, player, ui, controller, COUNT
    }
    public boolean initResources() {
        if (initialized) {
            return false;
        }

        initLayers(Layer.COUNT.ordinal());

        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();

        player = new Player(w/2, h - 750);
        add(Layer.player, player);

        int margin =  (int)(20 * GameView.MULTIPLIER);
        score = new Score(w - margin, margin);
        score.setScore(0);
        add(Layer.ui, score);
        initialized = true;

        // parallax scrolling
        add(Layer.bg, new HorizontalScrollBackground(R.mipmap.cookie_run_bg_1, -20));
        add(Layer.bg2, new HorizontalScrollBackground(R.mipmap.cookie_run_bg_2, -10));
        add(Layer.bg3, new HorizontalScrollBackground(R.mipmap.cookie_run_bg_3, -5));
//        add(Layer.bg, new VerticalScrollBackground(R.mipmap.cookie_run_bg_1, -20));
//        add(Layer.bg2, new VerticalScrollBackground(R.mipmap.cookie_run_bg_2, -10));
//        add(Layer.bg3, new VerticalScrollBackground(R.mipmap.cookie_run_bg_3, -5));
        return true;
    }
    private void initLayers(int layerCount) {
        layers = new ArrayList<>();
        for (int i = 0; i < layerCount; i++) {
            layers.add(new ArrayList<>());
        }
    }

    public void update() {
        if (!initialized) return;
        for (ArrayList<GameObject> objects: layers) {
            for (GameObject o : objects) {
                o.update();
            }
        }
    }
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
}
