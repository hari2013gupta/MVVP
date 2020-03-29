package com.hari.mvvp.mainF;

public class MainPresenterImpl implements MainPresenter, MainPresenterListener {
    MainView mainView;
    MainInteractor mainInteractor;
    public MainPresenterImpl(MainView mainView){
        this.mainView = mainView;
        this.mainInteractor = new MainInteractorImpl(this);
    }
}
