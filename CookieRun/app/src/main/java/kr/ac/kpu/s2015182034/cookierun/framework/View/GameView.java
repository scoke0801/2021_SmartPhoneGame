package kr.ac.kpu.s2015182034.cookierun.framework.View;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import kr.ac.kpu.s2015182034.cookierun.framework.game.BaseGame;
import kr.ac.kpu.s2015182034.cookierun.framework.utils.Sound;

public class GameView extends View {
    // final 변수는 생성자에서 그 값이 결정되어야 한다.
    private static final String TAG = GameView.class.getSimpleName();
    private boolean running;

    // public : 외부에서 모르고도 접근할 수 있도록
    // static: 외부에서 해당 view 객체를 모르고도 접근할 수 있도록

    private long lastFrame;

    public static GameView view;
    public static float MULTIPLIER = 2;

    // 생성자 종류, 필요에 맞게 정의 필요
    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        GameView.view = this;

        Sound.init(context);
        this.running = true;
    }

    private void update() {
        BaseGame game = BaseGame.get();
        game.update();

        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        BaseGame game = BaseGame.get();
        boolean justInitialized = game.initResources();
        if (justInitialized) {
            requestCallback();
        }
    }
    private void requestCallback() {
        if(!this.running){
            return;
        }
        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long time) {
                if (lastFrame == 0) {
                    lastFrame = time;
                }
                BaseGame game = BaseGame.get();
                game.frameTime = (float) (time - lastFrame) / 1_000_000_000;
                update();
                lastFrame = time;
                requestCallback();
            }
        });
    }
    @Override
    protected void onDraw(Canvas canvas) {
        BaseGame.get().draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return BaseGame.get().onTouchEvent(event);
    }

    public void pauseGame(){
        this.running = false;
    }

    public void resumeGame(){
        if(!running){
            this.running = true;
            lastFrame = 0;
            requestCallback();
        }
    }
}
