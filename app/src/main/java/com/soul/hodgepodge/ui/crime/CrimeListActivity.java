package com.soul.hodgepodge.ui.crime;

import androidx.fragment.app.Fragment;

import com.soul.hodgepodge.fragment.crime.CrimeListFragment;

// TODO: 2020/8/13 158 
public class CrimeListActivity extends BaseFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }


}