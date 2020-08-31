package com.soul.hodgepodge.fragment.crime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.soul.hodgepodge.R;
import com.soul.hodgepodge.bean.crime.CrimeBean;
import com.soul.hodgepodge.data.crime.CrimeLab;
import com.soul.hodgepodge.dialog.DatePickerFragment;
import com.soul.hodgepodge.ui.crime.DatePickerFragmentActivity;
import com.soul.hodgepodge.utils.LogUtils;

import java.util.Date;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public
/**
 * Created by Chjr on 2020/8/13
 */
// TODO: 2020/8/14 192
class CrimeFragment extends Fragment {
    private CrimeBean mCrime;
    private EditText mTitleField;
    private Button mDateBtn;
    private Button mDeleteBtn;
    private CheckBox mSolvedCb;

    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;
    /**
     *
     * @param crimeID id 可以根据需要修改为其他参数 可以是Object 可以是基本类型
     *                args.putSerializable(OBJECT ,object);
     *                args.putChar(CHAR,char);
     *                ...
     *                获取数据时调用getArgument() 再使用对应的getSerializable(),getChar()等方法
     *
     * @return 附加 argument bundle 给 fragment.setArgument(Bundle)，
     * 必须在Fragment 创建之后 ，绑定添加个Activity之前
     */
    public static CrimeFragment newInstance(UUID crimeID){
        Bundle args = new Bundle();

        args.putSerializable(ARG_CRIME_ID ,crimeID);
        LogUtils.e("UUID : " +crimeID.toString());
        CrimeFragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(args);
        return crimeFragment;
    }

//    private CrimeFragment() {
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if(null != bundle){
            UUID id = (UUID) bundle.getSerializable(ARG_CRIME_ID);
            mCrime = CrimeLab.getInstance(getActivity()).getCrimeBean(id);
            if(null == mCrime){
                mCrime = new CrimeBean();
            }
        }else{
            LogUtils.e("MSG IS NULL");
            mCrime = new CrimeBean();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime,container,false);

        mSolvedCb = v.findViewById(R.id.crime_cb_solved);
        mDateBtn = v.findViewById(R.id.crime_btn_date);
        mDeleteBtn = v.findViewById(R.id.crime_btn_delete);
        mTitleField = v.findViewById(R.id.crime_et_title);

        mTitleField.setText(mCrime.getTitle());
        mSolvedCb.setChecked(mCrime.isSolved());

        mSolvedCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCrime.setSolved(b);
            }
        });

        updateDate();
//        mDateBtn.setEnabled(false);
        mDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FragmentManager fm = getFragmentManager();
//                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
//                /**
//                 * setTargetFragment Fragment 数据传递 CrimeFragment为接受数据Fragment，dialogFragment为传递方
//                 */
//                dialog.setTargetFragment(CrimeFragment.this,REQUEST_DATE);
//                dialog.show(fm,DIALOG_DATE);
                Intent i = DatePickerFragmentActivity.newIntent(getActivity(),mCrime.getDate());
                startActivityForResult(i,REQUEST_DATE);
//                getActivity().startActivityForResult(i,REQUEST_DATE);
            }
        });
        mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CrimeLab.getInstance(getActivity()).deleteCrimeBean(mCrime.getID());
                getActivity().finish();
            }
        });

        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mCrime.setTitle(editable.toString());
            }
        });

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();

        CrimeLab.getInstance(getContext()).updateCrime(mCrime);
    }

    /**
     * startActivityForResult(i,REQUEST_DATE); Fragment会收到onActivityResult
     * getActivity().startActivityForResult(i,REQUEST_DATE);
     *      承载Activity onActivityResult会收到调用 但是Fragment 不会受到
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        LogUtils.e("resultCode" + resultCode + " mCrime : " + (mCrime != null));
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_DATE && mCrime != null){
            assert data != null;
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate();
        }
    }

    private void updateDate() {
        mDateBtn.setText(mCrime.getDate().toString());
    }

    private String getCrimeReport(){
        String solvedString = null;
        if(mCrime.isSolved()){
            solvedString = getString(R.string.crime_report_solved);
        }else{
            solvedString = getString(R.string.crime_report_unsolved);
        }
        String dateFormat = "EEE , MMM dd";
        String dateString = DateFormat.format(dateFormat,mCrime.getDate()).toString();
        String suspect = mCrime.getSuspect();
        if(suspect == null){
            suspect = getString(R.string.crime_report_no_suspect);
        }else{
            suspect = getString(R.string.crime_report_suspect,suspect);
        }
        String report = getString(R.string.crime_report,mCrime.getTitle(),dateString,solvedString,suspect);

        return report;
    }

    public void returnResult(){
        getActivity().setResult(Activity.RESULT_OK);
    }

}
