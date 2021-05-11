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

    protected static float FRAME_RATE = 8.5f; // 1초당 8.5장의 속도로 애니메이션을 수행하도록

    protected float speed;

    protected float spriteIdx = 0;

    private int count = 0;
    private boolean isUpMove = true;
    protected String typeName;
    private String[] ITEM_TYPE = new String[]{
            "Coin", "Barrier", "Blinker",
    };
    private static int[] ITEM_RESOURCES = new int[]{
            R.mipmap.coin, R.mipmap.barrier, R.mipmap.blinker
    };

    protected Item(String type, float x, float y, int frameCount) {
        this.x = x;
        this.y = y;
        this.typeName = type;
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
            bitmap = new AnimationBitmap(resId, FRAME_RATE, frameCount);
        }
        this.sx = bitmap.getWidth();
        this.sy = bitmap.getHeight();
    }

    private void init(String type, float x, float y) {
        this.x = x;
        this.y = y;
        this.typeName = type;
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
    public static Item get(String type, float x, float y){
        MainGame game = MainGame.get();
        Item item  = (Item)game.get(Item.class);
        if(item == null){
            item = new Item(type, x,y, 1);
        }
        else{
            item.init(type, x,y);
        }
        return item;
    }
    public void update() {
        ++count;
        if(isUpMove){
            y += 0.3;
        }
        else{
            y -= 0.3;
        }
        if (count > 50){
            count = 0;
            isUpMove = !isUpMove;
        }
    }

    public void draw(Canvas canvas) {
        bitmap.draw(canvas, x, y);
        //bitmap.drawAABB(canvas, x, y);
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
    public String GetTypeName(){
        return typeName;
    }

    public void SetPosition(float x, float y){
        this.x = x;
        this.y = y;
    }
}
