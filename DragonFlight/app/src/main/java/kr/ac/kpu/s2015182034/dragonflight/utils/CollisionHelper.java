package kr.ac.kpu.s2015182034.dragonflight.utils;

import android.graphics.RectF;

import kr.ac.kpu.s2015182034.dragonflight.framework.BoxCollidable;

public class CollisionHelper {
    public static boolean collides(BoxCollidable objA, BoxCollidable objB){

        RectF rectA = objA.getBoundingRect();
        RectF rectB = objB.getBoundingRect();

        if (rectA.left > rectB.right) return false;
        if (rectA.top > rectB.bottom)return false;
        if(rectA. right < rectB.left) return false;
        return true;
    }
}
