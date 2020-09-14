package com.soul.hodgepodge.ui.crime;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.soul.hodgepodge.R;

public abstract class BaseFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fragment_root);
        setContentView(getLayoutResID());

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container_root);
        if(null == fragment){
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_root,fragment)
                    .commit();
        }
    }
    @LayoutRes
    protected int getLayoutResID(){
        return  R.layout.activity_fragment_root;
    }
}