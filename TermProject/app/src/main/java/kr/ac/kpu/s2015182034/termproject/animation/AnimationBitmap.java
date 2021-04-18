package kr.ac.kpu.s2015182034.termproject.animation;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.s2015182034.termproject.ui.view.GameView;

public class AnimationBitmap extends GameBitmap  {
    private static final int PIXEL_MULTIPLIER = 1;
    private final Bitmap bitmap;
    private final int imageWidth;
    private final int imageHeight;
    private final int frameWidth;
    private final long createOn;
    private final int frameCount;
    private final float framePerSecond;

    private int frameIndex;
    private Paint paint;

    public AnimationBitmap(int resId, float framePerSecond, int frameCount){
        Resources res = GameView.view.getResources();

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inScaled = false;
        bitmap = BitmapFactory.decodeResource(res, resId, opts);

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

        paint = new Paint();
        paint.setColor(0xFFFF0000);
        paint.setStrokeWidth(10f);
        paint.setStyle(Paint.Style.STROKE);
    }

    public void draw(Canvas canvas, float x, float y){
        int elapsed = (int)(System.currentTimeMillis() - createOn);
        frameIndex = Math.round(elapsed * framePerSecond * 0.001f) % frameCount;

        int fw = frameWidth;
        int h = imageHeight;

        int hw = fw / 2 * PIXEL_MULTIPLIER;// 원래 이미지 크기의 4배
        int hh = h / 2 * PIXEL_MULTIPLIER;

        Rect src = new Rect(fw * frameIndex, 0, fw  * (frameIndex + 1), h);
        RectF dst = getAABB(x,y);

        canvas.drawBitmap(bitmap, src, dst, null);
    }

    public void drawAABB(Canvas canvas, float x, float y){
        RectF dst = getAABB(x,y);
        canvas.drawRect(dst, paint);
    }
    public RectF getAABB(float x, float y){
        int hw = getWidth() / 2;
        int hh = getHeight() / 2;
        RectF dst = new RectF(x - hw,y - hh, x + hw, y + hh);
        return dst;
    }

    public int getWidth(){
        return frameWidth* PIXEL_MULTIPLIER;
    }
    public int getHeight(){
        return imageHeight * PIXEL_MULTIPLIER;
    }
}
