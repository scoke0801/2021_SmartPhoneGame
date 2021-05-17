package kr.ac.kpu.s2015182034.cookierun.game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.kpu.s2015182034.cookierun.framework.View.GameView;
import kr.ac.kpu.s2015182034.cookierun.framework.game.BaseGame;
import kr.ac.kpu.s2015182034.cookierun.framework.iface.GameObject;

public class StageMap implements GameObject {

    private static final String TAG = StageMap.class.getSimpleName();

    @Override
    public void update() {
        MainGame game = (MainGame)BaseGame.get();
        ArrayList<GameObject> objects = game.objectsAt(MainGame.Layer.platform);

        float rightMost = 0;
        for(GameObject obj: objects){
            Platform platform = (Platform)obj;
            float right = platform.getRight();
            if(rightMost < right){
                rightMost = right;
            }
        }

        float vw = GameView.view.getWidth();
        float vh = GameView.view.getHeight();
        if(rightMost < vw){
            // create a Platform;
            //Log.d(TAG, "Create Platform In StageMap");
            float tx = rightMost, ty =  vh - Platform.Type.T_10x2.height();
            Platform platform = new Platform(Platform.Type.RANDOM, tx, ty);
            game.add(MainGame.Layer.platform, platform);
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
