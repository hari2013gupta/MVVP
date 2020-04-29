package com.hari.mvvp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hari.mvvp.MvpView;
import com.hari.mvvp.MyConstants;
import com.hari.mvvp.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class BaseActivity extends AppCompatActivity implements MvpView {
    private String TAG = BaseActivity.class.getSimpleName();
    private ProgressDialog mProgressD;
    private AppCompatActivity activity;

    public void SummaryDialog(Activity activity, String titleString, String summeryString) {
        final Dialog dialog = new Dialog(activity, R.style.CustomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("====Alert Dialog====");

        RelativeLayout receiptContainer = (RelativeLayout) dialog.findViewById(R.id.receiptContainer);
        enableVisible(receiptContainer);

        TextView messageTV = (TextView) dialog.findViewById(R.id.messageTV);
        messageTV.setMovementMethod(new ScrollingMovementMethod());
        messageTV.setText(summeryString);

        TextView titleTV =  dialog.findViewById(R.id.titleTV);
        if (notNullParams(titleString)) {
            titleTV.setText(titleString);
            messageTV.setTextSize(20);
        } else {
            titleTV.setText("--Summary--");
        }
        Button confirmBTN = dialog.findViewById(R.id.confirmBTN);
        confirmBTN.setVisibility(View.GONE);
        Button cancelBTN = dialog.findViewById(R.id.cancelBTN);
        cancelBTN.setText("OK");
        cancelBTN.setOnClickListener(v -> dialog.dismiss());

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCancelable(false);
        dialog.setOnKeyListener((arg0, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                dialog.dismiss();
            }
            return true;
        });
        dialog.show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
    }

    public Activity getActivity() {
        return activity;
    }

    public void showProgressD(String message) {
        if (mProgressD == null) {
            mProgressD = new ProgressDialog(this);
            mProgressD.setCancelable(false);
            mProgressD.setMessage(message);
        }
        mProgressD.show();
    }

    // TODO: 5/29/2018 Do custom dialog here by hari

    public void hideProgressD() {
        if (mProgressD != null && mProgressD.isShowing()) {
            mProgressD.dismiss();
        }
    }

    public void showNoInternetAlert(Context context) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
            builder.setMessage("Please check the internet connection and try again.")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } catch (Exception ex) {
            ex.printStackTrace();
//            Logger.logMessage(context, "Common:showNoInternetAlert. Fail." + ex.getMessage(), Logger.LogType.Error);
        }
    }

    public void hideKeyBoard(Activity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void showKeyBoard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void enableViews(View... views) {
        for (View v : views) {
            v.setEnabled(true);
        }
    }

    public void disableViews(View... views) {
        for (View v : views) {
            v.setEnabled(false);
        }
    }

    public void enableVisible(View... views) {
        for (View v : views) {
            v.setVisibility(View.VISIBLE);
        }
    }

    public void disableVisible(View... views) {
        for (View v : views) {
            v.setVisibility(View.GONE);
        }
    }
    public boolean notNullParams(String string) {
        if (string == null || string.equals("") || string.equals("-") || !(string.length() > 0)) {
            return false;
        }
        return true;
    }

    public void showToast(String message) {
        showToastMessage(this, message);
    }

    public void printError(Exception e) {
        Log.w(TAG, e.getMessage(), e);
    }
    public void showToastMessage(Activity activity, String message) {
        if (activity != null && !activity.isFinishing()) {
            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
        }
    }

    public void showMessageAlert(String title, String msg) {
        showErrorMessageDialog(getActivity(), title, msg, () -> {
        });
    }

    public  Boolean IsInternetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (!(cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting())) {
            return false;
        } else
            return true;
    }
    public void showErrorMessageAlert(String msg) {
        if (!IsInternetConnected(getActivity())) {
            showErrorMessageDialog(getActivity(), "Error", "Please check your Internet connection and try again!", () -> {
            });
        } else {
            showErrorMessageDialog(getActivity(), "Error", "Something went wrong,\nplease contact tech support!", () -> {
            });
        }
    }

    public void showErrorMessageDialog(final Context mActivity, final String heading, final String title,
                                              final OnDialogOkClickListener onDialogOkClickListener) {
        Dialog mDialog = new Dialog(mActivity, R.style.CustomDialog);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.dialog_error);
        mDialog.setCancelable(false);
        TextView title_error = mDialog.findViewById(R.id.title_error);
        title_error.setText(heading);
        TextView title_error_msg = mDialog.findViewById(R.id.title_error_msg);
        title_error_msg.setText(title);
        Button okButton = mDialog.findViewById(R.id.okButton);
        okButton.setOnClickListener(v -> {
            if (onDialogOkClickListener != null) {
                onDialogOkClickListener.onOkButtonClicked();
            }
            mDialog.dismiss();
        });
        mDialog.setOnKeyListener((arg0, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                mDialog.dismiss();
            }
            return true;
        });
        mDialog.show();
    }

    public interface OnDialogOkClickListener {
        void onOkButtonClicked();
    }
    public interface OnAlertClickListener {
        void onSubmitButtonClicked();

        void onOkButtonClicked();

        void onCancelButtonClicked();
    }
    public void showCommonAlert(final Context mActivity,
                                final boolean isCancellable, final boolean isDisableBack,
                                final String heading, final String title,
                                final String cancelBtnString, final String okBtnString, final String submitBtnString,
                                OnAlertClickListener onAlertClickListener) {
        Dialog mDialog = new Dialog(mActivity, R.style.CustomDialog);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.setContentView(R.layout.dialog_error);
        mDialog.setCancelable(isCancellable);
        TextView title_error = mDialog.findViewById(R.id.title_error);
        title_error.setText(heading);
        TextView title_error_msg = mDialog.findViewById(R.id.title_error_msg);
        title_error_msg.setText(title);
        Button cancelButton = mDialog.findViewById(R.id.cancelButton);
        Button submitButton = mDialog.findViewById(R.id.submitButton);
        Button okButton = mDialog.findViewById(R.id.okButton);
        enableVisible(cancelButton, submitButton, okButton);
        okButton.setText(okBtnString);
        cancelButton.setText(cancelBtnString);
        submitButton.setText(submitBtnString);
        submitButton.setOnClickListener(v -> {
            if (onAlertClickListener != null) {
                onAlertClickListener.onSubmitButtonClicked();
            }
            mDialog.dismiss();
        });
        okButton.setOnClickListener(v -> {
            if (onAlertClickListener != null) {
                onAlertClickListener.onOkButtonClicked();
            }
            mDialog.dismiss();
        });
        cancelButton.setOnClickListener(v -> {
            if (onAlertClickListener != null) {
                onAlertClickListener.onCancelButtonClicked();
            }
            mDialog.dismiss();
        });
        mDialog.setOnKeyListener((arg0, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (!isDisableBack) {
                    mDialog.dismiss();
                } else {
                    Log.i(TAG, "=========Back button disabled for this dialog=========");
                }
            }
            return true;
        });
        mDialog.show();
    }

    public boolean openFragment(AppCompatActivity activity, Fragment fragment, boolean isReplace) {
        if (fragment != null) {
            String fragmentTAG = fragment.getClass().getSimpleName();
            FragmentManager fm = activity.getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            if (isReplace) {
                fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                ft.replace(FRAME_CONTAINER_ID, fragment, fragmentTAG);
            } else {
                ft.add(FRAME_CONTAINER_ID, fragment, fragmentTAG);
                ft.addToBackStack(fragmentTAG);
            }
            ft.commit();
            return true;
        } else {
            return false;
        }
    }

    public void initToolBar(final AppCompatActivity activity, String titleName, int statusColor, int bgColor) {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(activity.getResources().getColor(statusColor));
        }
        toolbar.setBackgroundColor(activity.getResources().getColor(bgColor));
        toolbar.setTitle(titleName);

        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);

        activity.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.layout_toolbar_common, null); //hear set your costume layout
        TextView tv_ToolbarTitle = (TextView) view.findViewById(R.id.tv_ToolbarTitle);
        applyFontForToolbarTitle(activity, toolbar);

        tv_ToolbarTitle.setText(titleName);

        activity.getSupportActionBar().setCustomView(view);
        activity.getSupportActionBar().setDisplayShowCustomEnabled(true);
        final ActionBar ab = activity.getSupportActionBar();
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbar.setNavigationOnClickListener(
                v -> {
                    activity.finish();
                    activity.overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
                }
        );
    }

    public void applyFontForToolbarTitle(Activity context, Toolbar toolbar) {
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View view = toolbar.getChildAt(i);
            if (view instanceof TextView) {
                TextView tv = (TextView) view;
                Typeface normalFont = Typeface.createFromAsset(context.getAssets(), "fonts/Calibri.ttf");
                Typeface boldFont = Typeface.createFromAsset(context.getAssets(), "fonts/Calibri Bold.ttf");
                if (tv.getText().equals(context.getTitle())) {
                    tv.setTypeface(normalFont);
                    break;
                }
            }
        }
    }
}
