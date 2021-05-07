package kr.ac.kpu.s2015182034.dragonflight.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.s2015182034.dragonflight.UI.View.GameView;
import kr.ac.kpu.s2015182034.dragonflight.framework.GameBitmap;
import kr.ac.kpu.s2015182034.dragonflight.framework.GameObject;

public class HorizontalScrollBackground implements GameObject {
    private final Bitmap bitmap;

    private Rect srcRect = new Rect();
    private RectF dstRect = new RectF();
    private float speed;
    private float scroll;

    public HorizontalScrollBackground(int resId, int speed) {
        bitmap = GameBitmap.load(resId);
        this.speed = speed * GameView.MULTIPLIER;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        srcRect.set(0, 0, w, h);
        float l = 0;
        float t = 0;

        float b = GameView.view.getHeight();
        float r = b * h / w;
        //W:r = h :b
        dstRect.set(l, t, r, b);
    }
    @Override
    public void update() {
        MainGame game = MainGame.get();
        float moveAmount = speed * game.frameTime;
        scroll += moveAmount;
        //dstRect.top += moveAmount;
        //dstRect.bottom += moveAmount;
    }

    @Override
    public void draw(Canvas canvas) {
        int vw = GameView.view.getWidth();
        int vh = GameView.view.getHeight();
        int iw = bitmap.getWidth();
        int ih = bitmap.getHeight();

        float dh = vw * ih / iw;
        float dw = vh * ih / iw;
        int curr = (int)(scroll % dw);
        if(curr > 0) curr -= dw;

        while(curr < vw){
            dstRect.set(curr, 0, curr + dw, vh);
            canvas.drawBitmap(bitmap, srcRect, dstRect, null);
            curr += dh;
        }

    }
}