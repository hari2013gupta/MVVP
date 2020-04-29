package com.hari.mvvp.fragment.quoteF;

public class QuoteContract {

    interface QuoteView {
        void showProgress();

        void hideProgress();

        void setQuote(String s);
    }

    interface QuotePresenter {
        void onButtonClick();

        void onDestroy();
    }

    public interface QuoteInteractor {
        interface OnFinishListener {
            void onFinished(String s);
        }
        void getNextQuote(OnFinishListener listener);
    }
}
