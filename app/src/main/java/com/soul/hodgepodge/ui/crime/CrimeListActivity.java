package com.soul.hodgepodge.ui.crime;

import android.util.DisplayMetrics;

import androidx.fragment.app.Fragment;

import com.soul.hodgepodge.R;
import com.soul.hodgepodge.fragment.crime.CrimeListFragment;
import com.soul.hodgepodge.utils.LogUtils;

// TODO: 2020/8/13 158 
public class CrimeListActivity extends BaseFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    @Override
    protected int getLayoutResID() {
        getInfo();
//        return R.layout.activity_twopane;
        return R.layout.activity_masterdetail;
    }

    private void getInfo() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // 屏幕宽度（像素）
        int height = metric.heightPixels;  // 屏幕高度（像素）
        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）


        LogUtils.e("width : " + width);
        LogUtils.e("height : " + height);
        LogUtils.e("density : " + density);
        LogUtils.e("densityDpi : " + densityDpi);
    }
}