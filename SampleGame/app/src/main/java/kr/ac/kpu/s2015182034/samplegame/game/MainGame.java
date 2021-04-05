package kr.ac.kpu.s2015182034.samplegame.game;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Random;

import kr.ac.kpu.s2015182034.samplegame.framework.GameObject;

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

    Player player;
    ArrayList<GameObject> objects = new ArrayList<>();

    public void InitResources() {
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

        player = new Player(450, 750, 0,0);
        objects.add(player);
    }

    public void update() {
        for(GameObject o : objects){
            o.update();
        }
    }

    public void draw(Canvas canvas) {
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
}
