package kr.ac.kpu.s2015182034.termproject.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;

import kr.ac.kpu.s2015182034.termproject.R;
import kr.ac.kpu.s2015182034.termproject.framework.CalculateFunctions;
import kr.ac.kpu.s2015182034.termproject.framework.GameObject;
import kr.ac.kpu.s2015182034.termproject.framework.MainGame;
import kr.ac.kpu.s2015182034.termproject.ui.view.GameView;

public class Player implements GameObject {
    private float x, y;   // 위치
    private float dx, dy; // 속도

    private int sx, sy; // 크기

    private float tx, ty; //target 위치

    private Bitmap bitmap;
    private ArrayList<Bitmap> bitmaps = new ArrayList<>();

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
            bitmap = BitmapFactory.decodeResource(res, R.mipmap.frog_1);

            sx = bitmap.getWidth();
            sy = bitmap.getHeight();
            //bitmap.setHeight(sy * 2);
            //bitmap.setWidth(sx * 2);
            //sx *= 2;
            //sy *= 2;

            bitmaps.add(BitmapFactory.decodeResource(res, R.mipmap.frog_1));
            bitmaps.add(BitmapFactory.decodeResource(res, R.mipmap.frog_2));
            bitmaps.add(BitmapFactory.decodeResource(res, R.mipmap.frog_3));
            bitmaps.add(BitmapFactory.decodeResource(res, R.mipmap.frog_4));
        }
    }

    public void moveTo(float x, float y){
        //if(isOnMove) return;

        this.x = x;
        this.y = y;

        this.tx = x;
        this.ty = y;
    }
    public void update() {
        spriteIdx += 1 * MainGame.get().frameTime;
        if(spriteIdx > 3) spriteIdx = 0;

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
        float left = x - sx / 2;
        float top = y - sy / 2;
        float degree = (float)(this.angle * 180.0f / Math.PI) ;

        canvas.save();
        canvas.rotate(degree, x, y);
        //canvas.drawBitmap(bitmaps[spriteIdx], left, top, null);
        canvas.drawBitmap(bitmap, left, top, null);

        canvas.restore();
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
}
