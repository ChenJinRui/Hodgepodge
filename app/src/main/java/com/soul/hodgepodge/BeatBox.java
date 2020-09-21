package com.soul.hodgepodge;

import android.content.Context;
import android.content.res.AssetManager;
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

    private AssetManager mAssetManager;

    private List<Sound> mSounds = new ArrayList<>();
    public BeatBox(Context context){
        mAssetManager = context.getAssets();
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
                mSounds.add(sound);
            }
        } catch (IOException e) {
            Log.e(TAG,"Could not list assets " ,e);
        }
    }

    public List<Sound> getSounds(){
        return mSounds;
    }
}
