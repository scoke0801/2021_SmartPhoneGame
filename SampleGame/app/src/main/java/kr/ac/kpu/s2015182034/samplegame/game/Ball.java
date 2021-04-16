package kr.ac.kpu.s2015182034.samplegame.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.Random;

import kr.ac.kpu.s2015182034.samplegame.framework.AnimationGameBitmap;
import kr.ac.kpu.s2015182034.samplegame.framework.GameObject;
import kr.ac.kpu.s2015182034.samplegame.R;
import kr.ac.kpu.s2015182034.samplegame.ui.view.GameView;

public class Ball implements GameObject {
    private float x, y;   // 위치
    private float dx, dy; // 속도

    private static float FRAME_RATE = 8.5f; // 1초당 8.5장의 속도로 애니메이션을 수행하도록
    private AnimationGameBitmap bitmap;
    public Ball(float x, float y, float dx, float dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;

        Random r = new Random();
        // FRAME_RATE의 90 ~ 110 % 사이의 값을 랜덤하게
        float frameRate = FRAME_RATE * (r.nextFloat() * 0.2f + 0.9f);
        bitmap = new AnimationGameBitmap(R.mipmap.fireball_128_24f, FRAME_RATE, 0);
    }

    public void update() {
        float frameTime = MainGame.get().frameTime;
        this.x += this.dx * frameTime;
        this.y += this.dy * frameTime;

        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();

        int frameWidth = bitmap.getWidth();
        int frameHeight = bitmap.getHeight();
        if(x < 0 || x > w - frameWidth){
            dx *= -1;
        }
        if(y < 0 || y > h - frameHeight){
            dy *= -1;
        }
    }

    public void draw(Canvas canvas) {
        bitmap.draw(canvas, x, y);
    }
}
