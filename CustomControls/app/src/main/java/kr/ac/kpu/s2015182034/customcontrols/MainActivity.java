package kr.ac.kpu.s2015182034.customcontrols;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //MyView mv = new MyView(this);
        //setContentView(mv);
    }

    // 방법 1
    //@Override
    //public boolean onTouchEvent(MotionEvent event) {
    //   return super.onTouchEvent(event);
    //}
}