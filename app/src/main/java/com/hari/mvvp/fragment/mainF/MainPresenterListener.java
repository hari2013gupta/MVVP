package com.hari.mvvp.fragment.mainF;

import android.content.Context;

public interface MainPresenterListener {
    int getCurrentSpeed();

    void updateSpeed(int speed);

    Context getCtx();
}
