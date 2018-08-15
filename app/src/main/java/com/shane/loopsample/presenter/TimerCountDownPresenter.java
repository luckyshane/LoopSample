package com.shane.loopsample.presenter;

import com.shane.loopsample.util.AppUtil;

import java.util.Timer;
import java.util.TimerTask;

public class TimerCountDownPresenter implements LoopContract.Presenter {
    private static final String TAG = TimerCountDownPresenter.class.getSimpleName();
    LoopContract.View view;
    Timer timer;
    TimerTask countDownTask;
    int count;
    static final int MAX_COUNT = 10;

    @Override
    public void attach(LoopContract.View view) {
        this.view = view;
        view.showTitle("Timer实现CountDown");
    }

    @Override
    public void detach() {
        if (countDownTask != null) {
            countDownTask.cancel();
        }
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void start() {
        if (timer == null) {
            timer = new Timer();
        }
        if (countDownTask != null) {
            countDownTask.cancel();
        }
        count = MAX_COUNT;
        countDownTask = new TimerTask() {
            @Override
            public void run() {
                final int aCount = count;
                if (count-- >= 0) {
                    AppUtil.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            view.showText(String.valueOf(aCount));
                        }
                    });
                } else {
                    if (countDownTask != null) {
                        countDownTask.cancel();
                    }
                }

            }
        };
        timer.schedule(countDownTask, 0, 1000);
    }

    @Override
    public void stop() {
        if (countDownTask != null) {
            countDownTask.cancel();
        }
    }


}
