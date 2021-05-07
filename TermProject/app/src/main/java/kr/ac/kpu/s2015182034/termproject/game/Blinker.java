package kr.ac.kpu.s2015182034.termproject.game;

import android.graphics.Canvas;

import kr.ac.kpu.s2015182034.termproject.framework.MainGame;
import kr.ac.kpu.s2015182034.termproject.game.Parent.Item;
import kr.ac.kpu.s2015182034.termproject.ui.view.GameView;

public class Blinker extends Item {
    private Blinker(String type, float x, float y, boolean isLeft){
        super();
    }

    @Override
    public void update() {
        float dx = MainGame.get().frameTime * this.speed;
        if(isLeft){
            dx = -dx;
        }
        x += dx;
    }

    @Override
    public void draw(Canvas canvas) {
        bitmap.draw(canvas, x, y);
        bitmap.drawAABB(canvas, x, y);
    }

    public static Blinker get(String type, float x, float y){
        MainGame game = MainGame.get();
        Blinker blinker = (Blinker)game.get(Blinker.class);
        if(blinker == null){
            blinker = new Blinker(type, x,y, false);
        }
        else{
            //item.init(type, x,y);
        }
        return blinker;
    }
}
