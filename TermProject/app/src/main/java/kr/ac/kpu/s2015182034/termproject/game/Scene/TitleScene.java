package kr.ac.kpu.s2015182034.termproject.game.Scene;


import android.view.MotionEvent;

import kr.ac.kpu.s2015182034.termproject.R;
import kr.ac.kpu.s2015182034.termproject.framework.BaseGame;
import kr.ac.kpu.s2015182034.termproject.framework.GameObject;
import kr.ac.kpu.s2015182034.termproject.framework.MainGame;
import kr.ac.kpu.s2015182034.termproject.game.VerticalScrollBackground;
import kr.ac.kpu.s2015182034.termproject.ui.view.GameView;

public class TitleScene extends Scene {
    enum Layer {
        bg, player, COUNT
    }
    public static TitleScene scene;
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


        add(Layer.bg, new VerticalScrollBackground(R.mipmap.map_1, 0));
        //add(Layer.player, new Player(w/2, h/2));
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            BaseGame.get().popScene();
        }
        return super.onTouchEvent(e);
    }
}
