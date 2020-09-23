package com.soul.hodgepodge.bean.beatbox;

public
/**
 * Created by Chjr on 2020/9/21
 *
 */
class Sound {

    private String mAssetPath;
    private String mName;
    private Integer mSoundId;//Integer在没有值时可以设置为null

    public Sound (String assetPath){
        mAssetPath = assetPath;
        String [] components = assetPath.split("/");
        String filename = components[components.length - 1];
        mName = filename.replace(".wav","");
    }

    public String getAssetPath() {
        return mAssetPath;
    }

    public String getName() {
        return mName;
    }

    public Integer getSoundId() {
        return mSoundId;
    }

    public void setSoundId(Integer soundId) {
        mSoundId = soundId;
    }
}
