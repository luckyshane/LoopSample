package com.shane.loopsample.presenter;


import android.os.Handler;
import android.os.Looper;

import com.shane.loopsample.loop.HandlerTimer;

import java.util.concurrent.TimeUnit;

public class HandlerCountDownPresenter implements LoopContract.Presenter {
    LoopContract.View view;
    Handler handler;
    HandlerTimer handlerTimer;
    HandlerTimer.TimerTask countDownTask;
    int count;
    static final int MAX_COUNT = 10;

    @Override
    public void attach(LoopContract.View view) {
        this.view = view;
        handler = new Handler(Looper.getMainLooper());
        view.showTitle("Handler实现CountDown");
    }

    @Override
    public void start() {
        if (handlerTimer == null) {
            handlerTimer = new HandlerTimer(handler);
        }
        handlerTimer.cancel(countDownTask);
        count = MAX_COUNT;
        countDownTask = handlerTimer.schedule(new Runnable() {
            @Override
            public void run() {
                view.showText(count + "");
                count--;
                if (count < 0 ) {
                    countDownTask.cancel();
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void stop() {
        if (handlerTimer != null) {
            handlerTimer.cancel(countDownTask);
        }
    }

    @Override
    public void detach() {
        if (handlerTimer != null) {
            handlerTimer.cancel(countDownTask);
        }
    }


}
