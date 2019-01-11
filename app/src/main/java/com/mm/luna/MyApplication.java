package com.mm.luna;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.library.utils.U;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.tencent.bugly.Bugly;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

/**
 * Created by ZMM on 2018/2/5.
 */

public class MyApplication extends Application {
    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getApplicationContext();

        BGASwipeBackHelper.init(this, null);
        Bugly.init(getApplicationContext(), "e18d5ba117", true);
        initLogger();
        U.init(this);
    }

    public static Context getAppContext() {
        return mAppContext;
    }

    private void initLogger() {
        LogStrategy logStrategy = new LogStrategy() {
            private String[] prefix = {". ", " ."};
            private int index = 0;

            @Override
            public void log(int priority, @Nullable String tag, @NonNull String message) {
                index = index ^ 1;
                Log.println(priority, prefix[index] + tag, message);
            }
        };
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .logStrategy(logStrategy)
                .showThreadInfo(false) // Optional
                .methodCount(1) // Optional
                .methodOffset(7)
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }
}
