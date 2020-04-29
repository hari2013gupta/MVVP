package com.hari.mvvp.fragment.mainF;

public interface MainInteractor {
    void accel(int a);

    void brek(int pwr);

    void pwrBrek();

    int getSpeedColor(int speed);
}
