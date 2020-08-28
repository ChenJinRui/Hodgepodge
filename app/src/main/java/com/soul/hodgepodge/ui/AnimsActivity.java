package com.soul.hodgepodge.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.soul.hodgepodge.R;
import com.soul.hodgepodge.adapter.AnimsAdapter;
import com.soul.hodgepodge.widget.dialog.MyDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chjr on 2020/7/29
 */

public class AnimsActivity extends Activity {

    private ListView lv;
    private AnimsAdapter mAnimsAdapter;
    private List<String> mAnimNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anims);
        lv = findViewById(R.id.anims_lv);
        initData();
        mAnimsAdapter = new AnimsAdapter(AnimsActivity.this,mAnimNames);
        lv.setAdapter(mAnimsAdapter);
        lv.setOnItemClickListener(mItemClick);
    }

    private void initData() {
        mAnimNames.add("AlphaAnimation\nres/anim/_<alpha>\n渐变透明度动画效果");
        mAnimNames.add("RotateAnimation\nres/anim/_<rotate>\n画面转移旋转动画效果");
        mAnimNames.add("ScaleAnimation\nres/anim/_<scale>\n渐变尺寸伸缩动画效果");
        mAnimNames.add("TranslateAnimation\nres/anim/_<translate>\n画面转换位置移动动画效果");
        mAnimNames.add("AnimationSet\nres/anim/_<set>\n一个持有其它动画元素alpha、scale、translate、rotate或者其它set元素的容器");
        mAnimNames.add("AnimatorSet\nView真实位置响应点击\nAnimatorSet 使用的是 Animator 的子类 -- 同时执行");
        mAnimNames.add("AnimatorSetSequentially\n--\nAnimatorSet 使用的是 Animator 的子类 -- 顺序执行");
    }

    private AdapterView.OnItemClickListener mItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // TODO: 2018/11/1
//            Toast.makeText(AnimsActivity.this,""+position,Toast.LENGTH_SHORT).show();

//            MyDialog myDialog = new MyDialog(AnimsActivity.this);
            MyDialog myDialog = new MyDialog(AnimsActivity.this,position);
            myDialog.show();
        }
    };

}