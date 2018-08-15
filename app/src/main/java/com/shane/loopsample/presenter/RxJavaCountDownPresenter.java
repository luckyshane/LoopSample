package com.shane.loopsample.presenter;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxJavaCountDownPresenter implements LoopContract.Presenter {
    private static final String TAG = RxJavaCountDownPresenter.class.getSimpleName();

    LoopContract.View view;
    Disposable countDownTaskDisposable;
    static final int MAX_COUNT = 10;

    @Override
    public void attach(LoopContract.View view) {
        this.view = view;
        view.showTitle("RxJava实现CountDown");
    }

    @Override
    public void detach() {
        if (countDownTaskDisposable != null) {
            countDownTaskDisposable.dispose();
        }
    }

    @Override
    public void start() {
        if (countDownTaskDisposable != null) {
            countDownTaskDisposable.dispose();
        }
        countDownTaskDisposable = Observable.intervalRange(0,  MAX_COUNT + 1, 0, 1, TimeUnit.SECONDS, Schedulers.io())
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return MAX_COUNT - aLong;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        view.showText(String.valueOf(aLong));
                    }
                });
    }

    @Override
    public void stop() {
        if (countDownTaskDisposable != null) {
            countDownTaskDisposable.dispose();
            countDownTaskDisposable = null;
        }
    }






}
