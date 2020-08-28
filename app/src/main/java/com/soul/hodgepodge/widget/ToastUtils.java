package com.soul.hodgepodge.widget;

import android.widget.Toast;

import com.soul.hodgepodge.MyApplication;
import com.soul.hodgepodge.utils.LogUtils;

import androidx.annotation.StringRes;


/**
 * Toast 弹窗工具类
 * Created by Chjr on 2020/7/23
 */
public class ToastUtils {
    private final static String TAG = ToastUtils.class.getSimpleName();

    private static Toast sToast = null;
    private static ToastUtils sToastUtils = null;

    public static ToastUtils getInstance(){
        if(null == sToastUtils){
            LogUtils.i(" sToastUtils == NUll ");
            sToastUtils = new ToastUtils();
        }
        return sToastUtils;
    };

    private ToastUtils() {
    }

    /**
     * 弹出多个toast时, 不会一个一个的弹, 后面一个要显示的内容直接显示在当前的toast上
     * @param msg 默认样式文字提示
     */
    public void show(String msg){
        if(sToast == null){
            // text 设置为NULL是为了有些ROOM修改了TOAST源码，会携带应用名或者Activity的名字等
            sToast = Toast.makeText(MyApplication.getContext(),null,Toast.LENGTH_LONG);
        }
        sToast.setText(msg);
        sToast.show();
    }
    /**
     * 弹出多个toast时, 不会一个一个的弹, 后面一个要显示的内容直接显示在当前的toast上
     * @param resId 默认样式String 资源文字提示
     */
    public void show(@StringRes int resId){
        if(sToast == null){
            // text 设置为NULL是为了有些ROOM修改了TOAST源码，会携带应用名或者Activity的名字等
            sToast = Toast.makeText(MyApplication.getContext(),null,Toast.LENGTH_LONG);
        }
        sToast.setText(resId);
        sToast.setDuration(Toast.LENGTH_LONG);
        sToast.show();
    }

    public  void cancel() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }
}
