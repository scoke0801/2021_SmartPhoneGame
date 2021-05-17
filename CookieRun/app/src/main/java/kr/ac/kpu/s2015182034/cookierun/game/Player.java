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
    private static final String TAG = Player.class.getSimpleName();
    private static final float GRAVITY = 2000.0f; // 초당 100픽셀씩 잡아당기는 힘을 가정
    private static final float JUMP_POWER = 1200.0f;
    private final float ground_y;

    private float x, y;   // 위치

    private float speed;
    private float vertSpeed; // 수직 속도

    private IndexedAnimationGameBitmap charBitmap;

    private int[] ANIM_INDICIES_RUNNING = { 100, 101, 102, 103};
    private int[] ANIM_INDICIES_JUMP = { 7, 8};
    private int[] ANIM_INDICIES_DOUBLE_JUMP = { 1,2,3,4};
    private enum State{
        running, jump, doubleJump, slide, hit
    }

    private State state = State.running;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        this.ground_y = y;
        this.speed = 800;
        this.vertSpeed = 0.0f;
        this.charBitmap = new IndexedAnimationGameBitmap(R.mipmap.cookie, 12.5f, 10);
        setState(state.running);
    }

    public void moveTo(float x, float y){
        //Sound.play(R.raw.hadouken);
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
                setState(state.running);
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
            setState(state.doubleJump);
            vertSpeed = -JUMP_POWER;
        }
        else {
            setState(state.jump);
            vertSpeed = -JUMP_POWER;
        }
    }


    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        int[] indices = ANIM_INDICIES_RUNNING;
        switch(state){
            case running:
                indices = ANIM_INDICIES_RUNNING;
                break;
            case jump:
                indices = ANIM_INDICIES_JUMP;
                break;
            case doubleJump:
                indices = ANIM_INDICIES_DOUBLE_JUMP;
                break;
        }
        charBitmap.setIndices(indices);
    }
}
