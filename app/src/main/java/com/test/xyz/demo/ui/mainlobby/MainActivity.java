package com.test.xyz.demo.ui.mainlobby;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.test.xyz.demo.R;
import com.test.xyz.demo.ui.mainlobby.navdrawer.FragmentDrawer;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {
    private final static int REPO_LIST_FRAG = 0;
    private final static int WEATHER_FRAG = 1;
    private final static int FRAGMENT_COUNT = WEATHER_FRAG + 1;
    private final static String CURRENT_FRAGMENT = "currentFragment";
    private final Fragment[] fragments = new Fragment[FRAGMENT_COUNT];

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private int currentFragment = REPO_LIST_FRAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize(savedInstanceState);
    }

    private void initialize(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        hideAllFragments();

        if (savedInstanceState != null) {
            currentFragment = savedInstanceState.getInt(CURRENT_FRAGMENT);
        }

        showFragment(currentFragment);
    }

    private void hideAllFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragments[0] = fragmentManager.findFragmentById(R.id.repolist_frag);
        fragments[1] = fragmentManager.findFragmentById(R.id.main_frag);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        for (int i = 0; i < fragments.length; ++i) {
            fragmentTransaction.hide(fragments[i]);
        }

        fragmentTransaction.commit();
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        showFragment(position);
    }

    private void showFragment(int fragmentIndex) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        String title = "";

        switch (fragmentIndex) {
            case REPO_LIST_FRAG:
                title = getString(R.string.nav_item_repo_list);
                break;
            case WEATHER_FRAG:
                title = getString(R.string.nav_item_main);
                break;
            default:
                break;
        }

        for (int i = 0; i < fragments.length; i++) {
            if (i == fragmentIndex) {
                transaction.show(fragments[i]);
            } else {
                transaction.hide(fragments[i]);
            }
        }

        transaction.commit();
        currentFragment = fragmentIndex;
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        savedState.putInt(CURRENT_FRAGMENT, currentFragment);
        super.onSaveInstanceState(savedState);
    }
}