package kr.ac.kpu.s2015182034.termproject.game;

import android.content.res.Resources;
import android.graphics.Canvas;

import java.util.Random;

import kr.ac.kpu.s2015182034.termproject.R;
import kr.ac.kpu.s2015182034.termproject.animation.AnimationBitmap;
import kr.ac.kpu.s2015182034.termproject.framework.MainGame;
import kr.ac.kpu.s2015182034.termproject.game.Parent.Item;
import kr.ac.kpu.s2015182034.termproject.ui.view.GameView;

public class Coin extends Item {
    Coin(String type, float x, float y, boolean isLeft){

        super(type, x, y, 0);
    }


    @Override
    public void update() {
        super.update();
    }

    public void draw(Canvas canvas) {
        bitmap.draw(canvas, x, y);
        //bitmap.drawAABB(canvas, x, y);
    }
    private void init(String type, float x, float y) {
        this.x = x;
        this.y = y;
        if(speed < 0){
            speed = -speed;
        }
        Random r = new Random();
        this.speed = r.nextInt(200) + 100;
        this.isLeft = isLeft;
        if(bitmap == null) {
            Resources res = GameView.view.getResources();
            if(isLeft) {
                bitmap = new AnimationBitmap(R.mipmap.truck_left, FRAME_RATE, 1);
            }
            else{
                bitmap = new AnimationBitmap(R.mipmap.truck_right, FRAME_RATE, 1);
            }
            this.sx = bitmap.getWidth();
            this.sy = bitmap.getHeight();
        }
    }
    public static Coin get(String type, float x, float y){
        MainGame game = MainGame.get();
        Coin coin  = (Coin)game.get(Item.class);
        if(coin == null){
            coin = new Coin(type, x,y, false);
        }
        else{
            coin.init(type, x,y);
        }
        return coin;
    }
}
