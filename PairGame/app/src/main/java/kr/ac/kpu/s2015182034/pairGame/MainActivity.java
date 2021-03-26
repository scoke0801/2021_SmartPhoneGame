package kr.ac.kpu.s2015182034.pairGame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int[] btnIds = {
            R.id.imgBtn01, R.id.imgBtn02, R.id.imgBtn03, R.id.imgBtn04, R.id.imgBtn05,
            R.id.imgBtn11, R.id.imgBtn12, R.id.imgBtn13, R.id.imgBtn14, R.id.imgBtn15,
            R.id.imgBtn21, R.id.imgBtn22, R.id.imgBtn23, R.id.imgBtn24, R.id.imgBtn25,
            R.id.imgBtn31, R.id.imgBtn32, R.id.imgBtn33, R.id.imgBtn34, R.id.imgBtn35,
    };
    private int cards[] = {
            R.mipmap.joker, R.mipmap.joker, R.mipmap.lalaland, R.mipmap.lalaland, R.mipmap.lh_riches,
            R.mipmap.lh_riches, R.mipmap.monster, R.mipmap.monster, R.mipmap.new_world, R.mipmap.new_world,
            R.mipmap.avengers, R.mipmap.avengers, R.mipmap.parasite, R.mipmap.parasite, R.mipmap.to_busan,
            R.mipmap.to_busan, R.mipmap.veteran, R.mipmap.veteran, R.mipmap.frozen, R.mipmap.frozen,
    };

    private ImageButton prevButton;
    private int visibleCarCount;
    private TextView scoreTextView;

    public void setScore(int score) {
        this.score = score;
        scoreTextView.setText("Flips: " + this.score);
    }

    private int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreTextView = findViewById(R.id.scoreTextView);

        Log.d(TAG, "onCreate Called");

        startGame();
    }

    public void onBtnCard(View view){
        if(view == prevButton){
            int color = getResources().getColor(R.color.purple_700);
            scoreTextView.setTextColor(color);
            return;
        }
        int color = getResources().getColor(R.color.gray);
        scoreTextView.setTextColor(color);

        int prevCard = 0;
        if(prevButton != null) {
            prevButton.setImageResource(R.mipmap.movie_back);
            prevCard = (Integer)prevButton.getTag();
        }

        int buttonIndex = getButtionIndex(view.getId());
        Log.d(TAG, "onBtnCard() has been called. ID =" +  view.getId() + "buttonIndex = " + buttonIndex);

        int card = cards[buttonIndex];
        ImageButton imageButton = (ImageButton)view;
        imageButton.setImageResource(card);

        if(card == prevCard){
            imageButton.setVisibility(View.INVISIBLE);
            prevButton.setVisibility(View.INVISIBLE);
            prevButton = null;
            visibleCarCount -= 2;
            if(visibleCarCount <= 0) {
                askRestart();
            }
            return;
        }
        if (prevButton != null) {
            setScore(score + 1);
        }
        prevButton = imageButton;
    }

    private int getButtionIndex(int resId){
        for(int i = 0; i < btnIds.length; ++i) {
            if(btnIds[i] == resId)
                return i;
        }
        return -1;
    }

    public void onBtnRestart(View view) {
        askRestart();
    }

    private void askRestart() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.restart_dialog_title);
        builder.setMessage(R.string.restart_dialog_message);
        builder.setPositiveButton(R.string.common_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startGame();
            }
        });
        builder.setNegativeButton(R.string.common_no, null);
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void startGame() {
        Random random = new Random();
        for(int i =0; i < cards.length; i++){
            int ri = random.nextInt(cards.length);  // 0 ~ 15
            int t = cards[i];
            cards[i] = cards[ri];
            cards[ri] = t;
        }

        for(int i = 0; i < btnIds.length; i++){
            ImageButton b = findViewById(btnIds[i]);
            b.setTag(cards[i]);
            b.setVisibility(View.VISIBLE);
            b.setImageResource(R.mipmap.movie_back);
        }

        prevButton = null;
        visibleCarCount = cards.length;

        setScore(0);
    }
}