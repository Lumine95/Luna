package com.mm.luna;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.Bugly;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

/**
 * Created by ZMM on 2018/2/5.
 */

public class MyApplication extends com.android.library.MyApplication {
    private static MyApplication mApplication;

    public static MyApplication getInstance() {
        if (mApplication == null) {
            mApplication = new MyApplication();

        }
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Logger.addLogAdapter(new AndroidLogAdapter());
        BGASwipeBackHelper.init(this, null);
        Bugly.init(getApplicationContext(), "e18d5ba117", true);
    }
}
