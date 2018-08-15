package com.shane.loopsample.presenter;


import android.support.v7.view.menu.MenuView;
import android.util.Log;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.schedulers.SingleScheduler;
import io.reactivex.schedulers.Schedulers;

public class RxJavaLoopNetworkReqPresenter implements LoopContract.Presenter {
    private static final String TAG = RxJavaLoopNetworkReqPresenter.class.getSimpleName();

    LoopContract.View view;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    Scheduler singScheduler = new SingleScheduler();

    @Override
    public void attach(LoopContract.View view) {
        this.view = view;
        view.showTitle("RxJava loop请求网络");
    }

    @Override
    public void detach() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    @Override
    public void start() {
        compositeDisposable.dispose();
        compositeDisposable = new CompositeDisposable();

        //loopAtFixRate();
        //loopAtFixRateEx();
        loopSequence();
    }

    private void getData() {
        compositeDisposable.add(getDataFromServer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "getData: " + integer);
                        view.showText(integer + "");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "getData error " + throwable.getMessage());
                        view.showText(throwable.getMessage());
                    }
                }));
    }

    @Override
    public void stop() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    private Observable<Integer> getDataFromServer() {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                if (emitter.isDisposed()) {
                    return;
                }
                int randomSleep = new Random().nextInt(5);
                try {
                    Thread.sleep(randomSleep * 1000);
                } catch (Exception e) {}
                if (emitter.isDisposed()) {
                    return;
                }
                if (randomSleep % 2 == 0) {
                    emitter.onError(new Exception("get fake error for " + randomSleep));
                    return;
                }
                emitter.onNext(randomSleep);
                emitter.onComplete();
            }
        });
    }

    // 嵌套风格loop， 不管实际结果，反正到点了就执行。
    private void loopAtFixRate() {
        compositeDisposable.add(Observable.interval(0, 5, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d(TAG, "interval: " + aLong);
                        getData();
                    }
                }));
    }

    // 改良版本
    private void loopAtFixRateEx() {
        compositeDisposable.add(Observable.interval(0, 5, TimeUnit.SECONDS)
                .flatMap(new Function<Long, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(Long aLong) throws Exception {
                        return getDataFromServer();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer value) throws Exception {
                        Log.d(TAG, "value: " + value);
                        view.showText(value + "");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "loopAtFixRateEx", throwable);
                    }
                }));
    }



    // 按照顺序loop，意味着第一次结果请求完成后，再考虑下次请求
    private void loopSequence() {
        Disposable disposable = getDataFromServer()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.d(TAG, "loopSequence subscribe");
                    }
                })
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "loopSequence doOnNext: " + integer);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "loopSequence doOnError: " + throwable.getMessage());
                    }
                })
                .delay(5, TimeUnit.SECONDS, true)       // 设置delayError为true，表示出现错误的时候也需要延迟5s进行通知，达到无论是请求正常还是请求失败，都是5s后重新订阅，即重新请求。
                .subscribeOn(Schedulers.io())
                .repeat()   // repeat保证请求成功后能够重新订阅。
                .retry()    // retry保证请求失败后能重新订阅
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        view.showText(integer + "");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showText(throwable.getMessage());
                    }
                });
        compositeDisposable.add(disposable);
    }






}
