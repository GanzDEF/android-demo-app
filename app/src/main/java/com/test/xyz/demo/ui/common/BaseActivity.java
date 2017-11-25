package com.test.xyz.demo.ui.common;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.test.xyz.demo.R;
import com.test.xyz.demo.ui.common.util.UIHelper;
import com.test.xyz.demo.ui.mainlobby.navdrawer.FragmentDrawer;
import com.test.xyz.demo.ui.repolist.RepoListFragment;

public abstract class BaseActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    protected void setupNavigationDrawer(FragmentDrawer.FragmentDrawerListener listener) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(listener);
    }

    protected void loadFragment(Fragment fragment, String title) {
        UIHelper.hideKeyboard(this);

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container_body,
                        fragment, null).commit();

        getSupportActionBar().setTitle(title);
    }

    protected void loadMainFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_body,
                        RepoListFragment.newInstance(), null).commit();

        getSupportActionBar().setTitle(R.string.repo_list);
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container_body);
        if (currentFragment instanceof RepoListFragment) {
            finish();
            return;
        }
        super.onBackPressed();
    }
}
