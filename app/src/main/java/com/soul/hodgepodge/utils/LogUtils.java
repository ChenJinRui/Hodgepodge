package com.soul.hodgepodge.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

/**
 * 日志输出工具类
 * Created by Chjr on 2020/7/29
 */
public class LogUtils {

    private static final int e = 0;
    private static final int w = 1;
    private static final int d = 2;
    private static final int i = 3;
    private static final int v = 4;
    private static final boolean isLogOut = true;// 是否输出日志

    public static void e(String logMessage) {
        if (isLogOut) {
            logOut(e, logMessage,generateTag());
        }
    }

    public static void w(String logMessage) {
        if (isLogOut) {
            logOut(w, logMessage,generateTag());
        }
    }

    public static void d(String logMessage) {
        if (!isLogOut) {

        }else if(TextUtils.isEmpty(logMessage)){
            logOut(d, "message is null",generateTag());
        }else{
            logOut(d, logMessage,generateTag());
        }
    }

    public static void i(String logMessage) {
        if (isLogOut) {
            logOut(i, logMessage,generateTag());
        }
    }

    public static void v(String logMessage) {
        if (isLogOut) {
            logOut(v, logMessage,generateTag());
        }
    }


    private static void logOut(int leave, String logMessage,String logTag) {
        switch (leave) {
            case e:
                Log.e(logTag, logMessage);
                break;
            case w:
                Log.w(logTag, logMessage);
                break;
            case d:
                Log.d(logTag, logMessage);
                break;
            case v:
                Log.v(logTag, logMessage);
                break;
            case i:
            default:
                Log.i(logTag, logMessage);
                break;
        }
    }

    @SuppressLint("DefaultLocale")
    private static String generateTag() {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[4];
        String callerClazzName = stackTraceElement.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        String tag = "%s.%s(Line:%d)";
        tag = String.format(tag, callerClazzName, stackTraceElement.getMethodName(), stackTraceElement.getLineNumber());
//        tag = String.format("%s.%s.%s",callerClazzName,stackTraceElement.getMethodName(),stackTraceElement.getLineNumber()+"");
        return tag;
    }


}
