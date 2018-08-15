package com.shane.loopsample.loop;

import android.os.Handler;

import java.util.concurrent.TimeUnit;

public class HandlerTimer {
    private Handler handler;

    public HandlerTimer(Handler handler) {
        this.handler = handler;
    }

    public TimerTask schedule(final Runnable runnable, final long delay, final TimeUnit delayTimeUnit) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void doRun() {
                runnable.run();
            }
        };
        handler.postDelayed(timerTask, delayTimeUnit.toMillis(delay));
        return timerTask;
    }

    public TimerTask schedule(final Runnable runnable, final long delay, final long period, final TimeUnit timeUnit) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void doRun() {
                runnable.run();
                handler.postDelayed(this, timeUnit.toMillis(period));
            }
        };
        handler.postDelayed(timerTask, timeUnit.toMillis(delay));
        return timerTask;
    }

    public void cancel(TimerTask timerTask) {
        if (timerTask != null) {
            timerTask.cancel();
            handler.removeCallbacks(timerTask);
        }
    }

    public static abstract class TimerTask implements Runnable {
        private volatile boolean isCancelled;
        public abstract void doRun();
        public void cancel() {
            isCancelled = true;
        }
        @Override
        public void run() {
            if (!isCancelled) {
                doRun();
            }
        }
    }


}
