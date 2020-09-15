package com.soul.hodgepodge;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.soul.hodgepodge.utils.LogUtils;

public class MainActivity extends AppCompatActivity {

    private static final String BUNDLE_KEY = "test_bundle";
    private static final String STRING_KEY = "test_String";
    private TextView tv01,tv02;
    private Button btn;

    private int pos = 0;

    // savedInstanceState 可能为空需判断
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtils.d("onCreate");
        initView();
        Log.d("TestDebug","TestDebugException",new Exception());
    }
    private boolean isF = false;


    /**
     *
     * @param context 启动Activity
     * @param arg1 携带参数
     * @param arg2 携带参数
     * @return 启动Intent
     * TODO 静态方法是否有影响？！
     * statActivity(newIntent);
     */
    public static Intent newIntent(Context context,String arg1,boolean arg2){
        Intent intent = new Intent(context,MainActivity.class);
        intent.putExtra("KEY_01",arg1);
        intent.putExtra("KEY_02",arg2);
        return intent;
    }
    private void initView() {

        tv01 = findViewById(R.id.text_1);
        tv02 = findViewById(R.id.text_2);
        btn = findViewById(R.id.btn);
        tv02.setScaleX(1.1f);
        tv02.setScaleY(1.1f);

        btn.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) //只做警告 编译通过但是低于版本还是会报错 需要针对SDK分别处理
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    Animator animation = ViewAnimationUtils.createCircularReveal(tv01,
                            tv01.getWidth()/2,
                            tv01.getHeight()/2,
                            tv01.getWidth(),
                            0);
                    animation.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
//                        tv01.setVisibility(View.GONE);
                        }
                    });
                    animation.start();
                }

            }
        });
        tv01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isF){
                    isF = true;
                    startTest(true);
                }


            }
        });
        tv02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv01.setVisibility(View.VISIBLE);
                if(isF){
                    isF = false;
                    startTest(false);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.d("onStart");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STRING_KEY,"string value");
        Bundle bundle = new Bundle();
        bundle.putString(STRING_KEY,"bundle value");
        outState.putBundle(BUNDLE_KEY,bundle);
    }

    //onRestoreInstanceState是在onStart()之后被调用 savedInstanceState 非空
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        LogUtils.d("onRestoreInstanceState");
        String value = savedInstanceState.getString(STRING_KEY);
        LogUtils.d(value);
        if (null != savedInstanceState.getBundle(BUNDLE_KEY)) {
            String bundleValue = savedInstanceState.getBundle(BUNDLE_KEY).getString(STRING_KEY);
            LogUtils.d(bundleValue);
        }
    }


    /**
     * test
     * @param isFirst a
     */
    private void startTest(boolean isFirst ) {


        if(isFirst){
            AnimatorSet animatorSetA = new AnimatorSet();//组合动画
            ObjectAnimator animatorA = ObjectAnimator.ofFloat(tv02, "scaleY", 1.1f, 1.0f, 1.0f);
            ObjectAnimator animatorB = ObjectAnimator.ofFloat(tv02, "scaleX", 1.1f, 1.0f, 1.0f);
            animatorSetA.setDuration(1000);
            animatorSetA.setInterpolator(new DecelerateInterpolator());
            animatorSetA.play(animatorA).with(animatorB);//两个动画同时开始

            AnimatorSet animatorSet = new AnimatorSet();//组合动画
            ObjectAnimator animator01 = ObjectAnimator.ofFloat(tv01, "scaleX", 1.0f, 1.2f, 1.1f);
            ObjectAnimator animator02 = ObjectAnimator.ofFloat(tv01, "scaleY", 1.0f, 1.2f, 1.1f);
            animatorSet.setDuration(1500);
            animatorSet.setInterpolator(new DecelerateInterpolator());
            animatorSet.play(animator01).with(animator02);//两个动画同时开始

            animatorSetA.start();
            animatorSet.start();
        }else{
            AnimatorSet animatorSetA = new AnimatorSet();//组合动画
            ObjectAnimator animatorA = ObjectAnimator.ofFloat(tv01, "scaleY", 1.1f, 1.0f, 1.0f);
            ObjectAnimator animatorB = ObjectAnimator.ofFloat(tv01, "scaleX", 1.1f, 1.0f, 1.0f);
            animatorSetA.setDuration(1000);
            animatorSetA.setInterpolator(new DecelerateInterpolator());
            animatorSetA.play(animatorA).with(animatorB);//两个动画同时开始

            AnimatorSet animatorSet = new AnimatorSet();//组合动画
            ObjectAnimator animator01 = ObjectAnimator.ofFloat(tv02, "scaleX", 1.0f, 1.2f, 1.1f);
            ObjectAnimator animator02 = ObjectAnimator.ofFloat(tv02, "scaleY", 1.0f, 1.2f, 1.1f);
            animatorSet.setDuration(1500);
            animatorSet.setInterpolator(new DecelerateInterpolator());
            animatorSet.play(animator01).with(animator02);//两个动画同时开始

            animatorSetA.start();
            animatorSet.start();
        }



    }
}