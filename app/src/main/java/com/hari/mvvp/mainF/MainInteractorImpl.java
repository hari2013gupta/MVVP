package com.hari.mvvp.mainF;

public class MainInteractorImpl implements MainInteractor {

    MainPresenterListener mainPresenterListener;

    public MainInteractorImpl(MainPresenterListener mainPresenterListener) {
        this.mainPresenterListener = mainPresenterListener;
    }

}
