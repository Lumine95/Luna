package com.mm.luna;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.Bugly;

/**
 * Created by ZMM on 2018/2/5.
 */

public class MyApplication extends Application {
    public static Context mContext;
    private static MyApplication mApplication;

    public static MyApplication getInstance() {
        if (mApplication == null) {
            mApplication = new MyApplication();

        }
        return mApplication;
    }

    /**
     * 返回Application的Context
     *
     * @return
     */
    public Context getAppContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();

        Logger.addLogAdapter(new AndroidLogAdapter());  // 初始化Logger
      //  CrashReport.initCrashReport(getApplicationContext(), "e18d5ba117", false);
        Bugly.init(getApplicationContext(), "e18d5ba117", true);
    }
}
