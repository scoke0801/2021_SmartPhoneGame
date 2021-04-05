package kr.ac.kpu.s2015182034.samplegame.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import kr.ac.kpu.s2015182034.samplegame.R;
import kr.ac.kpu.s2015182034.samplegame.framework.GameObject;
import kr.ac.kpu.s2015182034.samplegame.ui.view.GameView;

public class Player implements GameObject {
    private float x, y;   // 위치
    private float dx, dy; // 속도

    private int sx, sy; // 크기

    private float tx, ty; //target 위치

    private float speed;

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
    }

    public void moveTo(float x, float y){
        this.tx = x;
        this.ty = y;
    }
    public void update() {
        float delta_x = tx - x;
        float delta_y = ty - y;
        float distance = (float)Math.sqrt(delta_x * delta_x + delta_y * delta_y);
        float move_dist = speed * MainGame.get().frameTime;

        if (distance < move_dist){
            // 목표 위치와의 거리 차 비교
            this.x = tx;
            this.y = ty;
            return;
        }

        float angle = (float)Math.atan2(delta_y, delta_x);
        float mx = (float)(move_dist * Math.cos(angle));
        float my = (float)(move_dist * Math.sin(angle));

        this.x += mx;
        this.y += my;
    }

    public void draw(Canvas canvas) {
        float left = x - sx / 2;
        float top = y - sy / 2;
        canvas.drawBitmap(bitmap, left, top, null);
    }
}
