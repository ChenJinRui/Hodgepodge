package com.soul.hodgepodge;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import com.soul.hodgepodge.bean.beatbox.Sound;
import com.soul.hodgepodge.utils.LogUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public
/**
 * Created by Chjr on 2020/9/21
 *
 */
class BeatBox {

    private static final String TAG = BeatBox.class.getSimpleName();
    private static final String SOUNDS_FOLDER = "sample_sounds";
    private static final int MAX_SOUNDS = 5;

    private AssetManager mAssetManager;
    private List<Sound> mSounds = new ArrayList<>();
    private SoundPool mSoundPool;

    public BeatBox(Context context){
        mAssetManager = context.getAssets();
        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC,0);
        loadSounds();
    }

    private void loadSounds(){
        String [] soundNames ;
        try {
            soundNames = mAssetManager.list(SOUNDS_FOLDER);
            assert soundNames != null ;
            LogUtils.i("Found " + soundNames.length + " sounds ");
            for (String soundName : soundNames) {
                String assetPath = SOUNDS_FOLDER + "/" + soundName;
                Sound sound = new Sound(assetPath);
                load(sound);
                mSounds.add(sound);
            }
        } catch (IOException e) {
            Log.e(TAG,"Could not list assets " ,e);
        }
    }
    private void load(Sound sound) throws IOException {
        AssetFileDescriptor afd = mAssetManager.openFd(sound.getAssetPath());
        int soundID = mSoundPool.load(afd,1);
        sound.setSoundId(soundID);
    }
    public void play(Sound sound){
        Integer soundID = sound.getSoundId();
        if(soundID == null){
            return;
        }
        //音频id，左音量，右音量，优先级（无效），是否循环（0-N，-1 无限循环 n 次），
        mSoundPool.play(soundID,1.0f,1.0f,1,0,1.0f);
    }
    public List<Sound> getSounds(){
        return mSounds;
    }
    public void release(){
        mSoundPool.release();
    }
}
