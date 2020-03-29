package com.hari.mvvp;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.hari.mvvp.mainF.MainFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ContainerActivity extends BaseActivity implements ContainerView , FragmentCallback {
    private final int TIME_INTERVAL = 3000;
    String TAG = ContainerActivity.class.getSimpleName();
    AppCompatActivity activity;
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;

        LinearLayout parent = new LinearLayout(activity);
        parent.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
        parent.setOrientation(LinearLayout.VERTICAL);

//        LayoutInflater inflater = LayoutInflater.from(activity);
//        viewToolbar = inflater.inflate(R.layout.toolbar_common, null);
////        View inflatedLayout= inflater.inflate(R.layout.yourLayout, (ViewGroup) view, false);
//        parent.addView(viewToolbar);

        FrameLayout frameLayout = new FrameLayout(activity);
        frameLayout.setId(FRAME_CONTAINER_ID);
        frameLayout.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
        parent.addView(frameLayout);

        setContentView(parent);
//        initToolBarFragment(activity, "User Login", statusColor, themeColor, false);

//        if (getIntent().hasExtra(ARG_FRAGMENT_ID)) {
//            String titleString = null;
//            Fragment fragment = null;
//            switch (getIntent().getIntExtra(ARG_FRAGMENT_ID, 0)) {
//                case 0:
//                    titleString = "WALLET";
//                    fragment = new AttendanceFragment();
//                    break;
//                case 1:
//                    titleString = "SUBSCRIPTIONS";
//                    fragment = new AttendanceFragment();
//                    break;
//            }
//            replaceFragment(fragment);
//        }

        if (savedInstanceState == null) {
            int fragmentId = getIntent().getIntExtra(FRAGMENT_ARG, 1);
            switch (fragmentId){
                case 1:
                    openFragment(activity, MainFragment.newInstance(), true);
                    break;
                case 0:
                    Log.w(TAG, "Invalid Fragment");
                    break;
            }
        }
    }

    public void setFragment(Fragment fragment) {
        if (fragment != null) {
            Bundle bundle = new Bundle();
            bundle.putString("args", "0");
            fragment.setArguments(bundle);

            String fragmentTAG = fragment.getClass().getSimpleName();
            FragmentManager fragmentManager = activity.getSupportFragmentManager();

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.add(FRAME_CONTAINER_ID, fragment, fragmentTAG);
            transaction.addToBackStack(fragmentTAG).commit();
        } else {
            Log.e(TAG, "Error in creating fragment");
        }
    }

    private void backAction() {
        try {
            int i = getSupportFragmentManager().getBackStackEntryCount();
            if (i < 1) {
                if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
                    super.onBackPressed();
                    return;
                } else {
                    showToast("Please press back again to exit.");
                    mBackPressed = System.currentTimeMillis();
                }
            } else {
                getSupportFragmentManager().popBackStack();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showErrorMessageAlert("");
        }
    }

    @Override
    public void onBackPressed() {
        backAction();
    }

    @Override
    public void doing_nothing() {
    }

    @Override
    public void Update(String titleString, int navigationId) {

    }
}
