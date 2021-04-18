package kr.ac.kpu.s2015182034.termproject.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.ArrayList;

import kr.ac.kpu.s2015182034.termproject.R;
import kr.ac.kpu.s2015182034.termproject.animation.AnimationBitmap;
import kr.ac.kpu.s2015182034.termproject.framework.CalculateFunctions;
import kr.ac.kpu.s2015182034.termproject.framework.GameObject;
import kr.ac.kpu.s2015182034.termproject.framework.MainGame;
import kr.ac.kpu.s2015182034.termproject.ui.view.GameView;

public class Player implements GameObject {
    private float x, y;   // 위치
    private float dx, dy; // 속도

    private int sx, sy; // 크기

    private float tx, ty; //target 위치

    private static AnimationBitmap bitmap;

    private static float FRAME_RATE = 8.5f; // 1초당 8.5장의 속도로 애니메이션을 수행하도록

    private float angle = 0;
    private static float speed = (float)100.0;
    private boolean isOnMove;

    private float spriteIdx = 0;

    public Player(float x, float y, float dx, float dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.tx = 0;
        this.ty = 0;
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
        if(idx == 0){
            this.ty = this.y - this.sy;
            this.dy = frameTime * -speed;
            this.angle = 0.0f;
        }
        else if(idx == 1){
            this.ty = this.y + this.sy;
            this.dy = frameTime * speed;
            this.angle = 180.0f;
        }
        else if(idx == 2){
            this.tx = this.x - this.sx;
            this.dx = frameTime * -speed;
            this.angle = -90.0f;
        }
        else if (idx == 3){
            this.tx = this.x + this.sx;
            this.dx = frameTime * speed;
            this.angle = 90.0f;
        }
        isOnMove = true;
    }

    public void update() {
        if (isOnMove){
            float frameTime = MainGame.get().frameTime;
            spriteIdx += 3 * frameTime;
            if (spriteIdx > 3) {
                spriteIdx = 0;
            }
        }
        else{
            spriteIdx = 0;
        }
        x += dx;
        y += dy;

        if((dx > 0 && this.x > tx)||(dx < 0 && this.x < tx)) {
            x = tx;
            dx = 0;
            isOnMove = false;
        }
        if((dy > 0 && this.y > ty)||(dy < 0 && this.y < ty)) {
            y = ty;
            dy = 0;
            isOnMove = false;
        }
    }

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(this.angle, x, y);

        bitmap.draw(canvas, x, y);
        canvas.restore();

        bitmap.drawAABB(canvas,x,y);
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
    public RectF getAABB(){
        return bitmap.getAABB(x,y);
    }
    public void fixCollision(){

    }
}
