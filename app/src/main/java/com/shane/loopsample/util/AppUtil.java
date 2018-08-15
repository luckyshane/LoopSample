package com.shane.loopsample.util;

import android.os.Handler;
import android.os.Looper;

public class AppUtil {
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());


    public static void runOnMainThread(Runnable runnable) {
        if (isMainThread()) {
            runnable.run();
            return;
        }
        mainHandler.post(runnable);
    }

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }



}
