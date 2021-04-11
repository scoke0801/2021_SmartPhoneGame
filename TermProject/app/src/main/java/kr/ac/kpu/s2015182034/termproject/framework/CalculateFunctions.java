package kr.ac.kpu.s2015182034.termproject.framework;

import static java.lang.Math.sqrt;

public class CalculateFunctions {
    public CalculateFunctions (){

    }
    static public float GetDistance(float x1, float x2, float y1, float y2){
        float x = (x1 - x2);
        float y = (y1 - y2);
        return (float)(sqrt((x * x) + (y * y)));
    }
}
