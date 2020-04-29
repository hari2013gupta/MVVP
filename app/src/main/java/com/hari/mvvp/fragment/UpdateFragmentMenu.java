/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.hari.mvvp.fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.hari.mvvp.MyConstants;
import com.hari.mvvp.R;
import com.hari.mvvp.fragment.mainF.MainFragment;

import androidx.fragment.app.Fragment;

public class UpdateFragmentMenu extends Fragment implements MyConstants {
    public String CURRENT_FRAGMENT = UpdateFragmentMenu.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void setCurrentFragment(String fragmentTag) {
        CURRENT_FRAGMENT = fragmentTag;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_common, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_notification).setVisible(false);
        menu.findItem(R.id.action_logout).setVisible(false);
        switch (CURRENT_FRAGMENT){
            case "MainFragment":
                menu.findItem(R.id.action_notification).setVisible(true);
                break;
            case "QuoteFragment":
                menu.findItem(R.id.action_notification).setVisible(false);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_notification:
                MainFragment.getInstance().openQuoteFragment();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
