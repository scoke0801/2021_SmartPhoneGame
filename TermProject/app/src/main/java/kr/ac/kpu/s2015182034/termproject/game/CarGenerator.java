package kr.ac.kpu.s2015182034.termproject.game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.Random;

import kr.ac.kpu.s2015182034.termproject.framework.GameObject;
import kr.ac.kpu.s2015182034.termproject.framework.MainGame;
import kr.ac.kpu.s2015182034.termproject.ui.view.GameView;

public class CarGenerator implements GameObject {
    private static final String TAG = CarGenerator.class.getSimpleName();
    private static float INITIAL_SPAWN_INTERVAL = 2.0f;
    private float time;
    private float spawnInterval;
    private int wave;

    public CarGenerator(){
        time =  0.0f;
        spawnInterval = INITIAL_SPAWN_INTERVAL;
        wave = 0;
    }
    @Override
    public void update() {
        time += MainGame.get().frameTime;
        if(time >= spawnInterval){
            generate();
            time -= spawnInterval;
        }
    }

    private void generate() {
        ++wave;
        Log.d(TAG, "EnemyGenerator::generate()");
        MainGame game = MainGame.get();
        int tenth = GameView.view.getWidth() / 10;
        Random r = new Random();
        for (int i = 1; i <= 9; i += 2) {
            int x = tenth * i;
            int y = 0;
            int level = wave / 10 - r.nextInt(3);
            if(level < 1) level = 1;
            if(level > 20) level = 20;
            Car car = Car.get("Car", x, y, false);
            game.add(car);
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void fixCollision() {

    }
}
