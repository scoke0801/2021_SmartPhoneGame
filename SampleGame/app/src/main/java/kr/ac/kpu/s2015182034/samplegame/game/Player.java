package kr.ac.kpu.s2015182034.samplegame.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;

import kr.ac.kpu.s2015182034.samplegame.R;
import kr.ac.kpu.s2015182034.samplegame.framework.GameObject;
import kr.ac.kpu.s2015182034.samplegame.ui.view.GameView;

public class Player implements GameObject {
    private final MediaPlayer mediaPlayer;

    private float x, y;   // 위치
    private float dx, dy; // 속도

    private int sx, sy; // 크기

    private float tx, ty; //target 위치

    private float speed;

    private float angle = 0;

    private Bitmap bitmap;

    public Player(float x, float y, float dx, float dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.tx = 0;
        this.ty = 0;
        this.speed = 800;
        if(bitmap == null) {
            Resources res = GameView.view.getResources();
            bitmap = BitmapFactory.decodeResource(res, R.mipmap.plane_240);
            sx = bitmap.getWidth();
            sy = bitmap.getHeight();
        }

        // 어트리뷰트에는 일단 콘텍스트에는 넘겨주도록...
        mediaPlayer = MediaPlayer.create(GameView.view.getContext(), R.raw.hadouken);
    }

    public void moveTo(float x, float y){
        mediaPlayer.seekTo(0);
        mediaPlayer.start();

        Bullet bullet = new Bullet(this.x, this.y, x, y);
        MainGame.get().add(bullet);

        this.tx = x;
        this.ty = y;

        float delta_x = tx - this.x;
        float delta_y = ty - this.y;

        this.angle = (float)Math.atan2(delta_y, delta_x);
    }
    public void update() {
        x += dx;
        y += dy;

        if((dx > 0 && this.x > tx)||(dx < 0 && this.x < tx)) {
            x = tx;
            dx = 0;
        }
        if((dy > 0 && this.y > ty)||(dy < 0 && this.y < ty)) {
            y = ty;
            dy = 0;
        }
    }

    public void draw(Canvas canvas) {
        float left = x - sx / 2;
        float top = y - sy / 2;
        float degree = (float)(this.angle * 180.0f / Math.PI) + 90;

        canvas.save();

        canvas.rotate(degree, x, y);
        canvas.drawBitmap(bitmap, left, top, null);

        canvas.restore();
    }
}
