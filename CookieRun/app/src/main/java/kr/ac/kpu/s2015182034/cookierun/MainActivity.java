package kr.ac.kpu.s2015182034.cookierun;

import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;

import kr.ac.kpu.s2015182034.cookierun.framework.View.GameView;
import kr.ac.kpu.s2015182034.cookierun.game.MainGame;

public class MainActivity extends AppCompatActivity {
    private MainGame game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        game = new MainGame(); // 이렇게 안해도 가비지컬렉터가 MainGame에 대해 동작하지는 않음
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        GameView.MULTIPLIER = metrics.density* 0.35f;
    }

    @Override
    protected void onPause() {
        GameView.view.pauseGame();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GameView.view.resumeGame();
    }
}