package com.shane.loopsample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.shane.loopsample.presenter.LoopContract;
import com.shane.loopsample.presenter.CountDownTimerPresenter;
import com.shane.loopsample.presenter.HandlerCountDownPresenter;
import com.shane.loopsample.presenter.RxJavaCountDownPresenter;
import com.shane.loopsample.presenter.RxJavaLoopNetworkReqPresenter;
import com.shane.loopsample.presenter.TimerCountDownPresenter;

public class LoopActivity extends AppCompatActivity implements LoopContract.View {
    TextView countDownTv;
    LoopContract.Presenter presenter;
    private int type;

    public static final int HANDLER_COUNT_DOWN = 0;
    public static final int TIMER_COUNT_DOWN = 1;
    public static final int COUNT_DOWN_TIMER = 2;
    public static final int RXJAVA_COUNT_DOWN = 3;
    public static final int RXJAVA_LOOP_NETWORK_REQ = 4;
    public static final String KEY_TYPE = "type";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        initView();
        type = getIntent().getIntExtra(KEY_TYPE, HANDLER_COUNT_DOWN);
        presenter = getPresenter();
        if (presenter != null) {
            presenter.attach(this);
        }
    }

    private LoopContract.Presenter getPresenter() {
        switch (type) {
            case HANDLER_COUNT_DOWN:
                return new HandlerCountDownPresenter();
            case TIMER_COUNT_DOWN:
                return new TimerCountDownPresenter();
            case COUNT_DOWN_TIMER:
                return new CountDownTimerPresenter();
            case RXJAVA_COUNT_DOWN:
                return new RxJavaCountDownPresenter();
            case RXJAVA_LOOP_NETWORK_REQ:
                return new RxJavaLoopNetworkReqPresenter();
        }
        return new HandlerCountDownPresenter();
    }

    @Override
    public void showText(String text) {
        countDownTv.setText(text);
    }

    @Override
    public void showTitle(String title) {
        this.setTitle(title);
    }

    private void initView() {
        countDownTv = findViewById(R.id.count_down_tv);
        findViewById(R.id.start_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (presenter != null) {
                    presenter.start();
                }
            }
        });
        findViewById(R.id.stop_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (presenter != null) {
                    presenter.stop();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.detach();
            presenter = null;
        }
        super.onDestroy();
    }


}
