package com.soul.hodgepodge.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.soul.hodgepodge.R;
import com.soul.hodgepodge.utils.LogUtils;

import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public

/*
 * 时间选择弹窗
 * Created by Chjr on 2020/8/18
 *
 */

// TODO: 2020/8/19  第12章 响应式DialogFragment 练习
class DatePickerOptimizeFragment extends DialogFragment {

    private static final String ARG_DATE = "date";
    public static final String EXTRA_DATE = "com.soul.hodgepodge.dialog.DatePickerFragment.date";

    private DatePicker mDatePicker ;
    private Button mButton;
    public static DatePickerOptimizeFragment newInstance(Date date){
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_DATE,date);

        DatePickerOptimizeFragment fragment = new DatePickerOptimizeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    private DatePickerOptimizeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_date_optimize,container,false);
        mDatePicker = v.findViewById(R.id.dialog_date_picker);
        mButton = v.findViewById(R.id.dialog_button_sure);

        Date date = (Date) getArguments().getSerializable(ARG_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        mDatePicker.init(year,month,day,null);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = mDatePicker.getYear();
                int month = mDatePicker.getMonth();
                int day = mDatePicker.getDayOfMonth();

                Date date1 = new GregorianCalendar(year,month,day).getTime();
                sendResult(Activity.RESULT_OK,date1);
            }
        });
        return v;
    }



    private void sendResult(int resultCode,Date date){
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE,date);
        getActivity().setResult(resultCode,intent);
        getActivity().finish();
    }

}
