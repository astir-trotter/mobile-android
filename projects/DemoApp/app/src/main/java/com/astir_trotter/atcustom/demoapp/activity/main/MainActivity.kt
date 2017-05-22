package com.astir_trotter.atcustom.demoapp.activity.main

import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.util.SparseArrayCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem

import com.astir_trotter.atcustom.demoapp.R
import com.astir_trotter.atcustom.demoapp.activity.main.fragment.AboutFragment
import com.astir_trotter.atcustom.demoapp.activity.main.fragment.ChatFragment
import com.astir_trotter.atcustom.demoapp.activity.main.fragment.FeedbackFragment
import com.astir_trotter.atcustom.demoapp.activity.main.fragment.HomeFragment
import com.astir_trotter.atcustom.demoapp.activity.main.fragment.NewsFragment
import com.astir_trotter.atcustom.demoapp.activity.main.fragment.SettingsFragment
import com.astir_trotter.atcustom.ui.activity.BaseActivity

class MainActivity : BaseActivity() {

    private var toolbar: Toolbar? = null
    private var fragmentManager: FragmentManager? = null
    private var drawerLayout: DrawerLayout? = null
    private var navigationView: NavigationView? = null
    private var drawerToggle: ActionBarDrawerToggle? = null
    private var fragments: SparseArrayCompat<Fragment>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentManager = supportFragmentManager

        setupView()

        if (savedInstanceState == null) {
            fragments = SparseArrayCompat<Fragment>()
            showHome()
        }
    }

    private fun setupView() {
        toolbar = findViewById(R.id.actionbar) as Toolbar
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.main_drawer_layout) as DrawerLayout
        drawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close)
        drawerLayout?.addDrawerListener(drawerToggle!!)

        navigationView = findViewById(R.id.main_navigation_view) as NavigationView
        navigationView?.setNavigationItemSelectedListener { menuItem ->
            selectDrawerItem(menuItem)
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return drawerToggle?.onOptionsItemSelected(item)!! || super.onOptionsItemSelected(item)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        drawerToggle?.syncState()
        super.onPostCreate(savedInstanceState)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        drawerToggle?.onConfigurationChanged(newConfig)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    private fun showHome() = selectDrawerItem(navigationView?.menu!!.getItem(0))

    private fun selectDrawerItem(menuItem: MenuItem) {
        var fragment: Fragment? = fragments?.get(menuItem.itemId)

        if (fragment == null) {
            val fragmentClass: Class<*>
            when (menuItem.itemId) {
                R.id.drawer_home -> fragmentClass = HomeFragment::class.java
                R.id.drawer_chat -> fragmentClass = ChatFragment::class.java
                R.id.drawer_news -> fragmentClass = NewsFragment::class.java

            // Other
                R.id.drawer_settings -> fragmentClass = SettingsFragment::class.java
                R.id.drawer_feedback -> fragmentClass = FeedbackFragment::class.java
                R.id.drawer_about -> fragmentClass = AboutFragment::class.java

                else -> throw IllegalArgumentException("Unknown menu item selected.")
            }

            try {
                fragment = fragmentClass.newInstance() as Fragment
            } catch (e: Exception) {
                e.printStackTrace()
            }

            fragments?.put(menuItem.itemId, fragment)
        }

        menuItem.isChecked = true
        title = menuItem.title
        drawerLayout?.closeDrawers()
        fragmentManager?.beginTransaction()!!.replace(R.id.main_content_frame, fragment).commit()
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName

        private val FRAGMENTS = "fragments"
    }
}
