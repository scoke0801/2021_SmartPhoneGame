package kr.ac.kpu.s2015182034.termproject.game;

import android.graphics.Canvas;

import kr.ac.kpu.s2015182034.termproject.R;
import kr.ac.kpu.s2015182034.termproject.animation.GameBitmap;
import kr.ac.kpu.s2015182034.termproject.framework.GameObject;
import kr.ac.kpu.s2015182034.termproject.framework.MainGame;
import kr.ac.kpu.s2015182034.termproject.ui.view.GameView;

public class GameOverBoard implements GameObject {
    private float x, y;   // 위치
    private float ty; // 목표지점
    private float speed = 100.0f; // 이동 속도
    private int sx, sy; // 크기

    private boolean isLeft;
    private GameBitmap bitmap;

    // 해당 시간이 지나면 타이틀 화면 or 메인 게임 화면으로 지나가도록
    private final float LIFE_TIME = 5.0f;
    // 화면 중앙까지 이동하는데 걸려야 할 시간
    private final float TO_MOVE_TIME = 2.5f;
    
    // 화면 중앙에 도달한 이후 지난 시간
    private float elapsedTime = 0.0f;
    boolean isOnMove = true;

    public GameOverBoard(float x, float y) {
        // 상단에서부터 화면 중간까지...
        this.x = x;
        this.y = y;

        if (bitmap == null) {
            bitmap = new GameBitmap(R.mipmap.game_over);
        }
        sx = bitmap.getWidth();
        sy = bitmap.getHeight();

        // 목표지점 계산...
        float vh = GameView.view.getHeight();
        ty = y + vh * 0.5f;
        speed = vh * 0.5f / 1.25f;
    }
    @Override
    public void update() {
        float frameTime = MainGame.get().frameTime;
        if(isOnMove){
            y += speed * frameTime;
            if(y > ty){
                isOnMove = false;
            }
        }
        else{
            // 시간 확인
            elapsedTime += frameTime;
            if(elapsedTime > LIFE_TIME){
                // to do
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        bitmap.draw(canvas, x, y);
    }

    @Override
    public void movePosition(float xMoved, float yMoved) {

    }
}
