package kr.ac.kpu.s2015182034.termproject.framework;

import android.graphics.Canvas;

public interface GameObject {
    public void update();
    public void draw(Canvas canvas);

    public void movePosition(float xMoved, float yMoved);
}
