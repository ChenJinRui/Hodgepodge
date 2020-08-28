package com.soul.hodgepodge.widget.dialog;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.soul.hodgepodge.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import androidx.annotation.NonNull;

/**
 * Created by Soul on 2018/12/7.
 */

public class MyDialog extends Dialog {

    private ImageView img;
    private ImageView img2;
    private int screenWidth;
    private int screenHeight;
    private LinearLayout dialog_root;
    private Context mContext;
    private int position;

    public MyDialog(@NonNull Context context) {
        super(context, R.style.Theme_AppCompat_NoActionBar);
        mContext = context.getApplicationContext();
    }

    public MyDialog(@NonNull Context context, int pos) {
        super(context, R.style.Theme_AppCompat_NoActionBar);
        mContext = context.getApplicationContext();
        position = pos;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_anim);

        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();

        dialogWindow.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        setCanceledOnTouchOutside(true);
        lp.width = screenWidth;
        lp.height = screenHeight;

        img = findViewById(R.id.img);
        img2 = findViewById(R.id.img2);
        dialog_root = findViewById(R.id.dialog_root);
        img2.setScaleX(1.2f);
        img2.setScaleY(1.2f);


        switch (position){
            case 0:
                startAlphaAnimation(0);
                break;
            case 1:
                img2.setVisibility(View.VISIBLE);
                startRotateAnimation(1500);
                startRotateAnimation2(1500);
                break;
            case 2:
                startScaleAnimation(0);
                break;
            case 3:
                startTranslateAnimation(0);
                break;
            case 4:
                startAnimationSet(0);
                break;
            case 5:
                startAnimatorSet(0);
                break;
            case 6:
                startAnimatorSetSequentially(0);
                break;
            default:
                startAnimatorSet(0);
                break;

        }
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"onClick",Toast.LENGTH_SHORT).show();
            }
        });

        dialog_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });
    }
    private void startAlphaAnimation(long startTime){
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f,0.8f);
        alphaAnimation.setStartOffset(startTime);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setRepeatMode(AlphaAnimation.REVERSE);
        alphaAnimation.setRepeatCount(AlphaAnimation.INFINITE);
        img.startAnimation(alphaAnimation);
//        img.setAnimation(alphaAnimation);
//        alphaAnimation.startNow();
    }
    private void startRotateAnimation(long startTime){
        RotateAnimation rotateAnimation = new RotateAnimation(
                0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setStartTime(startTime);//？？？
        rotateAnimation.setDuration(1500);//设置动画持续时间
        rotateAnimation.setRepeatCount(10);//设置重复次数
        rotateAnimation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        img.startAnimation(rotateAnimation);
    }
    private void startRotateAnimation2(long startTime){
        RotateAnimation rotateAnimation = new RotateAnimation(
                0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
//        rotateAnimation.setStartTime(startTime);
        rotateAnimation.setDuration(1500);//设置动画持续时间
        rotateAnimation.setRepeatCount(10);//设置重复次数
        rotateAnimation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        rotateAnimation.setStartOffset(startTime);//执行前的等待时间
        img2.startAnimation(rotateAnimation);
    }
    private void startScaleAnimation(long startTime){
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                0.0f, 2.5f, 0.0f, 2.5f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1500);//设置动画持续时间
        scaleAnimation.setRepeatCount(10);//设置重复次数
        scaleAnimation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        scaleAnimation.setStartOffset(startTime);//执行前的等待时间
        img.startAnimation(scaleAnimation);
    }
    private void startTranslateAnimation(long startTime){
        TranslateAnimation translateAnimation = new TranslateAnimation(
                //X轴初始位置
                Animation.RELATIVE_TO_SELF, 0.0f,
                //X轴移动的结束位置
                Animation.RELATIVE_TO_SELF, 0.0f,
                //y轴开始位置
                Animation.RELATIVE_TO_SELF, 0.0f,
                //y轴移动后的结束位置
                Animation.RELATIVE_TO_SELF, 2.5f);

        //1秒完成动画
        translateAnimation.setStartOffset(startTime);
        translateAnimation.setDuration(1000);
        //如果fillAfter的值为真的话，动画结束后，控件停留在执行后的状态
        translateAnimation.setRepeatMode(TranslateAnimation.REVERSE);
        //设置动画播放次数
        translateAnimation.setRepeatCount(TranslateAnimation.INFINITE);
        img.setAnimation(translateAnimation);
    }

    private void startAnimationSet(long startTime) {
        TranslateAnimation translateAnimationX = new TranslateAnimation(
                //X轴初始位置
                Animation.RELATIVE_TO_SELF, 0.0f,
                //X轴移动的结束位置
                Animation.RELATIVE_TO_SELF, 2.0f,
                //y轴开始位置
                Animation.RELATIVE_TO_SELF, 0.0f,
                //y轴移动后的结束位置
                Animation.RELATIVE_TO_SELF, 0.0f);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(10);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);
        TranslateAnimation translateAnimationY = new TranslateAnimation(
                //X轴初始位置
                Animation.RELATIVE_TO_SELF, 0.0f,
                //X轴移动的结束位置
                Animation.RELATIVE_TO_SELF, 0.0f,
                //y轴开始位置
                Animation.RELATIVE_TO_SELF, 0.0f,
                //y轴移动后的结束位置
                Animation.RELATIVE_TO_SELF, 2.5f);
        //  AccelerateInterpolator  在动画开始的地方速率改变比较慢，然后开始加速
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(10);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        //false指使用自己的差值器
        final AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(800);// 动画的执行时间

        img.startAnimation(set);

    }

    /**
     * 顺序执行动画
     *
     * @param startTime
     */
    private void startAnimatorSet(long startTime){
        ObjectAnimator animatorA = ObjectAnimator.ofFloat(img, "TranslationX", -300, 300, 0);
        ObjectAnimator animatorB = ObjectAnimator.ofFloat(img, "scaleY", 0.5f, 1.5f, 1f);
        ObjectAnimator animatorC = ObjectAnimator.ofFloat(img, "rotation", 0, 270, 90, 180, 0);

        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(animatorA, animatorB, animatorC);
        animatorSet2.setStartDelay(startTime);
        animatorSet2.setDuration(3*1000);
        animatorSet2.start();
    }
    private void startAnimatorSetSequentially(long startTime){
        ObjectAnimator animatorA = ObjectAnimator.ofFloat(img, "TranslationX", -300, 300, 0);
        ObjectAnimator animatorB = ObjectAnimator.ofFloat(img2, "scaleY", 0.5f, 1.5f, 1f);
//        ObjectAnimator animatorC = ObjectAnimator.ofFloat(img, "rotation", 0, 270, 90, 180, 0);
        animatorA.setRepeatCount(10);
        animatorB.setRepeatCount(10);
//        animatorC.setRepeatCount(10);
        AnimatorSet animatorSet2 = new AnimatorSet();
//        animatorSet2.playSequentially(animatorA, animatorB, animatorC);
        animatorSet2.playSequentially(animatorA, animatorB);
//        animatorSet2.play(animatorA).after(animatorC).before(animatorB);
        animatorSet2.setDuration(9*1000);
        animatorSet2.start();

    }

    public static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }
        return line;
    }



}

