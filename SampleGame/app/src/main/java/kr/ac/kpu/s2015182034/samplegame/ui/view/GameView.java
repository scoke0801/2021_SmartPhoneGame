package kr.ac.kpu.s2015182034.samplegame.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import kr.ac.kpu.s2015182034.samplegame.game.MainGame;

public class GameView extends View {
    // final 변수는 생성자에서 그 값이 결정되어야 한다.
    private static final String TAG = GameView.class.getSimpleName();

    // public : 외부에서 모르고도 접근할 수 있도록
    // static: 외부에서 해당 view 객체를 모르고도 접근할 수 있도록

    private long lastFrame;

    public static GameView view;

    // 생성자 종류, 필요에 맞게 정의 필요
    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        GameView.view = this;

        MainGame game = MainGame.get();
        //game.InitResources();

        startUpdating();
    }

    private void startUpdating() {
        doGameFrame();
    }

    private void doGameFrame() {
        MainGame.get().update();
        invalidate();

        Choreographer.getInstance().postFrameCallback(
                new Choreographer.FrameCallback() {
                    @Override
                    public void doFrame(long time) {
                        if(lastFrame == 0) {
                            lastFrame = time;
                        }
                        // time의 단위가 나노 초임... fraem 계산은 밀리 초..
                        MainGame.get().frameTime = (float)(time - lastFrame) / 1_000_000_000;
                        doGameFrame();
                        lastFrame = time;
                    }
                });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        MainGame.get().InitResources();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        MainGame.get().draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return MainGame.get().onTouchEvent(event);
    }
}
