package com.soul.hodgepodge;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
/**
 *
 * Created by Ch jr on 2020/7/23
 */
public class MyApplication extends Application {

    public static Context sContext = null;//需要使用的上下文对象

    public static Handler sHandler = null;//需要使用的Handler

    public static Thread sMainThread = null;//提供主线程对象

    public static int mainThreadId = -1;//提供主线程对象的Id


    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        //真机调试查看数据库 s 谷歌浏览器打开chrome://inspect
        //Devices 界面下打开inspect 找到resources,点开 ,然后找到Websql就可以查看数据库和表
        Stetho.initializeWithDefaults(this);
        //真机调试查看数据库 e
        //this is test
    }

    /**
     *
     * @return 提供需要使用的上下文对象
     */
    public static Context getContext(){

        return sContext;
    }
    /**
     *
     * @return 提供需要使用的主线程对象
     */
    public static Thread getMainThread(){
        if(null == sMainThread){
            sMainThread = Thread.currentThread();//实例化Application当前的线程为主线程
        }
        return sMainThread;
    }
    /**
     *
     * @return 提供需要使用的主线程对象
     */
    public static int getMainThreadId(){
       if(-1 == mainThreadId){
           mainThreadId = android.os.Process.myPid();//获取当前线程的PID
       }
        return mainThreadId;
    }

    /**
     *
     * @return 提供需要使用的主线程对象
     */
    public static Handler getMainHandler(){
        if(null == sHandler){
            sHandler = new Handler() {
                @Override
                public void publish(LogRecord logRecord) {

                }

                @Override
                public void flush() {

                }

                @Override
                public void close() throws SecurityException {

                }
            };
        }
        return sHandler;
    }

}
