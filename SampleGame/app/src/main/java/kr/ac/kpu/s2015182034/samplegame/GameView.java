package kr.ac.kpu.s2015182034.samplegame;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends View {
    // final 변수는 생성자에서 그 값이 결정되어야 한다.
    private static final String TAG = GameView.class.getSimpleName();
    private static final int BALL_COUNT = 10;

    Player player;
    ArrayList<GameObject> objects = new ArrayList<>();

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
        for(GameObject object : objects) {
            object.update();
        }
        //draw();     // 그리기
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
        Random rand = new Random();
        for(int i = 0; i < BALL_COUNT; ++i){
            //float x = rand.nextInt(getWidth());
            //float y = rand.nextInt(getHeight());
            float x = rand.nextInt(1000);
            float y = rand.nextInt(1000);
            float dx = rand.nextFloat() * 1000 - 500;
            float dy = rand.nextFloat() * 1000 - 500;
            Ball ball = new Ball(x,y, dx, dy);
            objects.add(ball);
        }

        player = new Player(450, 750, 0,0);
        objects.add(player);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for(GameObject object : objects) {
            object.draw(canvas);
        }
        //Log.d(TAG,  "Drawing at : " + x + " y : " + y + " ft = " + frameTime);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch(action){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                player.moveTo(event.getX(), event.getY());
                return true;
            case MotionEvent.ACTION_UP:
                return true;
            default:
                break;
        }
        return false;
    }
}
