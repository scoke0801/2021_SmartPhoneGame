package kr.ac.kpu.s2015182034.samplegame.game;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Random;

import kr.ac.kpu.s2015182034.samplegame.framework.GameObject;
import kr.ac.kpu.s2015182034.samplegame.ui.view.GameView;

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
    private final int BALL_COUNT = 10;

    private boolean initialized = false;
    Player player;
    ArrayList<GameObject> objects = new ArrayList<>();

    public boolean InitResources() {
        if(this.initialized){
            return false;
        }

        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();
        Random rand = new Random();
        for(int i = 0; i < BALL_COUNT; ++i){
            //float x = rand.nextInt(getWidth());
            //float y = rand.nextInt(getHeight());
            float x = rand.nextInt(1000);
            float y = rand.nextInt(1000);
            float dx = rand.nextFloat() * 1000 - 500;
            float dy = rand.nextFloat() * 1000 - 500;
            Ball ball = new Ball(x,y, dx, dy);
            objects.add(ball);
        }

        player = new Player(w/ 2, h/2, 0,0);
        objects.add(player);

        this.initialized = true;
        return true;
    }

    public void update() {
        if(this.initialized == false) {
            return;
        }

        for(GameObject o : objects){
            o.update();
        }
    }

    public void draw(Canvas canvas) {
        if(this.initialized == false) {
            return;
        }

        for(GameObject object : objects) {
            object.draw(canvas);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch(action){
            case MotionEvent.ACTION_DOWN:
            //case MotionEvent.ACTION_MOVE:
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
