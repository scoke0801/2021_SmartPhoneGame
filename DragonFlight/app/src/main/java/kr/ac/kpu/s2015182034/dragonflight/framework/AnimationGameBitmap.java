package kr.ac.kpu.s2015182034.dragonflight.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class AnimationGameBitmap extends GameBitmap {
    private static final int PIXEL_MULTIPLIER = 4;
    private Bitmap bitmap;
    private final int imageWidth;
    private final int imageHeight;
    private final int frameWidth;
    private final long createOn;
    private final int frameCount;
    private final float framePerSecond;

    private int frameIndex;

    public AnimationGameBitmap(int resId, float framePerSecond, int frameCount){
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

        int hw = fw / 2 * PIXEL_MULTIPLIER;// 원래 이미지 크기의 4배
        int hh = h / 2 * PIXEL_MULTIPLIER;

        Rect src = new Rect(fw * frameIndex, 0, fw  * (frameIndex + 1), h);
        RectF dst = new RectF(x - hw,y - hh, x + hw, y + hh);

        canvas.drawBitmap(bitmap, src, dst, null);

        // testing
        //Paint paint = new Paint();
        //paint.setColor(0xFFFF0000);
        //canvas.drawRect(dst, paint);
    }

    public int getWidth(){
        return frameWidth* PIXEL_MULTIPLIER;
    }
    public int getHeight(){
        return imageHeight * PIXEL_MULTIPLIER;
    }
}
