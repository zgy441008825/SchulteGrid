package com.zou.schultegrid.sound;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.zou.schultegrid.R;

import org.reactivestreams.Subscriber;
import org.xutils.x;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * ************************************************************
 * description:  <br>
 * packageName:com.zou.schultegrid.sound mode:SchulteGrid <br>
 * created by 邹高原 on 2018/11/12 16:29<br>
 * last modified time：2018/11/12 16:29 <br>
 *
 * @author 邹高原
 * ************************************************************
 */
public class MusicHelper {

    /**
     * 用于播放提示音
     */
    private SoundPool soundPool;

    /**
     * 播放背景音
     */
    private MediaPlayer mediaPlayer;


    private static MusicHelper instance = new MusicHelper();

    private Map<String, Integer> soundMap = new HashMap<>();

    public static final String MUSIC_KEY_RIGHT = "right";
    public static final String MUSIC_KEY_ERROT = "error";

    public static MusicHelper getInstance() {
        return instance;
    }

    public void init() {
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        soundMap.put(MUSIC_KEY_RIGHT, soundPool.load(x.app(), R.raw.right, 1));
        soundMap.put(MUSIC_KEY_ERROT, soundPool.load(x.app(), R.raw.error, 1));
        prepareGameBg();
//        Flowable.create((FlowableOnSubscribe<Void>) emitter -> {
//            emitter.requested();
//
//            emitter.onNext(null);
//        }, BackpressureStrategy.BUFFER)
//                .subscribeOn(Schedulers.newThread())
//                .subscribe();
    }

    public void play(String key) {
        if (soundMap.containsKey(key) && soundMap.get(key) != null) {
            soundPool.play(soundMap.get(key), 1, 1, 1, 0, 1);
        }
    }

    public void releaseSound() {
        soundMap.clear();
        soundPool.release();
    }

    private void prepareGameBg() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(x.app(), R.raw.game_bg);
            mediaPlayer.setLooping(true);
        }
    }

    public void playGameBg() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public void pauseGameBg() {
        if (mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }

    public void stopGameBg() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}
