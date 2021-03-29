package kr.ac.kpu.s2015182034.samplegame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.logging.Handler;

public class GameView extends View {
    // final 변수는 생성자에서 그 값이 결정되어야 한다.
    private static final String TAG = GameView.class.getSimpleName();

    private Bitmap bitmap;

    private float x;
    private float y;
    // Handler handler = new Handler();

    // 생성자 종류, 필요에 맞게 정의 필요
    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        InitResources();

        startUpdating();
    }

    private void startUpdating() {
        doGameFrame();
    }

    private void doGameFrame() {
        // update();   // 계산
        x += 1;
        y += 2;
        // draw();     // 그리기
        // invalidate()함수는 여러번 중첩 호출되어도 한번에 그림으로써 중첩 호출을 해결
        invalidate();

        // handler.postDelayed(new Runnable() {
        //    @Override
        //    public void run() {
        //        doGameFrame();
        //    }
        //}, 15);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                doGameFrame();
            }
        } , 15);
    }
    private void InitResources() {
        Resources res = getResources();
        bitmap = BitmapFactory.decodeResource(res, R.mipmap.soccer_ball_240);

        x = 100;
        y = 100;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap,x, y, null);
        Log.d(TAG,  "Drawing at : " + x + " y : " + y);
    }
}
