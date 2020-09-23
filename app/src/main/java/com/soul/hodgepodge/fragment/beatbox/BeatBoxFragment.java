package com.soul.hodgepodge.fragment.beatbox;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soul.hodgepodge.BeatBox;
import com.soul.hodgepodge.R;
import com.soul.hodgepodge.bean.beatbox.Sound;
import com.soul.hodgepodge.databinding.FragmentBeatBoxBinding;
import com.soul.hodgepodge.databinding.ListItemSoundBinding;
import com.soul.hodgepodge.viewmodel.beatbox.SoundViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public
/**
 * Created by Chjr on 2020/9/21
 * DataBinding 是 Google 在 Jetpack 中推出的一款数据绑定的支持库
 */
class BeatBoxFragment extends Fragment {
    private BeatBox mBeatBox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //保留Fragment 使其实持有例对象不被销毁（BeatBox）（旋转等操作时） 只销毁Fragment的视图
        setRetainInstance(true);

        mBeatBox = new BeatBox(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FragmentBeatBoxBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_beat_box,container,false);
        //binding.beatBoxRecyclerView = beat_box_recycler_view
        binding.beatBoxRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        binding.beatBoxRecyclerView.setAdapter(new SoundAdapter(mBeatBox.getSounds()));
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBeatBox.release();
    }

    public static BeatBoxFragment newInstance(){
        return new BeatBoxFragment();
    }

    private class SoundHolder extends RecyclerView.ViewHolder{

        private ListItemSoundBinding mBinding;
        /**
         * dataBinding {
         *         enabled = true
         *     }
         */
        public SoundHolder(ListItemSoundBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.setViewModel(new SoundViewModel(mBeatBox));
        }

        /**
         * DataBinding 是 Google 在 Jetpack 中推出的一款数据绑定的支持库
         * dataBinding {
         *         enabled = true
         *     }
         */
        public void bind (Sound sound){
            mBinding.getViewModel().setSound(sound);

            //立即刷新数据 一般不需要
            mBinding.executePendingBindings();
        }

    }

    private class SoundAdapter extends RecyclerView.Adapter<SoundHolder>{
        private List<Sound> mSounds;

        public SoundAdapter(List<Sound> sounds) {
            mSounds = sounds;
        }

        @NonNull
        @Override
        public SoundHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            ListItemSoundBinding binding = DataBindingUtil
                    .inflate(inflater,R.layout.list_item_sound,parent,false);

            return new SoundHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull SoundHolder holder, int position) {
            Sound sound = mSounds.get(position);
            holder.bind(sound);
        }

        @Override
        public int getItemCount() {
            return mSounds.size();
        }
    }


}
