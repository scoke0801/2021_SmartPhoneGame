package kr.ac.kpu.s2015182034.cookierun.game;

import kr.ac.kpu.s2015182034.cookierun.R;
import kr.ac.kpu.s2015182034.cookierun.framework.View.GameView;
import kr.ac.kpu.s2015182034.cookierun.framework.object.ImageObject;

public class Platform extends ImageObject {
    public enum PlatformType{
        // Pt : PlatformType
        PT_10x2, PT_2x2, PT_3x1
    }
    public Platform(PlatformType type, float x, float y) {
        super(GetResId(type), x, y);

        final float BASE_SIZE = 40 * GameView.MULTIPLIER;
        float w = BASE_SIZE * 10;
        float h = BASE_SIZE * 2;
        if(type == PlatformType.PT_10x2){
            w = BASE_SIZE * 10;
            h = BASE_SIZE * 2;
        }
        else if(type == PlatformType.PT_2x2){
            w = BASE_SIZE * 2;
            h = BASE_SIZE * 2;
        }
        else if(type == PlatformType.PT_3x1){
            w = BASE_SIZE * 3;
            h = BASE_SIZE * 1;
        }

        dstRect.set(x, y, x + w, y + h);
    }
     private static int GetResId(PlatformType type){
         int resId = -1;
         if(type == PlatformType.PT_10x2){
             resId = R.mipmap.cookierun_platform_480x48;
         }
         else if(type == PlatformType.PT_2x2){
             resId = R.mipmap.cookierun_platform_124x120;
         }
         else if(type == PlatformType.PT_3x1){
             resId = R.mipmap.cookierun_platform_120x40;
         }
         return resId;
    }
}
