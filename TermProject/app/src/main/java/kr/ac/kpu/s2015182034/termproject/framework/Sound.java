package kr.ac.kpu.s2015182034.termproject.framework;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

import java.util.HashMap;

import kr.ac.kpu.s2015182034.termproject.R;

// shader Bimtap
// Sound pool
public class Sound {
    private static final String TAG = Sound.class.getSimpleName();
    private static SoundPool soundPool;
    private static HashMap<Integer, Integer> soundIdMap = new HashMap<Integer, Integer>();
    private static MediaPlayer bgm;
    private static Context context;
    private static final int[] SOUND_IDS = {
            R.raw.bgm, R.raw.e_blinker, R.raw.e_game_over,
            R.raw.e_move, R.raw.e_water_fall, R.raw.e_barrior,
            R.raw.e_coin
    };

    public static void init(Context context){
        AudioAttributes audioAttributes[] = new AudioAttributes[2];
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)  {
            audioAttributes[0] = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            audioAttributes[1] = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes[0])
                    .setMaxStreams(10)
                    .build();
        }
        else{
           soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        }
        for(int resId : SOUND_IDS){
            int soundId = soundPool.load(context, resId, 1);
            soundIdMap.put(resId, soundId);
        }

        Sound.context = context;
        bgm = MediaPlayer.create(context, R.raw.bgm);
        bgm.start();
    }
    public static int play(int resId){
        Log.d(TAG, "Sound.play" + resId);
        int soundId = soundIdMap.get(resId);
        int streamId = soundPool.play(soundId, 1f, 1f, 1, 0, 1f);
        return streamId;
    }
    public static void playBgm(){
        bgm.stop();
        bgm = MediaPlayer.create(context, R.raw.bgm);
        bgm.start();
    }
}
