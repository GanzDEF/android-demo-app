package com.test.xyz.demo.presentation.mainlobby

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import com.test.xyz.demo.R
import com.test.xyz.demo.presentation.common.util.UIHelper
import com.test.xyz.demo.presentation.mainlobby.navdrawer.FragmentDrawer
import com.test.xyz.demo.presentation.projectdetails.ProjectDetailsFragment
import com.test.xyz.demo.presentation.projectlist.ProjectListFragment
import com.test.xyz.demo.presentation.weather.WeatherFragment
import java.util.*

class MainActivity : AppCompatActivity(), FragmentDrawer.FragmentDrawerListener {
    private lateinit var toolbar: Toolbar
    private lateinit var drawerFragment: FragmentDrawer
    private lateinit var fragmentList: MutableList<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize(savedInstanceState)
    }

    override fun onDrawerItemSelected(view: View, navigationDrawerFragment: FragmentDrawer.NavigationDrawerFragment) {
        var activeFragment: Fragment? = null
        var title = ""

        when (navigationDrawerFragment) {
            FragmentDrawer.NavigationDrawerFragment.PROJECT_LIST_FRAG -> {
                title = getString(R.string.projects)
                activeFragment = fragmentList!![0]
            }
            FragmentDrawer.NavigationDrawerFragment.WEATHER_FRAG -> {
                title = getString(R.string.weather)
                activeFragment = fragmentList!![1]
            }
        }

        loadFragment(activeFragment)
        supportActionBar!!.title = title
    }

    fun loadProjectDetailsFragment(title: String) {
        val projectDetailsFragment = ProjectDetailsFragment.newInstance(title)
        loadFragment(projectDetailsFragment)
    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.container_body)
        if (currentFragment is ProjectListFragment) {
            finish()
            return
        }
        super.onBackPressed()
    }

    private fun initialize(savedInstanceState: Bundle?) {
        fragmentList = ArrayList()
        fragmentList!!.add(ProjectListFragment.newInstance())
        fragmentList!!.add(WeatherFragment.newInstance())
        setupNavigationDrawer(this)
        loadFragment(fragmentList!![0])
        supportActionBar!!.setTitle(R.string.projects)
    }

    //region Activity Helpers
    private fun loadFragment(fragment: Fragment?) {
        UIHelper.hideKeyboard(this)

        supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container_body, fragment, null).commit()
    }

    private fun setupNavigationDrawer(listener: FragmentDrawer.FragmentDrawerListener) {
        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        drawerFragment = supportFragmentManager.findFragmentById(R.id.fragment_navigation_drawer) as FragmentDrawer
        drawerFragment!!.setUp(R.id.fragment_navigation_drawer, findViewById<DrawerLayout>(R.id.drawer_layout), toolbar)
        drawerFragment!!.setDrawerListener(listener)
    }
    //endregion
}