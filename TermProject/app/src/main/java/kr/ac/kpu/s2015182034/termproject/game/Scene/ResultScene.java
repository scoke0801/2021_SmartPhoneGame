package kr.ac.kpu.s2015182034.termproject.game.Scene;


import android.view.MotionEvent;

import kr.ac.kpu.s2015182034.termproject.R;
import kr.ac.kpu.s2015182034.termproject.framework.BaseGame;
import kr.ac.kpu.s2015182034.termproject.framework.GameObject;
import kr.ac.kpu.s2015182034.termproject.framework.Sound;
import kr.ac.kpu.s2015182034.termproject.game.GameOverBoard;
import kr.ac.kpu.s2015182034.termproject.game.ImageObject;
import kr.ac.kpu.s2015182034.termproject.ui.view.GameView;

public class ResultScene extends Scene {
    enum Layer {
        ui, COUNT
    }
    public static ResultScene scene;
    private float elapsedTime = 0.0f;
    public void add(Layer layer, GameObject obj) {
        add(layer.ordinal(), obj);
    }
    @Override
    public void start() {
        super.start();
        transparent = true;
        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();
        initLayers(Layer.COUNT.ordinal());

        //add(Layer.bg, new ImageObject(R.mipmap.title, 0, 0));
        Sound.play(R.raw.e_game_over);
        add(Layer.ui, new GameOverBoard(w / 2, 0));
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            if(elapsedTime > 1.5f) {
                BaseGame.get().popSceneNoResume();
                BaseGame.get().popSceneNoResume();
                BaseGame.get().push(new TitleScene());
            }
        }
        return super.onTouchEvent(e);
    }

    @Override
    public void TimeUpdate(){
        elapsedTime += BaseGame.get().frameTime;
    }
}
