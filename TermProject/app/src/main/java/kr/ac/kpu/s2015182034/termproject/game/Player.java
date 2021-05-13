package kr.ac.kpu.s2015182034.termproject.game;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.kpu.s2015182034.termproject.R;
import kr.ac.kpu.s2015182034.termproject.animation.AnimationBitmap;
import kr.ac.kpu.s2015182034.termproject.framework.BoxCollidable;
import kr.ac.kpu.s2015182034.termproject.framework.CalculateFunctions;
import kr.ac.kpu.s2015182034.termproject.framework.GameObject;
import kr.ac.kpu.s2015182034.termproject.framework.MainGame;
import kr.ac.kpu.s2015182034.termproject.ui.view.GameView;

public class Player implements GameObject, BoxCollidable {
    private static final String TAG = Player.class.getSimpleName();
    private float x, y;   // 위치
    private float dx, dy; // 속도

    private int sx, sy; // 크기
    
    private float jumpX, jumpY; // jump 계산에 사용할 변수
    private float moveTime = 0.0f;
    private final float TO_MOVE_TIME = 1.0f;
    private static AnimationBitmap bitmap;

    private static float FRAME_RATE = 8.5f; // 1초당 8.5장의 속도로 애니메이션을 수행하도록

    private static float angle = 0;
    private static float speed = (float)100.0;
    private static boolean isOnMove;

    private float spriteIdx = 0;

    private static float remainBarrierTime = 0.0f;
    public Player(float x, float y, float dx, float dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.isOnMove = false;
        if(bitmap == null) {
            Resources res = GameView.view.getResources();
            bitmap = new AnimationBitmap(R.mipmap.frog, FRAME_RATE, 4);
            this.sx = bitmap.getWidth();
            this.sy = bitmap.getHeight();
        }
    }

    public void moveTo(float x, float y){
        if(isOnMove) return;

        float frameTime = MainGame.get().frameTime;
        int idx = CalculateNextPositionIndex(x, y);
        if(idx == 0){   // 상
            this.dy = frameTime * -speed;
            this.angle = 0.0f;
            this.jumpX = -this.sx * 0.75f;
            this.jumpY = this.sy * 0.75f;
        }
        else if(idx == 1){ // 하
            this.dy = frameTime * speed;
            this.angle = 180.0f;
            this.jumpX = -this.sx * 0.75f;
            this.jumpY = -this.sy * 0.75f;
        }
        else if(idx == 2){ // 좌
            this.dx = frameTime * -speed;
            this.angle = -90.0f;
        }
        else if (idx == 3){ // 우
            this.dx = frameTime * speed;
            this.angle = 90.0f;
        }
        isOnMove = true;
    }

    public void update() {
        float frameTime = MainGame.get().frameTime;
        if (isOnMove){
            spriteIdx += 3 * frameTime;
            if (spriteIdx > 3) {
                spriteIdx = 0;
            }
        }
        else{
            spriteIdx = 0;
        }
        remainBarrierTime += frameTime;
        if(remainBarrierTime < 0.0f){
            remainBarrierTime = 0.0f;
        }
        if(isOnMove) {
            y += dy;
            x += dx;
            if(moveTime > TO_MOVE_TIME * 0.5f){
                x -= jumpX * frameTime;
                y -= jumpY * frameTime;
            }
            else{
                x += jumpX * frameTime;
                y += jumpY * frameTime;
            }
            moveTime += frameTime;

            if(moveTime > TO_MOVE_TIME){
                moveTime += frameTime;
                int h = GameView.view.getHeight();
                Log.d(TAG, "DY: " + this.dy + " ScroolSize: " + this.dy * TO_MOVE_TIME * 100.0f);
                MainGame.get().ScrollMap(0.0f, this.dy * TO_MOVE_TIME * 100.0f);
                dx = dy = 0;
                isOnMove = false;
                jumpX = jumpY = 0.0f;
                moveTime = 0.0f;
            }
        }
    }

    public void Jump(){
        
    }

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(this.angle, x, y);

        bitmap.draw(canvas, x, y);
        canvas.restore();

        //bitmap.drawAABB(canvas,x,y);
    }
    private int CalculateNextPositionIndex(float x, float y){
        float dists[] = {   // top, bottom, left, right
                CalculateFunctions.GetDistance(x, this.x, y, this.y - sy),
                CalculateFunctions.GetDistance(x, this.x, y, this.y + sy),
                CalculateFunctions.GetDistance(x, this.x - sx, y, this.y),
                CalculateFunctions.GetDistance(x, this.x + sx, y, this.y)
        };
        int closerPointIdx = 0;
        for(int i=0; i < dists.length; i++) {
            if (dists[closerPointIdx] > dists[i]) {
                closerPointIdx = i;
            }
        }
        return closerPointIdx;
    }
    public void fixCollision(){

    }

    @Override
    public void getBoundingRect(RectF rect) {
        bitmap.getBoundingRect(x, y, rect);
    }

    // 아이템 - Barrier를 획득하였을 경우
    public void SetBarrier(float barrierTime){
        remainBarrierTime += barrierTime;
    }
    public boolean IsOnBarrier(){
        return (remainBarrierTime > 0.0f);
    }
    public float GetXPos(){
        return x;
    }
    public float GetYPos(){
        return y;
    }
}
