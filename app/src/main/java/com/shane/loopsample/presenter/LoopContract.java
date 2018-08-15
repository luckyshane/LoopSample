package com.shane.loopsample.presenter;

public interface LoopContract {

    interface View {
        void showText(String text);
        void showTitle(String title);
    }

    interface Presenter {
        void attach(View view);
        void detach();
        void start();
        void stop();
    }


}
