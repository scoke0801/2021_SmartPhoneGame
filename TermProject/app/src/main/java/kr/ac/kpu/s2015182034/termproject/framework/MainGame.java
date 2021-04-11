package kr.ac.kpu.s2015182034.termproject.framework;


import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Random;

import kr.ac.kpu.s2015182034.termproject.game.Player;
import kr.ac.kpu.s2015182034.termproject.ui.view.GameView;

public class MainGame {
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

    public void InitResources() {
        if(this.initialized){
            return;
        }
        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();

        player = new Player(w/ 2, h/2, 0,0);
        objects.add(player);

        this.initialized = true;
    }

    public void update() {
        if(this.initialized == false) return;

        for(GameObject o : objects){
            o.update();
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
        objects.add(gameObject);
    }

    public void remove(GameObject gameObject) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                objects.remove(gameObject);
            }
        });
    }
}
