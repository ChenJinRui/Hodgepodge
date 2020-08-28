package com.soul.hodgepodge.ui.crime;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import com.soul.hodgepodge.dialog.DatePickerOptimizeFragment;

import java.util.Date;

public class DatePickerFragmentActivity extends BaseFragmentActivity {

    private static final String KEY_DATE = "date";

    @Override
    protected Fragment createFragment() {
        Date date = (Date) getIntent().getSerializableExtra(KEY_DATE);
        return DatePickerOptimizeFragment.newInstance(date);
    }
    public static Intent newIntent(Context context, Date date){
        Intent intent = new Intent(context, DatePickerFragmentActivity.class);
        intent.putExtra(KEY_DATE,date);
        return intent;
    }
}