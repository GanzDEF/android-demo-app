package com.test.xyz.demo.ui.mainlobby;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.test.xyz.demo.R;
import com.test.xyz.demo.ui.common.BaseActivity;
import com.test.xyz.demo.ui.mainlobby.navdrawer.FragmentDrawer;
import com.test.xyz.demo.ui.repodetails.RepoDetailsFragment;
import com.test.xyz.demo.ui.repolist.RepoListFragment;
import com.test.xyz.demo.ui.weather.WeatherFragment;

public class MainActivity extends BaseActivity implements FragmentDrawer.FragmentDrawerListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize(savedInstanceState);
    }

    private void initialize(Bundle savedInstanceState) {
        setupNavigationDrawer(this);
        loadMainFragment();
    }

    @Override
    public void onDrawerItemSelected(View view, FragmentDrawer.NavigationDrawerFragment navigationDrawerFragment) {
        loadNavigationDrawerFragment(navigationDrawerFragment);
    }

    public void loadRepoDetailsFragment(String title) {
        RepoDetailsFragment repoDetailsFragment = RepoDetailsFragment.newInstance(title);
        loadFragment(repoDetailsFragment, getString(R.string.repo_details));
    }

    private void loadNavigationDrawerFragment(FragmentDrawer.NavigationDrawerFragment navigationDrawerFragment) {
        Fragment activeFragment = null;
        String title = "";

        switch (navigationDrawerFragment) {
            case REPO_LIST_FRAG:
                title = getString(R.string.repo_list);
                activeFragment = RepoListFragment.newInstance();
                break;
            case WEATHER_FRAG:
                title = getString(R.string.nav_item_main);
                activeFragment = WeatherFragment.newInstance();
                break;
            default:
                break;
        }

        loadFragment(activeFragment, title);
    }
}