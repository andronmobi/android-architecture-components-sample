package com.andronmobi.bookstore.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.view.View;

import com.andronmobi.bookstore.R;
import com.andronmobi.bookstore.ui.fragment.FragmentBookList;
import com.andronmobi.bookstore.ui.fragment.FragmentCart;
import com.andronmobi.bookstore.ui.fragment.FragmentControl;
import com.andronmobi.bookstore.ui.fragment.NavFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentControl {

    static final String TAG = "MainActivity";
    static final String STATE_SELECTED_ROOT_FRAGMENT = "selectedRootFragment";
    static final String STATE_SELECTED_ITEM_ID = "selectedItemId";

    protected DrawerLayout mDrawer;
    protected ActionBarDrawerToggle mDrawerToggle;

    private String mSelectedRootFragmentName;
    private int mSelectedItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbarAndDrawer(R.id.toolbar, R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setDefaultNavigationState();
        Class fragmentToDisplay = FragmentBookList.class;
        if (savedInstanceState != null) {
            // Restore the navigate state
            mSelectedItemId = savedInstanceState.getInt(STATE_SELECTED_ITEM_ID, mSelectedItemId);
            mSelectedRootFragmentName = savedInstanceState.getString(STATE_SELECTED_ROOT_FRAGMENT, mSelectedRootFragmentName);
            try {
                fragmentToDisplay = Class.forName(mSelectedRootFragmentName);
            } catch (ClassNotFoundException e) {
                Log.e(TAG, "Can't convert fragment class name '" + mSelectedRootFragmentName + "' to Class");
                setDefaultNavigationState();
                fragmentToDisplay = FragmentBookList.class;
            }
        }
        displayFragment(fragmentToDisplay);
        navigationView.setCheckedItem(mSelectedItemId);
    }

    private void setupToolbarAndDrawer(int resToolbarId, int resDrawerId) {

        Toolbar toolbar = findViewById(resToolbarId);
        setSupportActionBar(toolbar);

        mDrawer = findViewById(resDrawerId);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    private void setDefaultNavigationState() {
        mSelectedItemId = R.id.nav_fragment_books_list;
        mSelectedRootFragmentName = FragmentBookList.class.getName();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(STATE_SELECTED_ITEM_ID, mSelectedItemId);
        savedInstanceState.putString(STATE_SELECTED_ROOT_FRAGMENT, mSelectedRootFragmentName);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        if (isDrawerOpened()) {
            closeDrawer();
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                getSupportFragmentManager().popBackStack();
            } else {
                if (!isFinishing()) {
                    finish();
                }
            }
        }
    }

    private boolean isDrawerOpened() {
        if (mDrawer != null) {
            return mDrawer.isDrawerOpen(GravityCompat.START);
        }
        return false;
    }

    private void closeDrawer() {
        if (mDrawer != null) {
            mDrawer.closeDrawer(GravityCompat.START);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Do nothing if we click on the current item
        if (item.isChecked()) {
            return true;
        }

        // Return to the root fragment if there are other fragments in the stack
        while (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStackImmediate();
        }

        // Handle navigation view item clicks here.
        mSelectedItemId = item.getItemId();
        if (mSelectedItemId == R.id.nav_fragment_books_list) {
            mSelectedRootFragmentName = FragmentBookList.class.getName();
            replaceFragment(FragmentBookList.class);
        } else if (mSelectedItemId == R.id.nav_fragment_cart) {
            mSelectedRootFragmentName = FragmentCart.class.getName();
            replaceFragment(FragmentCart.class);
        }

        closeDrawer();
        return true;
    }

    @Override
    public void displayFragment(Class fragmentClass) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        String backStateName = fragmentClass.getName();
        Fragment fragment = fragmentManager.findFragmentByTag(backStateName);

        if (fragment == null) {
            try {
                fragment = (Fragment) fragmentClass.newInstance();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.flContent, fragment, backStateName);
                ft.addToBackStack(backStateName);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            } catch (InstantiationException e) {
                return;
            } catch (IllegalAccessException e) {
                return;
            }
        }
    }

    @Override
    public void replaceFragment(Class fragmentClass) {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
        }
        displayFragment(fragmentClass);
    }

    @Override
    public void onFragmentChanged(NavFragment newFragment) {
        // Set a title for the toolbar
        String name = newFragment.getName();
        setTitle(name != null ? name : getResources().getString(R.string.app_name));

        // Update the navigation bar toggle
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            // Remove the hamburger icon
            mDrawerToggle.setDrawerIndicatorEnabled(false);
            // Show the back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // Set on toggle click listener to pop the previous fragment
            mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getSupportFragmentManager().popBackStack();
                }
            });
        } else {
            // Remove the back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            // Show the hamburger button
            mDrawerToggle.setDrawerIndicatorEnabled(true);
            // Set on toggle click listener to open nav drawer
            mDrawerToggle.setToolbarNavigationClickListener(mDrawerToggle.getToolbarNavigationClickListener());
        }
    }

}
