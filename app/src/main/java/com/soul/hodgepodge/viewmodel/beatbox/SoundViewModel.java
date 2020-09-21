package com.soul.hodgepodge.viewmodel.beatbox;

import com.soul.hodgepodge.BeatBox;
import com.soul.hodgepodge.bean.beatbox.Sound;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public
/**
 * Created by Chjr on 2020/9/21
 * extends BaseObservable 注解绑定熟悉调用notifyChange() /notifyPropertyChanged(int)
 */
class SoundViewModel extends BaseObservable {

    private Sound mSound;
    private BeatBox mBeatBox;

    public SoundViewModel(BeatBox beatBox) {
        mBeatBox = beatBox;
    }

    public Sound getSound() {
        return mSound;
    }

    public void setSound(Sound sound) {
        mSound = sound;
        //数据变更时通知绑定类view同步（每次调用setSound LIstItemSoundBinding 刷新Button.settext）
        notifyChange();
    }
    @Bindable
    public String getTitle(){
        return mSound.getName();
    }
}
