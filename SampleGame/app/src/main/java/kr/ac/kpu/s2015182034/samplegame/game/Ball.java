package kr.ac.kpu.s2015182034.samplegame.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.s2015182034.samplegame.framework.GameObject;
import kr.ac.kpu.s2015182034.samplegame.R;
import kr.ac.kpu.s2015182034.samplegame.ui.view.GameView;

public class Ball implements GameObject {
    private final long createOn;
    private float x, y;   // 위치
    private float dx, dy; // 속도

    private static int sx, sy; // 크기

    private static Bitmap bitmap;

    private int frameIndex = 0;

    private static float FRAME_RATE = 8.5f; // 1초당 8.5장의 속도로 애니메이션을 수행하도록
    private static int FRAME_COUNT = 24;
    public Ball(float x, float y, float dx, float dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;

        if(bitmap == null) {
            Resources res = GameView.view.getResources();
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inScaled = false;
            bitmap = BitmapFactory.decodeResource(res, R.mipmap.fireball_128_24f, opts);
            sx = bitmap.getWidth();
            sy = bitmap.getHeight();
        }
        createOn = System.currentTimeMillis();
    }

    public void update() {
        float frameTime = MainGame.get().frameTime;
        this.x += this.dx * frameTime;
        this.y += this.dy * frameTime;

        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();

        float left = x - sy / 2;
        float top = y - sy / 2;

        int frameWidth = sy;
        if(x < 0 || x > w - frameWidth){
            dx *= -1;
        }
        if(y < 0 || y > h - sy){
            dy *= -1;
        }

        int elapsed = (int)(System.currentTimeMillis() - createOn);
        frameIndex = Math.round(elapsed * FRAME_RATE * 0.001f) % FRAME_COUNT;
        //frameIndex = (frameIndex + 1) % 24;
    }

    public void draw(Canvas canvas) {
        int w = sx;
        int h = sy;

        int fw = h;
        float ballRadius = 100.0f;

        Rect src = new Rect(fw * frameIndex, 0, fw  * (frameIndex + 1), h);
        RectF dst = new RectF(x - ballRadius,y - ballRadius, x + ballRadius, y + ballRadius);

        canvas.drawBitmap(bitmap, src, dst, null);
    }
}
