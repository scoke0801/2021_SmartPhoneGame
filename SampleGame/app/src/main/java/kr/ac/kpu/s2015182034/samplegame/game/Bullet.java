package kr.ac.kpu.s2015182034.samplegame.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import kr.ac.kpu.s2015182034.samplegame.R;
import kr.ac.kpu.s2015182034.samplegame.framework.GameObject;
import kr.ac.kpu.s2015182034.samplegame.ui.view.GameView;

public class Bullet implements GameObject {
    private final float angle;
    //private final float speed;
    private float x, y;   // 위치
    private float dx, dy; // 속도

    private static int sx, sy; // 크기

    private static float radius;

    Paint paint = new Paint();

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

        paint.setColor(0xFFFF0000);
    }

    public void update() {
        float frameTime = MainGame.get().frameTime;
        this.x += this.dx * frameTime;
        this.y += this.dy * frameTime;

        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();

        boolean toBeDeleted = false;

        float left = x - sx / 2;
        float top = y - sy / 2;

        if(left < 0 || left + sx > w){
            toBeDeleted = true;
        }
        if(top < 0 || top + sy > h){
            toBeDeleted = true;
        }
        if( toBeDeleted ){
            MainGame.get().remove(this);
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(x,y,radius, paint);
    }
}
