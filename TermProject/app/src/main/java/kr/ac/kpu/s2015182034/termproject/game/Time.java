package kr.ac.kpu.s2015182034.termproject.game;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.s2015182034.termproject.R;
import kr.ac.kpu.s2015182034.termproject.animation.GameBitmap;
import kr.ac.kpu.s2015182034.termproject.framework.GameObject;
import kr.ac.kpu.s2015182034.termproject.framework.MainGame;
import kr.ac.kpu.s2015182034.termproject.ui.view.GameView;

// 타이머가 아님.
// Score 클래스처럼 남은 시간을 표현하기 위하여 사용할 것
public class Time implements GameObject {
    private final Bitmap bitmap;
    private final int right;
    private final int top;

    private float time = 300.0f;
    private Rect rectSrc = new Rect();
    private RectF rectDst = new RectF();
    public Time(int right, int top){
        bitmap = GameBitmap.load(R.mipmap.score);
        this.right = right;
        this.top = top;
    }
    @Override
    public void update() {
        time -= MainGame.get().frameTime;
    }

    @Override
    public void draw(Canvas canvas) {
        int value = (int)this.time;
        int nw = bitmap.getWidth() / 10;
        int nh = bitmap.getHeight();
        int x = right + 20;
        float dw = nw * GameView.MULTIPLIER;
        float dh = nh * GameView.MULTIPLIER;

        rectSrc =  new Rect();
        while(value >= 0){
            int digit = value % 10;
            rectSrc.set(digit * nw, 0, (digit + 1)* nw, nh);
            rectDst.set(x, top, x + dw, top + dh);
            x -= dw;
            canvas.drawBitmap(bitmap, rectSrc, rectDst, null);
            value /= 10;
            if (value == 0) break;
        }
    }

    @Override
    public void movePosition(float xMoved, float yMoved) {

    }
}
