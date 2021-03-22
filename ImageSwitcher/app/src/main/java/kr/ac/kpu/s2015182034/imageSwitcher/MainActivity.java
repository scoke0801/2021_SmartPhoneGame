package kr.ac.kpu.s2015182034.imageSwitcher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    ImageButton btPrev, btNext;
    ImageView imageView;
    TextView textView;

    int catImageList[] = {R.mipmap.cat1, R.mipmap.cat2, R.mipmap.cat3, R.mipmap.cat4, R.mipmap.cat5 };
    String textList[] = {"1 / 5", "2 / 5", "3 / 5","4 / 5", "5 / 5"};
    int imageCount = catImageList.length;
    int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btPrev = findViewById(R.id.prevButton);
        btNext = findViewById(R.id.nextButton);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.mainTextView);

        btPrev.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN : {
                        BtnPrev(v, R.mipmap.prev_p, false);
                        break;
                    }
                    case MotionEvent.ACTION_UP   : {
                        BtnPrev(v, R.mipmap.prev, true);
                        break;
                    }
                }
                return false;
            }
        });

        btNext.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN : {
                        BtnNext(v, R.mipmap.next_p, false);
                        break;
                    }
                    case MotionEvent.ACTION_UP   : {
                        BtnNext(v, R.mipmap.next, true);
                        break;
                    }
                }
                return false;
            }
        });
    }

    public void BtnNext(View view, int btnImage, boolean nextImage) {
        btNext.setImageResource(btnImage);
        if(nextImage == false) {
            return;
        }

        currentIndex += 1;
        if(currentIndex >= imageCount){
            currentIndex = imageCount - 1;
        }
        imageView.setImageResource(catImageList[currentIndex]);
        textView.setText(textList[currentIndex]);
    }
    public void BtnPrev(View view, int btnImage, boolean nextImage) {
        btPrev.setImageResource(btnImage);
        if(nextImage == false) {
            return;
        }

        currentIndex -= 1;
        if(currentIndex < 0) {
            currentIndex = 0;
        }
        imageView.setImageResource(catImageList[currentIndex]);
        textView.setText(textList[currentIndex]);
    }
}