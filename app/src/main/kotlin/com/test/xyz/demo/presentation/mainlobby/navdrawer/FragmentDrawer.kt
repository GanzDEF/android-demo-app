package com.test.xyz.demo.presentation.mainlobby.navdrawer

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.*
import com.test.xyz.demo.R
import java.util.*

class FragmentDrawer : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var mDrawerToggle: ActionBarDrawerToggle
    private lateinit var drawerListener: FragmentDrawerListener
    private lateinit var adapter: NavigationDrawerAdapter
    private lateinit var containerView: View
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        titles = activity.resources.getStringArray(R.array.nav_drawer_labels)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false)

        recyclerView = layout.findViewById<View>(R.id.drawerList) as RecyclerView

        adapter = NavigationDrawerAdapter(activity, data)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

        recyclerView.addOnItemTouchListener(RecyclerTouchListener(activity, recyclerView, object : ClickListener {
            override fun onClick(view: View, position: Int) {
                drawerListener.onDrawerItemSelected(view, NavigationDrawerFragment.valueOf(position))
                drawerLayout.closeDrawer(containerView)
            }

            override fun onLongClick(view: View?, position: Int) {
            }
        }))

        return layout
    }

    fun setDrawerListener(listener: FragmentDrawerListener) {
        this.drawerListener = listener
    }

    fun setUp(fragmentId: Int, drawerLayout: DrawerLayout, toolbar: Toolbar) {
        containerView = activity.findViewById(fragmentId)

        this.drawerLayout = drawerLayout

        mDrawerToggle = object : ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            override fun onDrawerOpened(drawerView: View?) {
                super.onDrawerOpened(drawerView)
                activity.invalidateOptionsMenu()
            }

            override fun onDrawerClosed(drawerView: View?) {
                super.onDrawerClosed(drawerView)
                activity.invalidateOptionsMenu()
            }

            override fun onDrawerSlide(drawerView: View?, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                toolbar.alpha = 1 - slideOffset / 2
            }
        }

        this.drawerLayout.setDrawerListener(mDrawerToggle)
        this.drawerLayout.post { mDrawerToggle.syncState() }

    }

    interface ClickListener {
        fun onClick(view: View, position: Int)
        fun onLongClick(view: View?, position: Int)
    }

    internal class RecyclerTouchListener(context: Context, recyclerView: RecyclerView, private val clickListener: ClickListener?) : RecyclerView.OnItemTouchListener {

        private val gestureDetector: GestureDetector

        init {
            gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    return true
                }

                override fun onLongPress(e: MotionEvent) {
                    val child = recyclerView.findChildViewUnder(e.x, e.y)
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child))
                    }
                }
            })
        }

        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            val child = rv.findChildViewUnder(e.x, e.y)

            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child))
            }
            return false
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
    }

    interface FragmentDrawerListener {
        fun onDrawerItemSelected(view: View, navigationDrawerFragment: NavigationDrawerFragment)
    }

    enum class NavigationDrawerFragment constructor(val value: Int) {
        PROJECT_LIST_FRAG(0),
        WEATHER_FRAG(1);

        companion object {
            private val map = HashMap<Int, NavigationDrawerFragment>()

            init {
                for (navigationDrawerFragment in NavigationDrawerFragment.values()) {
                    map[navigationDrawerFragment.value] = navigationDrawerFragment
                }
            }

            fun valueOf(value: Int): NavigationDrawerFragment {
                return map[value] ?: NavigationDrawerFragment.values().get(0)
            }
        }
    }

    class NavDrawerItem {
        var isShowNotify: Boolean = false
        var title: String? = null
    }

    companion object {
        private lateinit var titles: Array<String>

        val data: MutableList<NavDrawerItem>
            get() {
                val data = ArrayList<NavDrawerItem>()

                for (i in titles.indices) {
                    val navItem = NavDrawerItem()

                    navItem.title = titles[i]
                    data.add(navItem)
                }

                return data
            }
    }
}
