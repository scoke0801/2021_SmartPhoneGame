package kr.ac.kpu.s2015182034.cookierun.framework.object;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.s2015182034.cookierun.framework.View.GameView;
import kr.ac.kpu.s2015182034.cookierun.framework.bitmap.GameBitmap;
import kr.ac.kpu.s2015182034.cookierun.framework.game.BaseGame;
import kr.ac.kpu.s2015182034.cookierun.framework.iface.GameObject;

public class VerticalScrollBackground implements GameObject {
    private final Bitmap bitmap;

    private Rect srcRect = new Rect();
    private RectF dstRect = new RectF();
    private float speed;
    private float scroll;

    public VerticalScrollBackground(int resId, int speed) {
        bitmap = GameBitmap.load(resId);
        this.speed = speed * GameView.MULTIPLIER;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        srcRect.set(0, 0, w, h);
        float l = 0;
        float t = 0;

        float r = GameView.view.getWidth();;
        float b = r * h / w;
        //W:r = h :b
        dstRect.set(l, t, r, b);
    }
    @Override
    public void update() {
        BaseGame game = BaseGame.get();
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
        int curr = (int)(scroll % dh);
        if(curr > 0) curr -= dh;

        while(curr < vh){
            dstRect.set(0, curr, vw, curr + dh);
            canvas.drawBitmap(bitmap, srcRect, dstRect, null);
            curr += dh;
        }

    }

}