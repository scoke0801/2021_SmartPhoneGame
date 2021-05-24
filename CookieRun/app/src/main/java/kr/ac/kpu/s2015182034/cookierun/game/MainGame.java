package kr.ac.kpu.s2015182034.cookierun.game;

import android.content.res.AssetManager;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.kpu.s2015182034.cookierun.R;
import kr.ac.kpu.s2015182034.cookierun.framework.View.GameView;
import kr.ac.kpu.s2015182034.cookierun.framework.game.BaseGame;
import kr.ac.kpu.s2015182034.cookierun.framework.iface.GameObject;
import kr.ac.kpu.s2015182034.cookierun.framework.object.HorizontalScrollBackground;

public class MainGame extends BaseGame {
    public enum Layer{
        bg, platform, item, player, ui, controller, COUNT
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

        float y = h - Platform.Type.T_2x2.height() - 140;
        player = new Player(200, y);
        add(Layer.player, player);

        int margin =  (int)(20 * GameView.MULTIPLIER);
        score = new Score(w - margin, margin);
        score.setScore(0);
        add(Layer.ui, score);

        // parallax scrolling
        add(Layer.bg, new HorizontalScrollBackground(R.mipmap.cookie_run_bg_1, -40));
        add(Layer.bg, new HorizontalScrollBackground(R.mipmap.cookie_run_bg_2, -20));
        add(Layer.bg, new HorizontalScrollBackground(R.mipmap.cookie_run_bg_3, -10));

        // 텍스트 파일 역시 리소스로 등록을해서 사용을 하거나
        // 안드로이드에서 지원하는 에셋을 사용하거나
        // configuration에 따라서 다른 것들이 될 수 있는 컬러, 크기
        // 그런 내용들과 관련없는 파일들은 에셋에 담아 사용한다.
        add(Layer.controller, new StageMap("stage_01.txt"));

        float tx = 0.0f, ty = h - Platform.Type.T_10x2.height();
        while(tx < w){
            Platform platform = new Platform(Platform.Type.RANDOM, tx, ty);
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


    public ArrayList<GameObject> objectsAt(Layer layer) {
        return objectsAt(layer.ordinal());
    }
}
