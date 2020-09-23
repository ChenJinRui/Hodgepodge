package com.soul.hodgepodge.viewmodel.beatbox;

import com.soul.hodgepodge.BeatBox;
import com.soul.hodgepodge.bean.beatbox.Sound;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Chjr on 2020/9/22
 */
public class SoundViewModelTest {
    private BeatBox mBeatBox;
    private Sound mSound;
    //mSubject 测试类命名提高复用时效率（不用mSoundViewModel 的原因）
    private SoundViewModel mSubject;

    @Before
    public void setUp() throws Exception {
        //mock(BeatBox.class);创建虚拟的BeatBox 对象
        mBeatBox = mock(BeatBox.class);
        mSound = new Sound("assetPath");
        mSubject = new SoundViewModel(mBeatBox);
        mSubject.setSound(mSound);
    }
    @Test
    public void exposeSoundNameAsTitle(){
        //判断getTitle 和getName 获取的值是否相同
        assertThat(mSubject.getTitle(),is(mSound.getName()));
    }
    @Test
    public void callsBeatBoxPlayOnButtonClicked(){
        mSubject.onButtonClicked();
        //验证MBeatBox的play的方法以mSound为参数
        verify(mBeatBox).play(mSound);
    }
}