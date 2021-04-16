package kr.ac.kpu.s2015182034.dragonflight.framework;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;

import kr.ac.kpu.s2015182034.dragonflight.UI.View.GameView;

public class GameBitmap {
    private static HashMap<Integer, Bitmap> bitmaps = new HashMap<Integer, Bitmap>();

    public static Bitmap load(int resId) {
        Bitmap bitmap = bitmaps.get(resId);
        if (bitmap == null) {
            Resources res = GameView.view.getResources();

            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inScaled = false;

            bitmap = BitmapFactory.decodeResource(res, resId, opts);
            bitmaps.put(resId, bitmap);
        }
        return bitmap;
    }
}
