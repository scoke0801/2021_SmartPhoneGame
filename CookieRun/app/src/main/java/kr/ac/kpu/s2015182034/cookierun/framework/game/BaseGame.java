package kr.ac.kpu.s2015182034.cookierun.framework.game;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.kpu.s2015182034.cookierun.framework.View.GameView;
import kr.ac.kpu.s2015182034.cookierun.framework.iface.GameObject;
import kr.ac.kpu.s2015182034.cookierun.framework.iface.Recyclable;

public class BaseGame {
    private static final String TAG = BaseGame.class.getSimpleName();
    // singleton
    private static BaseGame instance;

    public float frameTime;

    public static BaseGame get() {
        return instance;
    }
    protected BaseGame(){
        instance = this;
    }

    ArrayList<ArrayList<GameObject>> layers;
    private static HashMap<Class, ArrayList<GameObject>> reclycleBin = new HashMap<>();


    public void recycle(GameObject object){
        Class className = object.getClass();
        ArrayList<GameObject> array = reclycleBin.get(className);
        if(array == null){
            array = new ArrayList<>();
            reclycleBin.put(className, array);
        }
        array.add(object);
    }

    public GameObject get(Class className){
        ArrayList<GameObject> array = reclycleBin.get(className);
        if(array == null) return null;
        if(array.isEmpty()) return null;
        return array.remove(0);
    }
    public boolean initResources() {
        Log.d(TAG, "BaseGame::initResources is error");
        return false;
    }

    protected void initLayers(int layerCount) {
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
    }
    public void draw(Canvas canvas) {
        //if (!initialized) return;
        for(ArrayList<GameObject> objects : layers){
            for (GameObject o : objects) {
                o.draw(canvas);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "BaseGame::onTouchEvent is error");
        return false;
    }

    public void add(int layerIndex, GameObject gameObject) {
        GameView.view.post(new Runnable(){
            @Override
            public void run(){
                ArrayList<GameObject> objects = layers.get(layerIndex);
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
