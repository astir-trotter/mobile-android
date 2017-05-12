package com.astir_trotter.atcustom.demoapp.activity.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.astir_trotter.atcustom.demoapp.R;
import com.astir_trotter.atcustom.demoapp.activity.main.fragment.AboutFragment;
import com.astir_trotter.atcustom.demoapp.activity.main.fragment.ChatFragment;
import com.astir_trotter.atcustom.demoapp.activity.main.fragment.FeedbackFragment;
import com.astir_trotter.atcustom.demoapp.activity.main.fragment.HomeFragment;
import com.astir_trotter.atcustom.demoapp.activity.main.fragment.NewsFragment;
import com.astir_trotter.atcustom.demoapp.activity.main.fragment.SettingsFragment;
import com.astir_trotter.atcustom.ui.activity.BaseActivity;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    Toolbar toolbar;
    FragmentManager fragmentManager;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        setupView();

        if (savedInstanceState == null)
            showHome();
    }

    private void setupView() {
        toolbar = (Toolbar) findViewById(R.id.actionbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);

        navigationView = (NavigationView) findViewById(R.id.main_navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                selectDrawerItem(menuItem);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        drawerToggle.syncState();
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void showHome() {
        selectDrawerItem(navigationView.getMenu().getItem(0));
    }

    private void selectDrawerItem(MenuItem menuItem) {
        Class fragmentClass;

        switch (menuItem.getItemId()) {
            case R.id.drawer_home:
                fragmentClass = HomeFragment.class;
                break;
            case R.id.drawer_chat:
                fragmentClass = ChatFragment.class;
                break;
            case R.id.drawer_news:
                fragmentClass = NewsFragment.class;
                break;

            case R.id.drawer_settings:
                fragmentClass = SettingsFragment.class;
                break;
            case R.id.drawer_feedback:
                fragmentClass = FeedbackFragment.class;
                break;
            case R.id.drawer_about:
                fragmentClass = AboutFragment.class;
                break;

            default:
                throw new IllegalArgumentException("Unknown menu item selected.");
        }

        try {
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            fragmentManager.beginTransaction().replace(R.id.main_content_frame, fragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();
    }

//    @Override
//    public void setTitle(CharSequence title) {
//        ((TextView) toolbar.findViewById(R.id.actionbar_title)).setText(title);
//    }
}
