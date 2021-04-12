package kr.ac.kpu.s2015182034.samplegame.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.s2015182034.samplegame.R;
import kr.ac.kpu.s2015182034.samplegame.framework.GameObject;
import kr.ac.kpu.s2015182034.samplegame.ui.view.GameView;

public class Bullet implements GameObject {
    private final long createOn;
    //private final float speed;
    private float x, y;   // 위치
    private float dx, dy; // 속도
    private float angle;

    private static int sx, sy; // 크기

    private static float radius;
    private static Bitmap bitmap;

    private int frameIndex = 0;

    private static float FRAME_RATE = 8.5f; // 1초당 8.5장의 속도로 애니메이션을 수행하도록
    private static int FRAME_COUNT = 10;

    public Bullet(float x, float y, float tx, float ty) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.radius = 10.0f;
        //this.speed = 1000;

        float delta_x = tx - this.x;
        float delta_y = ty - this.y;

        this.angle = (float)Math.atan2(delta_y, delta_x);

        float move_dist = 1000;// speed * MainGame.get().frameTime;
        this.dx = (float)(move_dist * Math.cos(angle));
        this.dy = (float)(move_dist * Math.sin(angle));

        if(bitmap == null) {
            Resources res = GameView.view.getResources();
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inScaled = false;
            bitmap = BitmapFactory.decodeResource(res, R.mipmap.laser_light, opts);
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

        boolean toBeDeleted = false;

        int frameWidth = sx / FRAME_COUNT;
        if(x < 0 || x > w - frameWidth){
            toBeDeleted = true;
        }
        if(y < 0 || y > h - sy){
            toBeDeleted = true;
        }
        if( toBeDeleted ){
            MainGame.get().remove(this);
        }

        int elapsed = (int)(System.currentTimeMillis() - createOn);
        frameIndex = Math.round(elapsed * FRAME_RATE * 0.001f) % FRAME_COUNT;
    }

    public void draw(Canvas canvas) {
        int w = sx;
        int h = sy;

        int fw = w / FRAME_COUNT;

        int hw = 100;
        int hh = 124;
        float ballRadius = 100.0f;

        Rect src = new Rect(fw * frameIndex, 0, fw  * (frameIndex + 1), h);
        RectF dst = new RectF(x - hw,y - hh, x + hw, y + hh);

        float degree = (float)(this.angle * 180.0f / Math.PI) + 90;
        canvas.save();
        canvas.rotate(degree, x, y);
        canvas.drawBitmap(bitmap, src, dst, null);
        canvas.restore();
    }
}
