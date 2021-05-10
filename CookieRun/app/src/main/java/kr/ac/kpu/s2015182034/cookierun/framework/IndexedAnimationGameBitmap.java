package kr.ac.kpu.s2015182034.cookierun.framework;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;

import kr.ac.kpu.s2015182034.cookierun.framework.View.GameView;

public class IndexedAnimationGameBitmap extends AnimationGameBitmap{

    private float frameHeight;
    protected ArrayList<Rect> srcRects;

    public IndexedAnimationGameBitmap(int resId, float framePerSecond, int frameCount) {
        super(resId, framePerSecond, frameCount);
        this.frameWidth = 270;
        this.frameHeight = 270;
       // setIndices(100, 101, 102, 103); // 100의 자리를 y좌표, 10의 자리를 x좌표로 계산
    }
    @Override
    public void draw(Canvas canvas, float x, float y){
        int elapsed = (int)(System.currentTimeMillis() - createOn);
        frameIndex = Math.round(elapsed * framePerSecond * 0.001f) % frameCount;

        int fw = frameWidth;
        int h = imageHeight;

        float hw = fw / 2 * GameView.MULTIPLIER;
        float hh = h / 2 *  GameView.MULTIPLIER;

        dstRect.set(x - hw,y - hh, x + hw, y + hh);

        canvas.drawBitmap(bitmap, srcRects.get(frameIndex), dstRect, null);
    }

    @Override
    public int getWidth(){
        return frameWidth;
    }

    @Override
    public int getHeight() {
        return imageHeight;
    }

    // 가변인자
    public void setIndices(int... indices){
        srcRects = new ArrayList<>();
        for(int index : indices){
            int x = index % 100;
            int y = index / 100;
            int orderSize = 2;

            int l = orderSize + x * 272;
            int t = orderSize + y * 272;

            int r = l + 270;
            int b = t + 270;

            srcRects.add(new Rect(l, t, r, b));
        }
        frameCount = indices.length;
    }
}
