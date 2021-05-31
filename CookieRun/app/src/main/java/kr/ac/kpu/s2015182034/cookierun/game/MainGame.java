package kr.ac.kpu.s2015182034.cookierun.game;

import android.content.res.AssetManager;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.kpu.s2015182034.cookierun.R;
import kr.ac.kpu.s2015182034.cookierun.framework.View.GameView;
import kr.ac.kpu.s2015182034.cookierun.framework.game.BaseGame;
import kr.ac.kpu.s2015182034.cookierun.framework.iface.BoxCollidable;
import kr.ac.kpu.s2015182034.cookierun.framework.iface.GameObject;
import kr.ac.kpu.s2015182034.cookierun.framework.object.HorizontalScrollBackground;
import kr.ac.kpu.s2015182034.cookierun.framework.utils.CollisionHelper;
import kr.ac.kpu.s2015182034.cookierun.game.scenes.MainScene;

public class MainGame extends BaseGame {
    private boolean initialized;

    public static MainGame get() {
        return (MainGame) instance;
    }


    @Override
    public boolean initResources() {
        if (initialized) {
            return false;
        }

        push(new MainScene());

        initialized = true;
        return true;

    }

}