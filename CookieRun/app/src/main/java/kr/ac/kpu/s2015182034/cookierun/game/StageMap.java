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
    public StageMap(String filename){
        widgetH = GameView.view.getHeight();
        AssetManager asset = GameView.view.getContext().getAssets();
        try {
            InputStream is = asset.open(filename);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);

            String header = reader.readLine();
            // split()의 인자 regex
            String[] comps = header.split(" ");
            columns = Integer.parseInt(comps[0]);
            rows = Integer.parseInt(comps[1]);
            Log.d(TAG, "Col = " + columns + ", rows = " + rows);
            while(true){
                String line = reader.readLine();
                if(line == null){
                    break;
                }
                lines.add(line);
                Log.d(TAG, "Line = " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void update() {
        MainGame game = (MainGame) BaseGame.get();
        createComlumns();

//        ArrayList<GameObject> objects = game.objectsAt(MainGame.Layer.platform);
//        float rightMost = 0;
//        for (GameObject obj: objects) {
//            Platform platform = (Platform) obj;
//            float right = platform.getRight();
//            if (rightMost < right) {
//                rightMost = right;
//            }
//        }
//        float vw = GameView.view.getWidth();
//        float vh = GameView.view.getHeight();
//        if (rightMost < vw) {
//            Log.d(TAG, "create a Platform here !! @" + rightMost + " Platforms=" + objects.size());
//            float tx = rightMost, ty = vh - Platform.Type.T_2x2.height();
//            Platform platform = new Platform(Platform.Type.RANDOM, tx, ty);
//            game.add(MainGame.Layer.platform, platform);
//
//            Random r = new Random();
//            game.add(MainGame.Layer.item, new Jelly(r.nextInt(60), tx, r.nextInt((int) ty)));
//        }
    }

    // 파일에서 읽어온 내용을 바탕으로 세로로 젤리를 한 줄 만들어내는 함수!!
    private void createComlumns() {
        if(current > 100) {
            return;
        }
        //float y = widgetH - Platform.Type.T_2x2.height();
        float y = 0;
        for(int row = 0; row < rows; ++row){
            char ch = getAt(current, row);
            createObject(ch, xPos, y);
            y += Platform.UNIT_SIZE * GameView.MULTIPLIER;
        }
        xPos += Platform.UNIT_SIZE * GameView.MULTIPLIER;
        ++current;
    }

    private void createObject(char ch, float x, float y) {
        MainGame game = (MainGame) BaseGame.get();
        if(ch >= '1' && ch <= '9'){
            game.add(MainGame.Layer.item, new Jelly(ch - '1', x, y));
            return;
        }
        if(ch >= 'O' && ch <= 'Q'){
            game.add(MainGame.Layer.platform, new Platform(Platform.Type.values()[ch - 'O'], x, y));
            return;
        }
    }

    private char getAt(int x, int y) {
        try {
            int lineIndex = x / columns * rows + y;
            String line = lines.get(lineIndex);
            return line.charAt(x % columns);
        }
        catch(Exception e){ // 정상적으로 읽지 못하였을 때
            return 0;
        }
    }

    @Override
    public void draw(Canvas canvas) {
    }
}