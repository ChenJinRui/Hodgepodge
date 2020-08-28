package com.soul.hodgepodge.ui.crime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.soul.hodgepodge.R;
import com.soul.hodgepodge.bean.crime.CrimeBean;
import com.soul.hodgepodge.data.crime.CrimeLab;
import com.soul.hodgepodge.dialog.DatePickerFragment;
import com.soul.hodgepodge.fragment.crime.CrimeFragment;
import com.soul.hodgepodge.utils.LogUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;


/**
 * 实现层级是导航 在子Activity 的manifest配置中添加：返回上级页面（CrimeListActivity）
 * android:parentActivityName=".ui.crime.CrimeListActivity"
 * Intent intent = new Intent(this,CrimeListActivity.class);
 * intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
 * startActivity(intent);
 * finish();
 * FLAG_ACTIVITY_CLEAR_TOP 在回退栈中寻找指定目标Activity实例，如果存在，弹出在他之上的所有Activity,
 * 并将目标Activity置栈顶显示
 */
public class CrimePageActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<CrimeBean> mCrimeBeans;
    private FragmentStatePagerAdapter adapter;

    private static final String ARG_CRIME_ID = "com.soul.hodgepodge.ui.crime.crime_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_page);

        initView();
    }


    public static Intent newIntent(Context context ,UUID uuid){
        Intent intent = new Intent(context,CrimePageActivity.class);
        intent.putExtra(ARG_CRIME_ID,uuid);
        return intent;
    }

    private void initView() {
        //ViewPager的布局不支持 margin
        mViewPager = findViewById(R.id.activity_crime_pager_view_pager);
        mCrimeBeans = CrimeLab.getInstance(getApplicationContext()).getCrimeBeans();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fragmentManager
                ,FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                CrimeBean crimeBean = mCrimeBeans.get(position);
                return CrimeFragment.newInstance(crimeBean.getID());
            }

            @Override
            public int getCount() {
                return mCrimeBeans.size();
            }
        });
        /**
         * FragmentPagerAdapter 和 FragmentStatePagerAdapter
         * FragmentStatePagerAdapter会销毁不在需要的Fragment可以在onSaveInstanceState(bundle)
         * 中保存信息，相对节省内存
         * FragmentPagerAdapter 只是销毁了视图 Fragment的实例还在FragmentManger中 不会被销毁
         */
//        adapter = new FragmentStatePagerAdapter(fragmentManager,
//                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
//            @Override
//            public int getCount() {
//                return mCrimeBeans.size();
//            }
//
//            @NonNull
//            @Override
//            public Fragment getItem(int position) {
//                CrimeBean crimeBean = mCrimeBeans.get(position);
//                pos = position;
//                return CrimeFragment.newInstance(crimeBean.getID());
//            }
//        };
//        mViewPager.setAdapter(adapter);
        if(null != getIntent()){
            UUID crimeID = (UUID) getIntent().getSerializableExtra(ARG_CRIME_ID);
            for (int i = 0; i < mCrimeBeans.size(); i++) {
                if(crimeID.equals(mCrimeBeans.get(i).getID())){
                    mViewPager.setCurrentItem(i);
                    break;
                }
            }
        }
    }

}