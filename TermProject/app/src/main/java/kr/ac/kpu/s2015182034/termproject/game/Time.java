package kr.ac.kpu.s2015182034.termproject.game;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.kpu.s2015182034.termproject.R;
import kr.ac.kpu.s2015182034.termproject.animation.GameBitmap;
import kr.ac.kpu.s2015182034.termproject.framework.GameObject;
import kr.ac.kpu.s2015182034.termproject.framework.MainGame;
import kr.ac.kpu.s2015182034.termproject.ui.view.GameView;

// 타이머가 아님.
// Score 클래스처럼 남은 시간을 표현하기 위하여 사용할 것
public class Time implements GameObject {
    private static final String TAG = Time.class.getSimpleName();
    private final Bitmap bitmap;
    private final int right;
    private final int top;

    private float time = 310.0f;
    private float min = 5.0f;
    private float sec = 0.0f;
    private Rect rectSrc = new Rect();
    private RectF rectDst = new RectF();
    public Time(int right, int top){
        bitmap = GameBitmap.load(R.mipmap.time);
        this.right = right;
        this.top = top;
    }
    @Override
    public void update() {
        if(time <= 0.0f){
            time = 0.0f;
        }
        else{
            time -= MainGame.get().frameTime;
            min = time / 60.0f;
            sec = time % 60;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        int nw = bitmap.getWidth() / 11;
        int nh = bitmap.getHeight();
        int x = right;
        float dw = nw * GameView.MULTIPLIER;
        float dh = nh * GameView.MULTIPLIER;

        rectSrc =  new Rect();

        int iMin = (int)this.min;
        int iSec = (int)this.sec;

        // draw Min
        rectSrc.set(iMin * nw, 0, (iMin + 1)* nw, nh);
        rectDst.set(x, top, x + dw, top + dh);
        canvas.drawBitmap(bitmap, rectSrc, rectDst, null);
        x += dw;

        rectSrc.set(10 * nw, 0, (11)* nw, nh);
        rectDst.set(x, top, x + dw, top + dh);
        canvas.drawBitmap(bitmap, rectSrc, rectDst, null);
        x += dw;

        // 역순으로 그려지도록
        int value = iSec;
        x += dw;
        int count = 0;
        while(value >= 0){
            int digit = value % 10;
            rectSrc.set(digit * nw, 0, (digit + 1)* nw, nh);
            rectDst.set(x, top, x + dw, top + dh);
            x -= dw;
            canvas.drawBitmap(bitmap, rectSrc, rectDst, null);
            // 0도 그려지도록 하자
            if(value > 0) {
                value /= 10;
            }
            else {
                value = -1;
            }
            if(count > 0){
                value = -1;
            }
            ++count;
        }
    }

    @Override
    public void movePosition(float xMoved, float yMoved) {

    }
}
