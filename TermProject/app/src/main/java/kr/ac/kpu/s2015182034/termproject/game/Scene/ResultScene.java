package kr.ac.kpu.s2015182034.termproject.game.Scene;


import android.view.MotionEvent;

import kr.ac.kpu.s2015182034.termproject.R;
import kr.ac.kpu.s2015182034.termproject.framework.BaseGame;
import kr.ac.kpu.s2015182034.termproject.framework.GameObject;
import kr.ac.kpu.s2015182034.termproject.game.GameOverBoard;
import kr.ac.kpu.s2015182034.termproject.game.ImageObject;
import kr.ac.kpu.s2015182034.termproject.ui.view.GameView;

public class ResultScene extends Scene {
    enum Layer {
        ui, COUNT
    }
    public static ResultScene scene;
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

        add(Layer.ui, new GameOverBoard(w / 2, 0));
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            BaseGame.get().popScene();
            BaseGame.get().push(new TitleScene());
        }
        return super.onTouchEvent(e);
    }
}
