package com.hari.mvvp.mainF;

import com.hari.mvvp.R;

public class MainInteractorImpl implements MainInteractor {

    MainPresenterListener mainPresenterListener;

    public MainInteractorImpl(MainPresenterListener mainPresenterListener) {
        this.mainPresenterListener = mainPresenterListener;
    }

    @Override
    public void accel(int rpm) {
        int speed = mainPresenterListener.getCurrentSpeed();
        speed = speed + rpm;
        mainPresenterListener.updateSpeed(speed);
    }

    @Override
    public int getSpeedColor(int speed) {
        int color = mainPresenterListener.getCtx().getResources().getColor(R.color.Bee_Yellow);
        if (speed <= 20) {
        } else if (speed > 20 && speed < 40) {
            color = mainPresenterListener.getCtx().getResources().getColor(R.color.Yellow_Green);
        } else if (speed >= 40 && speed <= 55) {
            color = mainPresenterListener.getCtx().getResources().getColor(R.color.Green_Onion);
        } else if (speed > 55 && speed <= 75) {
            color = mainPresenterListener.getCtx().getResources().getColor(R.color.Bean_Red);
        } else if (speed > 75 && speed <= 100) {
            color = mainPresenterListener.getCtx().getResources().getColor(R.color.Red_Wine);
        } else if (speed > 100) {
            color = mainPresenterListener.getCtx().getResources().getColor(R.color.Blood_Red);
        }
        return color;
    }

    @Override
    public void brek(int pwr) {
        int speed = mainPresenterListener.getCurrentSpeed();
        if (speed <= pwr) {
            speed = 0;
        } else {
            speed = speed - pwr;
        }
        mainPresenterListener.updateSpeed(speed);
    }

    @Override
    public void pwrBrek() {
        mainPresenterListener.updateSpeed(0);
    }
}
