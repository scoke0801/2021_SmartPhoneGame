package kr.ac.kpu.s2015182034.termproject.game.Parent;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.Random;

import kr.ac.kpu.s2015182034.termproject.R;
import kr.ac.kpu.s2015182034.termproject.animation.AnimationBitmap;
import kr.ac.kpu.s2015182034.termproject.framework.BoxCollidable;
import kr.ac.kpu.s2015182034.termproject.framework.GameObject;
import kr.ac.kpu.s2015182034.termproject.framework.MainGame;
import kr.ac.kpu.s2015182034.termproject.framework.Recyclable;
import kr.ac.kpu.s2015182034.termproject.ui.view.GameView;

public class Item implements GameObject, BoxCollidable, Recyclable {
    protected float x, y;   // 위치

    protected int sx, sy; // 크기

    protected boolean isLeft;
    protected AnimationBitmap bitmap;

    private static float FRAME_RATE = 8.5f; // 1초당 8.5장의 속도로 애니메이션을 수행하도록

    protected float speed;

    protected float spriteIdx = 0;

    private String[] ITEM_TYPE = new String[]{
            "Blinker", "Barrier", "Coin",
    };
    private static int[] ITEM_RESOURCES = new int[]{
            R.mipmap.car_digger_left, R.mipmap.car_digger_right
    };

    protected Item(String type, float x, float y) {
        this.x = x;
        this.y = y;
        Random r = new Random();
        this.speed = r.nextInt(200) + 100;
        if (bitmap == null) {
            Resources res = GameView.view.getResources();
            int resId = -1;
            int index = -1;
            for(int i = 0; i < ITEM_TYPE.length; ++i){
                if (ITEM_TYPE[i] == type){
                    index = i;
                    break;
                }
            }
            resId = ITEM_RESOURCES[index];
            bitmap = new AnimationBitmap(resId, FRAME_RATE, 1);
        }
        this.sx = bitmap.getWidth();
        this.sy = bitmap.getHeight();
    }



    protected Item() {

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
                bitmap = new AnimationBitmap(R.mipmap.car_digger_left, FRAME_RATE, 1);
            }
            else{
                bitmap = new AnimationBitmap(R.mipmap.car_digger_right, FRAME_RATE, 1);
            }
            this.sx = bitmap.getWidth();
            this.sy = bitmap.getHeight();
        }
    }
    public static Item get(String type, float x, float y){
        MainGame game = MainGame.get();
        Item item  = (Item)game.get(Item.class);
        if(item == null){
            item = new Item(type, x,y);
        }
        else{
            item.init(type, x,y);
        }
        return item;
    }
    public void update() {
        // 아이템은 위아래로 둥실거리게만...
        float dy = MainGame.get().frameTime * this.speed;
        this.y += dy;
    }

    public void draw(Canvas canvas) {
        bitmap.draw(canvas, x, y);
        bitmap.drawAABB(canvas, x, y);
    }

    public void fixCollision(){

    }

    @Override
    public void getBoundingRect(RectF rect) {
        bitmap.getBoundingRect(x,y,rect);
    }

    @Override
    public void recyle() {
        // To do
    }
}