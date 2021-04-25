package kr.ac.kpu.s2015182034.termproject.utils;

import android.graphics.RectF;

import kr.ac.kpu.s2015182034.termproject.framework.BoxCollidable;

public class CollisionHelper {
    private static RectF rect1 = new RectF();
    private static RectF rect2 = new RectF();
    public static boolean collides(BoxCollidable objA, BoxCollidable objB){

        objB.getBoundingRect(rect2);
        objA.getBoundingRect(rect1);

        if (rect1.left   > rect2.right) return false;
        if (rect1.top    > rect2.bottom)return false;
        if (rect1.right  < rect2.left)  return false;
        if (rect1.bottom < rect2.top)   return false;

        return true;
    }
}
