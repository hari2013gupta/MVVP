package com.hari.mvvp.fragment.quoteF;

public class QuotePresenterImpl implements QuoteContract.QuotePresenter, QuoteContract.QuoteInteractor.OnFinishListener {

    QuoteContract.QuoteView view;
    QuoteContract.QuoteInteractor interactor;

    QuotePresenterImpl(QuoteContract.QuoteView view, QuoteContract.QuoteInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onButtonClick() {
        if (view != null) {
            view.showProgress();
        }
//        interactor.getNextQuote(s -> view.setQuote(s));
        interactor.getNextQuote(this);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void onFinished(String s) {
        if (view != null) {
            view.hideProgress();
            view.setQuote(s);
        }
    }
}
