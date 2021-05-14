package kr.ac.kpu.s2015182034.cookierun.framework.bitmap;

import android.graphics.Canvas;
import android.graphics.Rect;

import kr.ac.kpu.s2015182034.cookierun.framework.View.GameView;

public class AnimationGameBitmap extends GameBitmap {
    protected final int imageWidth;
    protected final int imageHeight;
    protected int frameWidth;
    protected final long createOn;
    protected int frameCount;
    protected final float framePerSecond;

    protected int frameIndex;
    protected Rect srcRect = new Rect();

    public AnimationGameBitmap(int resId, float framePerSecond, int frameCount){
        super(resId);
        bitmap = GameBitmap.load(resId);

        imageWidth = bitmap.getWidth();
        imageHeight = bitmap.getHeight();

        if(frameCount == 0){
            frameCount = imageWidth / imageHeight;
        }

        frameWidth = imageWidth / frameCount;
        this.frameCount = frameCount;
        this.framePerSecond = framePerSecond;
        createOn = System.currentTimeMillis();
        frameIndex = 0;
    }

    //public void update(){
    //    int elapsed = (int)(System.currentTimeMillis() - createOn);
    //    frameIndex = Math.round(elapsed * framePerSecond * 0.001f) % frameCount;
    //}

    @Override
    public void draw(Canvas canvas, float x, float y){
        int elapsed = (int)(System.currentTimeMillis() - createOn);
        frameIndex = Math.round(elapsed * framePerSecond * 0.001f) % frameCount;

        int fw = frameWidth;
        int h = imageHeight;

        float hw = fw / 2 * GameView.MULTIPLIER;// 원래 이미지 크기의 4배
        float hh = h / 2 *  GameView.MULTIPLIER;

        srcRect.set(fw * frameIndex, 0, fw  * (frameIndex + 1), h);
        dstRect.set(x - hw,y - hh, x + hw, y + hh);

        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }

    @Override
    public int getWidth(){
        return frameWidth;
    }

    @Override
    public int getHeight() {
        return imageHeight;
    }
}
