package kr.ac.kpu.s2015182034.cookierun.game.scenes;


import android.view.MotionEvent;

import kr.ac.kpu.s2015182034.cookierun.R;
import kr.ac.kpu.s2015182034.cookierun.framework.View.GameView;
import kr.ac.kpu.s2015182034.cookierun.framework.game.Scene;
import kr.ac.kpu.s2015182034.cookierun.framework.iface.GameObject;
import kr.ac.kpu.s2015182034.cookierun.framework.object.ImageObject;
import kr.ac.kpu.s2015182034.cookierun.game.MainGame;
import kr.ac.kpu.s2015182034.cookierun.game.Player;

public class SecondScene extends Scene {
    enum Layer {
        bg, player, COUNT
    }
    public static SecondScene scene;
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

        add(Layer.bg, new ImageObject(R.mipmap.cookie_run_bg_1, w/2, h/2));
        add(Layer.player, new Player(w/2, h/2));
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            MainGame.get().popScene();
        }
        return super.onTouchEvent(e);
    }
}
