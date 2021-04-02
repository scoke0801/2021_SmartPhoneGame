package kr.ac.kpu.s2015182034.samplegame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.logging.Handler;

public class GameView extends View {
    // final 변수는 생성자에서 그 값이 결정되어야 한다.
    private static final String TAG = GameView.class.getSimpleName();

    private Ball b1;
    private Ball b2;

    // public : 외부에서 모르고도 접근할 수 있도록
    // static: 외부에서 해당 view 객체를 모르고도 접근할 수 있도록
    public static float frameTime;
    private long lastFrame;

    public static GameView view;

    // 생성자 종류, 필요에 맞게 정의 필요
    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        GameView.view = this;

        InitResources();

        startUpdating();
    }

    private void startUpdating() {
        doGameFrame();
    }

    private void doGameFrame() {
        // update();   // 계산
        b1.update();
        b2.update();

        // draw();     // 그리기
        // invalidate()함수는 여러번 중첩 호출되어도 한번에 그림으로써 중첩 호출을 해결
        invalidate();

        Choreographer.getInstance().postFrameCallback(
                new Choreographer.FrameCallback() {
                    @Override
                    public void doFrame(long time) {
                        if(lastFrame == 0) {
                            lastFrame = time;
                        }
                        // time의 단위가 나노 초임... fraem 계산은 밀리 초..
                        frameTime = (float)(time - lastFrame) / 1_000_000_000;
                        doGameFrame();
                        lastFrame = time;
                    }
                });
    }
    private void InitResources() {
        b1 = new Ball(100, 100, 100, 200);
        b2 = new Ball(850, 100, -50, 150);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        b1.draw(canvas);
        b2.draw(canvas);
        //Log.d(TAG,  "Drawing at : " + x + " y : " + y + " ft = " + frameTime);
    }
}
