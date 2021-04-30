package kr.ac.kpu.s2015182034.termproject.game;

import android.graphics.Canvas;

import kr.ac.kpu.s2015182034.termproject.framework.MainGame;
import kr.ac.kpu.s2015182034.termproject.game.Parent.Item;
import kr.ac.kpu.s2015182034.termproject.ui.view.GameView;

public class Barrier extends Item {

    private static final float LIFE_TIME = 1.5f;
    private static float remainLifeTime = LIFE_TIME;

    Barrier(String type, float x, float y, boolean isLeft){
        super();
    }

    @Override
    public void update() {
        float frameTime = MainGame.get().frameTime;
        remainLifeTime -= frameTime;

        if (remainLifeTime < 0.0f){
            MainGame.get().remove(this);
        }
    }

    public void draw(Canvas canvas) {
        if(remainLifeTime > 0.0f) {
            bitmap.draw(canvas, x, y);
            bitmap.drawAABB(canvas, x, y);
        }
    }
}
