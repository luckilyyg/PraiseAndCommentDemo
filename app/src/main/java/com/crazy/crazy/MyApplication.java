package com.crazy.crazy;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Resources;



/**
 * Created on 2019/8/27 17:13
 *
 * @auther super果
 * @annotation
 */
public class MyApplication extends Application {
    private static MyApplication baseApplication;


    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;

    }
    public static Context getAppContext() {
        return baseApplication;
    }
    public static Context getInstance() {
        return baseApplication;
    }
    public static Resources getAppResources() {
        return baseApplication.getResources();
    }

}
