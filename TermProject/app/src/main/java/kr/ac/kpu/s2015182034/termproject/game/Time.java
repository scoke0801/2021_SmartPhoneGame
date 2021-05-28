package kr.ac.kpu.s2015182034.termproject.game;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.s2015182034.termproject.R;
import kr.ac.kpu.s2015182034.termproject.animation.GameBitmap;
import kr.ac.kpu.s2015182034.termproject.framework.GameObject;

// 타이머가 아님.
// Score 클래스처럼 남은 시간을 표현하기 위하여 사용할 것
public class Time implements GameObject {
    private final Bitmap bitmap;
    private final int right;
    private final int top;

    private float time = 0.0f;
    private Rect rectSrc = new Rect();
    private RectF rectDst = new RectF();
    public Time(int right, int top){
        bitmap = GameBitmap.load(R.mipmap.score);
        this.right = right;
        this.top = top;
    }
    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void movePosition(float xMoved, float yMoved) {

    }
}
