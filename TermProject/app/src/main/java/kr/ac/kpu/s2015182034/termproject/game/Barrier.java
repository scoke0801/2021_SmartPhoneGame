package kr.ac.kpu.s2015182034.termproject.game;

import android.graphics.Canvas;

import kr.ac.kpu.s2015182034.termproject.framework.MainGame;
import kr.ac.kpu.s2015182034.termproject.ui.view.GameView;

public class Barrier extends Item {
    Barrier(String type, float x, float y, boolean isLeft){
        super();
    }

    @Override
    public void update() {
        if (isOnMove){
        }
        else{
        }
        float dx = MainGame.get().frameTime * this.speed;
        if(isLeft){
            dx = -dx;
        }
        x += dx;

        int w = GameView.view.getWidth();
        if(isLeft){
            if( x < 0) {
                this.x = w;
            }
        }
        else{
            if(x > w){
                this.x = 0;
            }
        }
    }

    public void draw(Canvas canvas) {
        bitmap.draw(canvas, x, y);
        bitmap.drawAABB(canvas, x, y);
    }
}
