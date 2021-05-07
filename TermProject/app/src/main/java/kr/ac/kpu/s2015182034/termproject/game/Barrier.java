package kr.ac.kpu.s2015182034.termproject.game;

import android.content.res.Resources;
import android.graphics.Canvas;

import java.util.Random;

import kr.ac.kpu.s2015182034.termproject.R;
import kr.ac.kpu.s2015182034.termproject.animation.AnimationBitmap;
import kr.ac.kpu.s2015182034.termproject.framework.MainGame;
import kr.ac.kpu.s2015182034.termproject.game.Parent.Item;
import kr.ac.kpu.s2015182034.termproject.ui.view.GameView;

public class Barrier extends Item {

    private static final float LIFE_TIME = 1.5f;
    private static float remainLifeTime = LIFE_TIME;
    private boolean isOnUsing = false;
    private Player connectePlayer;
    Barrier(String type, float x, float y, boolean isLeft){
        super(type, x, y, 1);
    }

    @Override
    public void update() {
        if(isOnUsing) {
            float frameTime = MainGame.get().frameTime;
            remainLifeTime -= frameTime;

            float x = connectePlayer.GetXPos();
            float y = connectePlayer.GetYPos();
            this.x = x;
            this.y = y;
            if (remainLifeTime < 0.0f){
                MainGame.get().remove(this);
            }
        }
        else{
            super.update();
        }
    }

    public void draw(Canvas canvas) {
        if(remainLifeTime > 0.0f) {
            bitmap.draw(canvas, x, y);
            bitmap.drawAABB(canvas, x, y);
        }
    }

    public boolean IsOnUse(){
        return isOnUsing;
    }
    public void setUse(boolean useInfo){
        isOnUsing = useInfo;
    }
    private void init(String type, float x, float y) {
        this.x = x;
        this.y = y;
        isOnUsing = false;
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
    public static Barrier get(String type, float x, float y){
        MainGame game = MainGame.get();
        Barrier barrier  = (Barrier)game.get(Item.class);
        if(barrier == null){
            barrier = new Barrier(type, x,y, false);
        }
        else{
            barrier.init(type, x,y);
        }
        return barrier;
    }

    public void ConnectPlayer(Player player){
        connectePlayer = player;
    }
}
