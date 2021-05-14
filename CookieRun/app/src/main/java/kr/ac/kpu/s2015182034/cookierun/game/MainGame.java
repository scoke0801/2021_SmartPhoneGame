package kr.ac.kpu.s2015182034.cookierun.game;

import android.view.MotionEvent;

import kr.ac.kpu.s2015182034.cookierun.R;
import kr.ac.kpu.s2015182034.cookierun.framework.View.GameView;
import kr.ac.kpu.s2015182034.cookierun.framework.game.BaseGame;
import kr.ac.kpu.s2015182034.cookierun.framework.iface.GameObject;
import kr.ac.kpu.s2015182034.cookierun.framework.object.HorizontalScrollBackground;

public class MainGame extends BaseGame {
    public enum Layer{
        bg, platform, player, ui, controller, COUNT
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

        player = new Player(200, h - 750);
        add(Layer.player, player);

        int margin =  (int)(20 * GameView.MULTIPLIER);
        score = new Score(w - margin, margin);
        score.setScore(0);
        add(Layer.ui, score);

        // parallax scrolling
        add(Layer.bg, new HorizontalScrollBackground(R.mipmap.cookie_run_bg_1, -40));
        add(Layer.bg, new HorizontalScrollBackground(R.mipmap.cookie_run_bg_2, -20));
        add(Layer.bg, new HorizontalScrollBackground(R.mipmap.cookie_run_bg_3, -10));

        float tx = 0.0f, ty = h - 100;
        while(tx < w){
            Platform platform = new Platform(Platform.PlatformType.PT_10x2, tx, ty);
            add(Layer.platform, platform);

            tx += platform.getDstWidth();
        }
        tx = 200f;
        while(tx < w - 200){
            Platform platform = new Platform(Platform.PlatformType.PT_2x2, tx, ty - 300);
            add(Layer.platform, platform);

            tx += platform.getDstWidth();
        }
        tx = 400.0f;
        while(tx < w- 400){
            Platform platform = new Platform(Platform.PlatformType.PT_3x1, tx, ty - 600);
            add(Layer.platform, platform);

            tx += platform.getDstWidth();
        }
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
            player.Jump();
            //player.moveTo(event.getX(), event.getY());
            return true;
        }
        return false;
    }
}
