package kr.ac.kpu.s2015182034.cookierun.game;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import kr.ac.kpu.s2015182034.cookierun.framework.View.GameView;
import kr.ac.kpu.s2015182034.cookierun.framework.game.BaseGame;
import kr.ac.kpu.s2015182034.cookierun.framework.iface.GameObject;

public class StageMap implements GameObject {
    private static final String TAG = StageMap.class.getSimpleName();
    private final ArrayList<String> lines = new ArrayList<String>();
    private int columns;
    private int rows;
    private int current;
    private float xPos = 0;
    private float widgetH;
    public static int UNIT_SIZE = 70;
    public StageMap(String filename) {
        AssetManager assets = GameView.view.getContext().getAssets();
        try {
            InputStream is = assets.open(filename);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            String header = reader.readLine();
            String[] comps = header.split(" ");
            columns = Integer.parseInt(comps[0]);
            rows = Integer.parseInt(comps[1]);
            UNIT_SIZE = (int) Math.ceil(GameView.view.getHeight() / rows / GameView.MULTIPLIER);
            Log.d(TAG, "Col=" + columns + " Row="  + rows + " UnitSize=" + UNIT_SIZE);
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                Log.d(TAG,  "-row=" + line);
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void update() {
        MainGame game = (MainGame) BaseGame.get();
        xPos -= Platform.SPEED * GameView.MULTIPLIER * game.frameTime;
        float vw = GameView.view.getWidth();
        while (xPos < vw) {
            //Log.d(TAG, "xPos=" + xPos + " column=" + current);
            createColumn();
            xPos += UNIT_SIZE * GameView.MULTIPLIER;
        }
    }

    private void createColumn() {
        float y = 0;
        for (int row = 0; row < rows; row++) {
            char ch = getAt(current, row);
            createObject(ch, xPos, y);
            y += UNIT_SIZE * GameView.MULTIPLIER;
        }
        current++;
    }

    private void createObject(char ch, float x, float y) {
        MainGame game = (MainGame) BaseGame.get();
        if (ch >= '1' && ch <= '9') {
            Jelly item = new Jelly(ch - '1', x, y);
            game.add(MainGame.Layer.item, item);
        } else if (ch >= 'O' && ch <= 'Q') {
            Platform platform = new Platform(Platform.Type.values()[ch - 'O'], x, y);
            game.add(MainGame.Layer.platform, platform);
        }
    }

    private char getAt(int x, int y) {
        try {
            int lineIndex = x / columns * rows + y;
            String line = lines.get(lineIndex);
            return line.charAt(x % columns);
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean isDone() {
        int lineIndex = current / columns * rows;
        return lines.size() <= lineIndex;
    }

    @Override
    public void draw(Canvas canvas) {
    }
}