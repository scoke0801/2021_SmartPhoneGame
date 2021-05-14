package kr.ac.kpu.s2015182034.cookierun.game;

import android.view.MotionEvent;

import kr.ac.kpu.s2015182034.cookierun.R;
import kr.ac.kpu.s2015182034.cookierun.framework.View.GameView;
import kr.ac.kpu.s2015182034.cookierun.framework.game.BaseGame;
import kr.ac.kpu.s2015182034.cookierun.framework.iface.GameObject;
import kr.ac.kpu.s2015182034.cookierun.framework.object.HorizontalScrollBackground;

public class MainGame extends BaseGame {
    public enum Layer{
        bg,bg2,bg3, enemy, bullet, player, ui, controller, COUNT
    }
    private Player player;
    private Score score;

    private boolean initialized;

    public void add(Layer layer, GameObject gameObject){
        add(layer.ordinal(), gameObject);
    }
    @Override
    public boolean initResources() {
        if (initialized) {
            return false;
        }

        initLayers(Layer.COUNT.ordinal());

        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();

        player = new Player(w/2, h - 750);
        add(Layer.player, player);

        int margin =  (int)(20 * GameView.MULTIPLIER);
        score = new Score(w - margin, margin);
        score.setScore(0);
        add(Layer.ui, score);

        // parallax scrolling
        add(Layer.bg, new HorizontalScrollBackground(R.mipmap.cookie_run_bg_1, -40));
        add(Layer.bg2, new HorizontalScrollBackground(R.mipmap.cookie_run_bg_2, -20));
        add(Layer.bg3, new HorizontalScrollBackground(R.mipmap.cookie_run_bg_3, -10));

        initialized = true;
        return true;
    }

    @Override
    public void update(){
        super.update();
        
        // 충돌처리는 여기서
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            player.moveTo(event.getX(), event.getY());
            return true;
        }
        return false;
    }
}
