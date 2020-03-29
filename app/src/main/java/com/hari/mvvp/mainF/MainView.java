package com.hari.mvvp.mainF;

import android.content.Context;

public interface MainView {

    void showMessage(String s);

    int getCurrentSpeed();

    void updateSpeed(int speed, int color);

    Context getCtx();

}
