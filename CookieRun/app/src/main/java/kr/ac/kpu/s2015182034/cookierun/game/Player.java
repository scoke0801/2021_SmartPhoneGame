package kr.ac.kpu.s2015182034.cookierun.game;


import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.kpu.s2015182034.cookierun.R;
import kr.ac.kpu.s2015182034.cookierun.framework.bitmap.GameBitmap;
import kr.ac.kpu.s2015182034.cookierun.framework.bitmap.IndexedAnimationGameBitmap;
import kr.ac.kpu.s2015182034.cookierun.framework.game.BaseGame;
import kr.ac.kpu.s2015182034.cookierun.framework.iface.BoxCollidable;
import kr.ac.kpu.s2015182034.cookierun.framework.iface.GameObject;

public class Player implements GameObject, BoxCollidable {
    private static final float FIRE_INTERVAL =  1.0f / 7.5f;
    private static final String TAG = Player.class.getSimpleName();
    private static final float GRAVITY = 2000.0f; // 초당 100픽셀씩 잡아당기는 힘을 가정
    private static final float JUMP_POWER = 1200.0f;
    private final float ground_y;

    private float x, y;   // 위치
    private float dx, dy; // 속도
    private float tx, ty; //target 위치

    private float speed;
    private float vertSpeed; // 수직 속도

    private IndexedAnimationGameBitmap charBitmap;
    private GameBitmap fireBitmap;

    private enum State{
        running, jump, doubleJump, slide, hit
    }

    private State state = State.running;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        this.ground_y = y;
        this.tx = x;
        this.ty = 0;
        this.speed = 800;
        this.vertSpeed = 0.0f;
        this.charBitmap = new IndexedAnimationGameBitmap(R.mipmap.cookie, 12.5f, 10);
        this.charBitmap.setIndices(100, 101, 102, 103);
    }

    public void moveTo(float x, float y){
        //Sound.play(R.raw.hadouken);

        this.tx = x;
    }
    @Override
    public void update() {
        BaseGame game = BaseGame.get();
        if(state == State.jump || state == State.doubleJump){
            float y = this.y + vertSpeed * game.frameTime;
            this.y = y;
            vertSpeed += GRAVITY * game.frameTime;

            if(y >= ground_y){
                y = ground_y;
                this.state = State.running;
                this.charBitmap.setIndices(100, 101, 102, 103);
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        charBitmap.draw(canvas, x, y);
    }

    @Override
    public void getBoundingRect(RectF rect) {
        charBitmap.getBoundingRect(x,y, rect);
    }

    public void Jump() {
        //if(state != State.running && state != State.jump && state != State.slide) {
        if(state != State.running && state != State.jump) {
            Log.d(TAG, "Not in a state that can jump: " + state);
            return;
        }
        if(state == State.jump) {
            state = State.doubleJump;
            charBitmap.setIndices(1,2,3,4);
        }
        else {
            state = State.jump;
            charBitmap.setIndices(7, 8);

        }
        vertSpeed = -JUMP_POWER;
    }
}