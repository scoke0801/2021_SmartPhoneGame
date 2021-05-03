package kr.ac.kpu.s2015182034.dragonflight.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.s2015182034.dragonflight.R;
import kr.ac.kpu.s2015182034.dragonflight.UI.View.GameView;
import kr.ac.kpu.s2015182034.dragonflight.framework.GameBitmap;
import kr.ac.kpu.s2015182034.dragonflight.framework.GameObject;

public class Score implements GameObject {

    private final Bitmap bitmap;
    private final int right;
    private final int top;

    private int score = 0, displayScore = 0;
    private Rect rectSrc = new Rect();
    private RectF rectDst = new RectF();

    public Score(int right, int top){
        bitmap = GameBitmap.load(R.mipmap.number_24x32);
        this.right = right;
        this.top = top;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        this.displayScore = score;
    }
    public void addScore(int score) {
        this.score += score;
    }
    @Override
    public void update() {
        if(displayScore < score){
            displayScore++;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        int value = this.displayScore;
        int nw = bitmap.getWidth() / 10;
        int nh = bitmap.getHeight();
        int x = right;
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
}
