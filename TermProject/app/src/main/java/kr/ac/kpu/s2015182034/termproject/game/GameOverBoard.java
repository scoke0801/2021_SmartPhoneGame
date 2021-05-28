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
    private final float speed = 100.0f; // 이동 속도
    private int sx, sy; // 크기

    private boolean isLeft;
    private GameBitmap bitmap;

    // 해당 시간이 지나면 타이틀 화면 or 메인 게임 화면으로 지나가도록
    private final float LIFE_TIME = 5.0f;
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
        ty = y - vh * 0.5f;
        
    }
    @Override
    public void update() {
        if(isOnMove){

        }
        else{
            // 시간 확인
            elapsedTime += MainGame.get().frameTime;
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
