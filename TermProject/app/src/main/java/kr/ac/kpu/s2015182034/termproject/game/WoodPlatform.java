package kr.ac.kpu.s2015182034.termproject.game;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.Random;

import kr.ac.kpu.s2015182034.termproject.R;
import kr.ac.kpu.s2015182034.termproject.animation.AnimationBitmap;
import kr.ac.kpu.s2015182034.termproject.animation.GameBitmap;
import kr.ac.kpu.s2015182034.termproject.framework.BoxCollidable;
import kr.ac.kpu.s2015182034.termproject.framework.GameObject;
import kr.ac.kpu.s2015182034.termproject.framework.MainGame;
import kr.ac.kpu.s2015182034.termproject.framework.Recyclable;
import kr.ac.kpu.s2015182034.termproject.game.Parent.Car;
import kr.ac.kpu.s2015182034.termproject.ui.view.GameView;

public class WoodPlatform implements GameObject, BoxCollidable, Recyclable {
    private static final String TAG = WoodPlatform.class.getSimpleName();
    protected float x, y;   // 위치

    protected int sx, sy; // 크기

    protected GameBitmap bitmap;

    protected float remainStopTime = 0.0f;

    protected float speed;
    protected boolean isOnMove;

    protected float spriteIdx = 0;
    private Player onPlayer = null;    // 자신위에 올라와있는 플레이어
    private boolean isOnPlayerExist = false;
    private WoodPlatform(String type, float x, float y){
        Random r = new Random();
        this.speed = r.nextInt(100) + 100;

        this.x = x;
        this.y = y;
        this.isOnMove = false;
        if (bitmap == null) {
            int resId = 0;
            if(type == "LongWood"){
                resId = R.mipmap.wood_01;
            }
            else if (type == "ShortWood") {
                resId = R.mipmap.wood_02;
            }
            bitmap = new GameBitmap(resId);
        }
        this.sx = bitmap.getWidth();
        this.sy = bitmap.getHeight();
    }

    @Override
    public void update() {
        float dx = MainGame.get().frameTime * this.speed;

        x -= dx;
        if(true == isOnPlayerExist){
            onPlayer.movePosition(-dx, 0.0f);
        }
        int w = GameView.view.getWidth();
        if( x < 0) {
            if (true == isOnPlayerExist) {
                onPlayer.movePosition(w - x, 0.0f);
            }
            this.x = w;
        }
    }

    public void draw(Canvas canvas) {
        bitmap.draw(canvas, x, y);
        //bitmap.drawAABB(canvas, x, y);
    }

    @Override
    public void fixCollision() {

    }
    private void init(String type, float x, float y) {
        this.x = x;
        this.y = y;
        Random r = new Random();
        //this.speed = r.nextInt(200) + 100;
        if (bitmap == null) {
            int resId = 0;
            if(type == "LongWood"){
                resId = R.mipmap.wood_01;
            }
            else if (type == "ShortWood") {
                resId = R.mipmap.wood_02;
            }
            bitmap = new GameBitmap(resId);
        }
        this.sx = bitmap.getWidth();
        this.sy = bitmap.getHeight();
    }
    public static WoodPlatform get(String type, float x, float y){
        MainGame game = MainGame.get();
        WoodPlatform obj  = (WoodPlatform)game.get(WoodPlatform.class);
        if(obj == null){
            obj = new WoodPlatform(type, x,y);
        }
        else{
            obj.init(type, x,y);
        }
        return obj;
    }

    @Override
    public void getBoundingRect(RectF rect) {
        bitmap.getBoundingRect(x,y,rect);
    }

    @Override
    public void recyle() {
        // To do
    }

    @Override
    public void movePosition(float xMoved, float yMoved) {
        this.x += xMoved;
        this.y += yMoved;
    }
    public void ConnectPlayer(Player player){
        if(player == null){
            isOnPlayerExist = false;
            onPlayer = null;
        }
        else{
            onPlayer = player;
            isOnPlayerExist = true;
        }
    }
}
