package com.soul.hodgepodge.fragment.crime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.soul.hodgepodge.R;
import com.soul.hodgepodge.bean.crime.CrimeBean;
import com.soul.hodgepodge.data.crime.CrimeLab;
import com.soul.hodgepodge.ui.crime.CrimeActivity;
import com.soul.hodgepodge.ui.crime.CrimePageActivity;
import com.soul.hodgepodge.utils.LogUtils;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public
/**
 * Created by Chjr on 2020/8/13
 *
 */
class CrimeListFragment extends Fragment {

    // TODO: 2020/9/15 mRecyclerView 实现ItemTouchHelper 侧滑删除 
    private RecyclerView mRecyclerView;
    private CrimeAdapter mCrimeAdapter;

    private static final int VIEW_TYPE_NORMAL = 0;
    private static final int VIEW_TYPE_POLICE = 1;

    private static final int REQUEST_CODE_CRIME = 1;

    private boolean mSubtitleVisible;
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    private Callbacks mCallbacks;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mRecyclerView = v.findViewById(R.id.crime_list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(savedInstanceState != null){
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }
        upDataUI();

        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE,mSubtitleVisible);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //需要设置setHasOptionsMenu(true);否则onCreateOptionsMenu 不会被调用
        setHasOptionsMenu(true);
    }

    /**
     * 需要设置setHasOptionsMenu(true);否则onCreateOptionsMenu 不会被调用
     */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list,menu);

        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if(mSubtitleVisible){
            subtitleItem.setTitle(R.string.hide_subtitle);
        }else{
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    /**
     * 响应 Menu 单击事件
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_crime:
                CrimeBean crimeBean = new CrimeBean();
                Log.e("test",crimeBean.toString());
                CrimeLab.getInstance(getActivity()).addCrime(crimeBean);
                if(mCallbacks != null){
                    upDataUI();
                    mCallbacks.onCrimeSelected(crimeBean);
                }else{
                    Intent intent = CrimePageActivity.newIntent(getActivity(),crimeBean.getID());
                    startActivity(intent);
                }
                return true;
            case R.id.show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                Objects.requireNonNull(getActivity()).invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void updateSubtitle(){
        CrimeLab crimeLab = CrimeLab.getInstance(getActivity());
        int crimeCount = crimeLab.getCrimeBeans().size();
        String subtitle = getString(R.string.subtitle_format,crimeCount);

        if(!mSubtitleVisible){
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    /**
     * 托管Activity的onResume()被调用时FragmentManager会调用Fragment的onResume()方法
     */
    @Override
    public void onResume() {
        super.onResume();
        upDataUI();
    }

    /**
     * 也可以通过重写 onActivityResult 监听托管Activity的onActivityResult()方法
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(REQUEST_CODE_CRIME == requestCode){

        }
    }

    /**
     * @param context 获取依赖activity（实现了Callbacks接口）的实例转换为Callbacks
     *                并在对应方法（onDetach）中销毁
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof Callbacks){//避免绑定Activity没有实现接口
            mCallbacks = (Callbacks) context;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    public interface Callbacks{
        void onCrimeSelected(CrimeBean crimeBean);
    }

    /**
     * 更新UI
     */
    public void upDataUI() {
        //单例的生命周期和APPLICATION相同 Context 避免持有影响回收
        CrimeLab crimeLab = CrimeLab.getInstance(Objects.requireNonNull(getActivity())
                .getApplicationContext());
        List<CrimeBean> crimeBeans = crimeLab.getCrimeBeans();
        if (null == mCrimeAdapter) {
            mCrimeAdapter = new CrimeAdapter(crimeBeans);
            mRecyclerView.setAdapter(mCrimeAdapter);
        } else {
            mCrimeAdapter.setCrimeBeans(crimeBeans);
            mCrimeAdapter.notifyDataSetChanged();
        }
        updateSubtitle();
    }

    //    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
    private class CrimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<CrimeBean> mCrimeBeans;

        public CrimeAdapter(List<CrimeBean> crimeBeans) {
            mCrimeBeans = crimeBeans;
        }

        @NonNull
        @Override
//        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            if (VIEW_TYPE_POLICE == viewType) {
                return new CrimePoliceHolder(inflater.inflate(R.layout.recycler_crime_list_police, parent, false));
            } else {
                return new CrimeHolder(inflater.inflate(R.layout.recycler_crime_list, parent, false));
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            if (holder instanceof CrimePoliceHolder) {
//        if(VIEW_TYPE_POLICE == holder.getItemViewType()){
                ((CrimePoliceHolder) holder).bind(mCrimeBeans.get(position));
            } else {
                ((CrimeHolder) holder).bind(mCrimeBeans.get(position));
            }
        }


        @Override
        public int getItemCount() {
            return mCrimeBeans.size();
        }

        public void setCrimeBeans(List<CrimeBean> crimeBeans){
            mCrimeBeans = crimeBeans;
        }
        @Override
        public int getItemViewType(int position) {
            //暂时不适用、多布局模板
//            if(position % 5 == 0){
//                return VIEW_TYPE_POLICE;
//            }
            return VIEW_TYPE_NORMAL;
        }

    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTV;
        private TextView mDateTV;
        private ImageView mSolveImg;

        private CrimeBean mCrimeBean;

        public CrimeHolder(@NonNull View itemView) {
            super(itemView);
            mTitleTV = itemView.findViewById(R.id.recycler_crime_title);
            mDateTV = itemView.findViewById(R.id.recycler_crime_date);
            mSolveImg = itemView.findViewById(R.id.recycler_crime_solved);
            itemView.setOnClickListener(this);
        }

        public void bind(CrimeBean crimeBean) {
            mCrimeBean = crimeBean;
            mTitleTV.setText(mCrimeBean.getTitle());
            mDateTV.setText(mCrimeBean.getDate().toString());
            mSolveImg.setVisibility(mCrimeBean.isSolved() ? View.VISIBLE : View.INVISIBLE);
        }

        @Override
        public void onClick(View view) {
//          startActivity(new Intent(getActivity(), CrimePageActivity.class));
//            startActivityForResult(CrimePageActivity.newIntent(getActivity(), mCrimeBean.getID())
//                    , REQUEST_CODE_CRIME);
            if(mCallbacks != null){
                mCallbacks.onCrimeSelected(mCrimeBean);
            }else{
                startActivityForResult(CrimePageActivity.newIntent(getActivity(), mCrimeBean.getID())
                        , REQUEST_CODE_CRIME);
            }

        }
    }

    private class CrimePoliceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTV;
        private TextView mDateTV;
        private CheckBox mSolveCB;

        private CrimeBean mCrimeBean;

        public CrimePoliceHolder(@NonNull View itemView) {
            super(itemView);
            mTitleTV = itemView.findViewById(R.id.recycler_crime_title);
            mDateTV = itemView.findViewById(R.id.recycler_crime_date);
            mSolveCB = itemView.findViewById(R.id.recycler_crime_solved);
            itemView.setOnClickListener(this);
        }

        public void bind(CrimeBean crimeBean) {
            mCrimeBean = crimeBean;
            mTitleTV.setText(mCrimeBean.getTitle());
            mDateTV.setText(mCrimeBean.getDate().toString());
            mSolveCB.setChecked(mCrimeBean.isSolved());
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity()
                    , mCrimeBean.getTitle() + "\t" + mCrimeBean.getDate().toString()
                    , Toast.LENGTH_SHORT).show();
        }
    }

}
