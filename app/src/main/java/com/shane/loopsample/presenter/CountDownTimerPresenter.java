package com.shane.loopsample.presenter;

import android.os.CountDownTimer;

public class CountDownTimerPresenter implements LoopContract.Presenter {
    LoopContract.View view;
    CountDownTimer timer;
    static final int MAX_COUNT = 10;
    static final int INTERVAL = 1000;

    @Override
    public void attach(LoopContract.View view) {
        this.view = view;
        view.showTitle("使用CountDownTimer来实现倒计时，实际测试效果不好");
    }

    @Override
    public void detach() {
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void start() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new CountDownTimer(MAX_COUNT * INTERVAL, INTERVAL / 2) {
            @Override
            public void onTick(long millisUntilFinished) {
                final int remainSeconds = (int) (millisUntilFinished / 1000);
                view.showText(remainSeconds + "");
            }

            @Override
            public void onFinish() {

            }
        };
        timer.start();
    }

    @Override
    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }



}
