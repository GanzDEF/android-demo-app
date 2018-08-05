package com.test.xyz.demo.presentation.mainlobby;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.test.xyz.demo.R;
import com.test.xyz.demo.presentation.common.BaseActivity;
import com.test.xyz.demo.presentation.mainlobby.navdrawer.FragmentDrawer;
import com.test.xyz.demo.presentation.projectdetails.ProjectDetailsFragment;
import com.test.xyz.demo.presentation.projectlist.ProjectListFragment;
import com.test.xyz.demo.presentation.weather.WeatherFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements FragmentDrawer.FragmentDrawerListener {
    private final List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize(savedInstanceState);
    }

    private void initialize(Bundle savedInstanceState) {
        fragmentList.add(ProjectListFragment.newInstance());
        fragmentList.add(WeatherFragment.newInstance());
        setupNavigationDrawer(this);
        loadMainFragment();
    }

    @Override
    public void onDrawerItemSelected(View view, FragmentDrawer.NavigationDrawerFragment navigationDrawerFragment) {
        Fragment activeFragment = null;
        String title = "";

        switch (navigationDrawerFragment) {
            case PROJECT_LIST_FRAG:
                title = getString(R.string.project_list);
                activeFragment = fragmentList.get(0);
                break;
            case WEATHER_FRAG:
                title = getString(R.string.weather);
                activeFragment = fragmentList.get(1);
                break;
        }

        loadFragment(activeFragment, title);
    }

    public void loadProjectDetailsFragment(String title) {
        ProjectDetailsFragment projectDetailsFragment = ProjectDetailsFragment.newInstance(title);
        loadFragment(projectDetailsFragment, getString(R.string.project_details));
    }

    private void loadMainFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_body, fragmentList.get(0), null).commit();

        getSupportActionBar().setTitle(R.string.project_list);
    }
}