package com.hari.mvvp.mainF;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hari.mvvp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class MainFragment extends BaseFragment implements MainView {
    public MainFragment INSTANCE;
    AppCompatActivity activity;
    @BindView(R.id.accelerateButton)
    Button accelerateButton;
    @BindView(R.id.breakButton)
    Button breakButton;
    @BindView(R.id.speedTV)
    TextView speedTV;
    int currentSpeed = 45, rpm = 5;
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

        initToolBarFragment(view, activity, "My Bike", statusColor, bgColor, false);

        mainPresenter.accelerateAction(rpm);
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
    public void showMessage(String s) {
        showToast(s);
    }

    @Override
    public int getCurrentSpeed() {
        return currentSpeed;
    }

    @Override
    public void updateSpeed(int speed, int speedStatus) {
        currentSpeed = speed;
        speedTV.setText("Current speed: " + currentSpeed);
        speedTV.setTextColor(speedStatus);
    }

    @Override
    public Context getCtx() {
        return activity.getBaseContext();
    }

    @OnClick({R.id.breakButton, R.id.pwrBreakButton, R.id.accelerateButton})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.breakButton:
                mainPresenter.breakAction(rpm);
                break;
            case R.id.pwrBreakButton:
                mainPresenter.pwrBreakAction();
                break;
            case R.id.accelerateButton:
                mainPresenter.accelerateAction(rpm);
                break;
        }
    }
}
