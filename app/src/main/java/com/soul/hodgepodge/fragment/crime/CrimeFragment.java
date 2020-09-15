package com.soul.hodgepodge.fragment.crime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.soul.hodgepodge.R;
import com.soul.hodgepodge.bean.crime.CrimeBean;
import com.soul.hodgepodge.data.crime.CrimeLab;
import com.soul.hodgepodge.dialog.DatePickerFragment;
import com.soul.hodgepodge.ui.crime.DatePickerFragmentActivity;
import com.soul.hodgepodge.utils.LogUtils;
import com.soul.hodgepodge.utils.PictureUtils;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

public
/**
 * Created by Chjr on 2020/8/13
 */
class CrimeFragment extends Fragment {

    private CrimeBean mCrime;
    private File mPhotoFile;

    private EditText mTitleField;
    private Button mDateBtn;
    private Button mDeleteBtn;
    private Button mSuspectBtn;
    private Button mReportBtn;
    private CheckBox mSolvedCb;

    private ImageButton mPhotoBtn;
    private ImageView mPhotoView;

    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_CONTACT = 1;
    private static final int REQUEST_PHOTO = 2;

    private Callbacks mCallbacks;

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

        mPhotoFile = CrimeLab.getInstance(getActivity()).getPhotoFile(mCrime);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime,container,false);

        mSolvedCb = v.findViewById(R.id.crime_cb_solved);
        mDateBtn = v.findViewById(R.id.crime_btn_date);
        mDeleteBtn = v.findViewById(R.id.crime_btn_delete);
        mTitleField = v.findViewById(R.id.crime_et_title);
        mReportBtn = v.findViewById(R.id.crime_report);
        mSuspectBtn = v.findViewById(R.id.crime_suspect);

        mPhotoBtn = v.findViewById(R.id.crime_camera);
        mPhotoView = v.findViewById(R.id.crime_photo);

        mTitleField.setText(mCrime.getTitle());
        mSolvedCb.setChecked(mCrime.isSolved());

        mSolvedCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCrime.setSolved(b);
                updateCrime();
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
                updateCrime();
            }
        });
        mReportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT,getCrimeReport());
                i.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.crime_report_subject));
                //Intent.createChoose 可以选择每次弹出所有符合展示条件的应用
                i = Intent.createChooser(i,getString(R.string.send_report));
                startActivity(i);
            }
        });
        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
//        pickContact.addCategory(Intent.CATEGORY_HOME);//测试pickContact 没有匹配的情况
        mSuspectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivityForResult(pickContact,REQUEST_CONTACT);
            }
        });
        if(mCrime.getSuspect() != null){
            mSuspectBtn.setText(mCrime.getSuspect());
        }
        /**
         * 防止隐式Intent pickContact 在系统找不到匹配Activity崩溃,提前屏蔽掉调用操作
         */
        PackageManager packageManager = getActivity().getPackageManager();
        if(packageManager.resolveActivity(pickContact,PackageManager.MATCH_DEFAULT_ONLY) == null){
            mSuspectBtn.setEnabled(false);
        }
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final boolean canTakePhoto = mPhotoFile != null &&
                captureImage.resolveActivity(packageManager) != null;
        mPhotoBtn.setEnabled(canTakePhoto);
        mPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //com.soul.hodgepodge.FileProvider 实在AndroidManifest.xml配置的地址
                Uri uri = FileProvider.getUriForFile(getActivity(),
                        "com.soul.hodgepodge.FileProvider",mPhotoFile);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT,uri);

                List<ResolveInfo> cameraActivites = getActivity().getPackageManager()
                        .queryIntentActivities(captureImage,PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo activity : cameraActivites) {
                    getActivity().grantUriPermission(activity.activityInfo.packageName,
                            uri,Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    startActivityForResult(captureImage,REQUEST_PHOTO);
                }
            }
        });
        updatePhoneView();
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
        LogUtils.e("requestCode" + resultCode + " mCrime : " + (mCrime != null));
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_DATE && mCrime != null){
            assert data != null;
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate();
            updateCrime();
        }else if(requestCode == REQUEST_CONTACT){
            Uri contactUri = data.getData();
            String [] queryFields = new String []{ContactsContract.Contacts.DISPLAY_NAME};
            Cursor c = getActivity().getContentResolver()
                    .query(contactUri,queryFields,null,null,null);
            try{
                if(c.getColumnCount() == 0){
                    return;
                }
                c.moveToFirst();
                String suspect = c.getString(0);
                mCrime.setSuspect(suspect);
                mSuspectBtn.setText(suspect);
                updateCrime();
            } finally {
                c.close();
            }
        }else if(requestCode == REQUEST_PHOTO){
            Uri uri = FileProvider.getUriForFile(getActivity(),
                    "com.soul.hodgepodge.FileProvider",mPhotoFile);
            getActivity().revokeUriPermission(uri,Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            LogUtils.e("updatePhoneView ： " + uri.getPath());
            updatePhoneView();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof Callbacks){
            mCallbacks = (Callbacks) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    public interface Callbacks{
        void onCrimeUpdated(CrimeBean crimeBean);
    }
    private void updateDate() {
        mDateBtn.setText(mCrime.getDate().toString());
    }

    private void updateCrime(){
        CrimeLab.getInstance(getActivity()).updateCrime(mCrime);
        mCallbacks.onCrimeUpdated(mCrime);
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

    private void updatePhoneView(){
        if(mPhotoFile == null || !mPhotoFile.exists()){
            LogUtils.e("updatePhoneView NUll ");
            mPhotoView.setImageDrawable(null);
        }else{
            LogUtils.e("updatePhoneView : " + mPhotoFile.getPath());
            Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(),getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
    }

    public void returnResult(){
        getActivity().setResult(Activity.RESULT_OK);
    }

}
