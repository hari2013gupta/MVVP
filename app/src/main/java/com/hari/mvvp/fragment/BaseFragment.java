package com.hari.mvvp.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.hari.mvvp.MvpView;
import com.hari.mvvp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseFragment extends UpdateFragmentMenu implements MvpView {
    private String TAG = BaseFragment.class.getSimpleName();
    private ProgressDialog mProgressD;

    public void showErrorMessageAlert(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (!IsInternetConnected(context)) {
                showNoInternetAlert(context);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
                builder.setMessage("Something went wrong, please contact tech team!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
//            Logger.logMessage(context, "Common:showErrorMessageAlert. Fail." + ex.getMessage(), Logger.LogType.Error);
        }
    }

    public void showToastMessage(Activity activity, String message) {
        if (activity != null && !activity.isFinishing()) {
            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
        }
    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    public void hideKeyboard1(Activity activity) {
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

    public boolean notNullParams(String string) {
        if (string == null || string.equals("") || string.equals("-") || !(string.length() > 0)) {
            return false;
        }
        return true;
    }
    public  Boolean IsInternetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (!(cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting())) {
            return false;
        } else
            return true;
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

    // TODO: 5/29/2018 Do custom dialog here by hari

    public void showProgressD(String message) {
        if (mProgressD == null) {
            mProgressD = new ProgressDialog(getActivity());
            mProgressD.setCancelable(false);
            mProgressD.setMessage(message);
        }

        mProgressD.show();
    }

    public void hideProgressD() {
        if (mProgressD != null && mProgressD.isShowing()) {
            mProgressD.dismiss();
        }
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

    public boolean validateMobileNo(String mobileNo) {
        if (!notNullParams(mobileNo) || mobileNo.length() < 10 || mobileNo.startsWith("0") || mobileNo.startsWith("1") ||
                mobileNo.startsWith("2") || mobileNo.startsWith("3") || mobileNo.startsWith("00")) {
            return false;
        }
        return true;
    }
    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    Unbinder unbinder;

    public void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
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

    public void initToolBarFragment(View rootView, final AppCompatActivity activity, String titleName, int statusColor, int bgColor, boolean showBack) {
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(activity.getResources().getColor(statusColor));
        }
        toolbar.setBackgroundColor(activity.getResources().getColor(bgColor));
        toolbar.setTitle(titleName);

        activity.setSupportActionBar(toolbar);
        final ActionBar ab = activity.getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayShowHomeEnabled(false);

        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.layout_toolbar_common, null);
        TextView tv_ToolbarTitle = view.findViewById(R.id.tv_ToolbarTitle);
        tv_ToolbarTitle.setText(titleName);

        ab.setCustomView(view);
        ab.setDisplayShowCustomEnabled(true);

        if (showBack) {
            toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
            toolbar.setNavigationOnClickListener(
                    v -> {
                        if (activity.getSupportFragmentManager().getBackStackEntryCount() < 1) {
                            activity.finish();
                        } else {
                            activity.getSupportFragmentManager().popBackStack();
                        }
                    }
            );
        } else {
            ab.setDisplayShowHomeEnabled(false);
            ab.setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    public void showToast(String message) {
        showToastMessage(getActivity(), message);
    }

    public void printError(Exception e) {
        Log.w(TAG, e.getMessage(), e);
    }

    public void openDialog(Activity activity, String titleString, String messageString) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialogTheme);
        builder.setTitle(titleString);

        final TextView tvMessage = new TextView(activity);
        tvMessage.setTextColor(Color.parseColor("#FF0000"));
        builder.setView(tvMessage);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void snackBar(View view, String message, String buttonText) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG).
                setAction(buttonText, v -> {
                });
        snackbar.setActionTextColor(Color.WHITE);
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.notification_template_icon_bg, 0, 0, 0);
        textView.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.size_margin20));
        snackbar.show();
    }

    public void showMessageAlert(String title, String msg) {
        showErrorMessageDialog(getActivity(), title, msg, () -> {
        });
    }

    public void showErrorAlert() {
        if (!IsInternetConnected(getActivity())) {
            showErrorMessageDialog(getActivity(), "Error", "Please check your Internet connection and try again!", () -> {
            });
        } else {
            showErrorMessageDialog(getActivity(), "Error", "Something went wrong,\nplease contact tech support!", () -> {
            });
        }
    }

    public interface OnDialogOkClickListener {
        void onOkButtonClicked();
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
}
