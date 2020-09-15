package com.soul.hodgepodge.ui.crime;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.soul.hodgepodge.bean.crime.CrimeBean;
import com.soul.hodgepodge.fragment.crime.CrimeFragment;

import java.util.UUID;

public class CrimeActivity extends BaseFragmentActivity implements CrimeFragment.Callbacks {

    public static final String KEY_UUID = "com.soul.hodgepodge.ui.crime.KEY_UUID";

    @Override
    protected Fragment createFragment() {
        UUID crimeID = (UUID) getIntent().getSerializableExtra(KEY_UUID);
        return CrimeFragment.newInstance(crimeID);
    }

    public static Intent newIntent(Context context, UUID id){
        Intent intent = new Intent(context,CrimeActivity.class);
        intent.putExtra(KEY_UUID,id);
        return intent;
    }


    @Override
    public void onCrimeUpdated(CrimeBean crimeBean) {

    }
}