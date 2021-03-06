package kr.ac.kpu.s2015182034.dragonflight.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.s2015182034.dragonflight.UI.View.GameView;

public class AnimationGameBitmap extends GameBitmap {
    private final int imageWidth;
    private final int imageHeight;
    private final int frameWidth;
    private final long createOn;
    private final int frameCount;
    private final float framePerSecond;

    private int frameIndex;
    private Rect srcRect = new Rect();

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

    public int getWidth(){
        return frameWidth;
    }
    public int getHeight() {
        return imageHeight;
    }
}
