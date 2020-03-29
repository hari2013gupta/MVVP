package com.hari.mvvp.mainF;

import android.content.Context;

public class MainPresenterImpl implements MainPresenter, MainPresenterListener {
    MainView mainView;
    MainInteractor mainInteractor;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
        this.mainInteractor = new MainInteractorImpl(this);
    }

    @Override
    public void accelerateAction(int a) {
        mainInteractor.accel(a);
    }

    @Override
    public void breakAction(int pwr) {
        mainInteractor.brek(pwr);
    }

    @Override
    public void pwrBreakAction() {
        mainInteractor.pwrBrek();
        mainView.showMessage("Power break!!");
    }

    @Override
    public int getCurrentSpeed() {
        return mainView.getCurrentSpeed();
    }

    @Override
    public Context getCtx() {
        return mainView.getCtx();
    }

    @Override
    public void updateSpeed(int speed) {
        int color = mainInteractor.getSpeedColor(speed);
        mainView.updateSpeed(speed, color);
        if(speed == 0){
            mainView.showMessage("Stopped!!");
        }
    }
}
