package com.hari.mvvp.mainF;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hari.mvvp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends BaseFragment implements MainView{
    public MainFragment INSTANCE;
    AppCompatActivity activity;
    @BindView(R.id.accelerateButton)
    Button accelerateButton;
    @BindView(R.id.breakButton)
    Button breakButton;
    @BindView(R.id.speedTV)
    Button speedTV;
    int currentSpeed = 45;
    MainPresenter mainPresenter;

    public static MainFragment newInstance() {
        MainFragment f = new MainFragment();

        return f;
    }

    public MainFragment getInstance() {
        return INSTANCE;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initToolBarFragment(view, activity, "Main Fragment", statusColor, bgColor, false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        INSTANCE = this;
        activity = (AppCompatActivity) getActivity();
        mainPresenter = new MainPresenterImpl(this);

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void showToast(String s) {
        showToast(s);
    }
}
