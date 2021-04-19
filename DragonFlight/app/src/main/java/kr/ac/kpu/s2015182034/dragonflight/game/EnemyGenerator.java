package kr.ac.kpu.s2015182034.dragonflight.game;

import android.graphics.Canvas;
import android.util.Log;

import kr.ac.kpu.s2015182034.dragonflight.UI.View.GameView;
import kr.ac.kpu.s2015182034.dragonflight.framework.GameObject;

public class EnemyGenerator implements GameObject {
    private static final String TAG = EnemyGenerator.class.getSimpleName();
    private static float INITIAL_SPAWN_INTERVAL = 5.0f;
    private float time;
    private float spawnInterval;

    public EnemyGenerator(){
        time =  0.0f;
        spawnInterval = INITIAL_SPAWN_INTERVAL;
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
        Log.d(TAG, "EnemyGenerator::generate()");
        MainGame game = MainGame.get();
        float xRate = GameView.view.getWidth() / 10;
        for(int i = 1; i < 10; i += 2){
            game.add(new Enemy(xRate *i, 0, 700));
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
