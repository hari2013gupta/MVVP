package com.hari.mvvp.mainF;

import android.content.Context;

public interface MainPresenterListener {
    int getCurrentSpeed();

    void updateSpeed(int speed);

    Context getCtx();
}
