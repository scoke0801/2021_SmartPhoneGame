package kr.ac.kpu.s2015182034.termproject.framework;

import android.graphics.Canvas;
import android.graphics.RectF;

public interface GameObject {
    public void update();
    public void draw(Canvas canvas);
    public RectF getAABB();
    public void fixCollision();
}
