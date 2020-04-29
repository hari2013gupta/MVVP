package com.hari.mvvp.fragment.quoteF;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hari.mvvp.R;
import com.hari.mvvp.fragment.BaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.OnClick;

import static android.view.View.GONE;

public class QuoteFragment extends BaseFragment implements QuoteContract.QuoteView {

    static QuoteFragment INSTANCE;
    String TAG = QuoteFragment.class.getSimpleName();
    AppCompatActivity activity;
    QuoteContract.QuotePresenter presenter;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    public static QuoteFragment newInstance() {
        QuoteFragment f = new QuoteFragment();

        return f;
    }

    public QuoteFragment getInstance() {
        return INSTANCE;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolBarFragment(view, activity, "My Quote", statusColor, bgColor, false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        INSTANCE = this;
        setCurrentFragment(TAG);
        activity = (AppCompatActivity) getActivity();
        presenter = new QuotePresenterImpl(this, new QuoteInteractorImpl());

        return inflater.inflate(R.layout.fragment_quote, container, false);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        textView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(GONE);
        textView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setQuote(String s) {
        textView.setText(s);
    }

    @Override
    public void onDestroyView() {
        presenter.onDestroy();
        super.onDestroyView();
    }

    @OnClick({R.id.button})
    void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.button:
                presenter.onButtonClick();
                break;
        }
    }

}
