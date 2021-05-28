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

public class MainGame extends BaseGame {
    private boolean initialized;
    private Player player;
    public Score score;

    public static MainGame get() {
        return (MainGame) instance;
    }
    public enum Layer {
        bg, platform, item, player, ui, controller, LAYER_COUNT
    }

    public void add(Layer layer, GameObject obj) {
        add(layer.ordinal(), obj);
    }

    public ArrayList<GameObject> objectsAt(Layer layer) {
        return objectsAt(layer.ordinal());
    }

    @Override
    public boolean initResources() {
        if (initialized) {
            return false;
        }
        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();

        initLayers(Layer.LAYER_COUNT.ordinal());

        add(Layer.controller, new StageMap("stage_01.txt"));

        player = new Player(Platform.Type.T_2x2.width(), h / 2);
        //layers.get(Layer.player.ordinal()).add(player);
        add(Layer.player, player);
//        add(Layer.controller, new EnemyGenerator());

        add(Layer.controller, new CollisionChecker(player));

        int margin = (int) (20 * GameView.MULTIPLIER);
        score = new Score(w - margin, margin);
        score.setScore(0);
        add(Layer.ui, score);

        add(Layer.bg, new HorizontalScrollBackground(R.mipmap.cookie_run_bg_1, -10));
        add(Layer.bg, new HorizontalScrollBackground(R.mipmap.cookie_run_bg_2, -20));
        add(Layer.bg, new HorizontalScrollBackground(R.mipmap.cookie_run_bg_3, -30));

        initialized = true;
        return true;

    }

    @Override
    public void update() {
        super.update();

        // collision check
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            if (event.getX() < GameView.view.getWidth() / 2) {
                player.jump();
            } else {
                player.startSliding();
            }
            return true;
        } else if (action == MotionEvent.ACTION_UP) {
            player.endSliding();
        }
        return false;
    }
}