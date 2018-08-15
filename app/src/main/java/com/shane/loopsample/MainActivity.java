package com.shane.loopsample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.shane.loopsample.util.ContextUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        findViewById(R.id.handler_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt(LoopActivity.KEY_TYPE, LoopActivity.HANDLER_COUNT_DOWN);
                ContextUtil.openPage(MainActivity.this, LoopActivity.class, bundle);
            }
        });
        findViewById(R.id.timer_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt(LoopActivity.KEY_TYPE, LoopActivity.TIMER_COUNT_DOWN);
                ContextUtil.openPage(MainActivity.this, LoopActivity.class, bundle);
            }
        });
        findViewById(R.id.count_down_timer_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(LoopActivity.KEY_TYPE, LoopActivity.COUNT_DOWN_TIMER);
                ContextUtil.openPage(MainActivity.this, LoopActivity.class, bundle);
            }
        });
        findViewById(R.id.rx_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(LoopActivity.KEY_TYPE, LoopActivity.RXJAVA_COUNT_DOWN);
                ContextUtil.openPage(MainActivity.this, LoopActivity.class, bundle);
            }
        });
        findViewById(R.id.rx_loop_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(LoopActivity.KEY_TYPE, LoopActivity.RXJAVA_LOOP_NETWORK_REQ);
                ContextUtil.openPage(MainActivity.this, LoopActivity.class, bundle);
            }
        });


    }







}
