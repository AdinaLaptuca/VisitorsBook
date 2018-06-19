package com.adinalaptuca.visitorsbook.activities.main;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.adinalaptuca.visitorsbook.AppDelegate;
import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.authentication.login.LoginActivity;
import com.adinalaptuca.visitorsbook.activities.main.EmployeesFragment.EmployeesFragment;
import com.adinalaptuca.visitorsbook.activities.main.RoomsFragment.RoomsFragment;
import com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.TakePhoto.TakePhotoFragment;
import com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.VisitsFragment;
import com.adinalaptuca.visitorsbook.custom.BaseFragment;
import com.adinalaptuca.visitorsbook.custom.BaseToolbarActivity;
import com.adinalaptuca.visitorsbook.custom.BaseToolbarFragment;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseToolbarActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final int ACTIVITY_RESULT = 10;

    @BindView(R.id.tabLayout)
    protected TabLayout tabLayout;

    private List<List<BaseFragment>> stackFragments = new ArrayList<>();
    private VisitsFragment visitsFragment;
    private EmployeesFragment employeesFragment;
    private RoomsFragment       roomsFragment;

    private TakePhotoFragment takePhotoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setActionBar(toolbar);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.visits));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.employees));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.rooms));

        visitsFragment = new VisitsFragment();
        employeesFragment = new EmployeesFragment();
        roomsFragment = new RoomsFragment();

        stackFragments.add(new ArrayList<BaseFragment>(Collections.singletonList(visitsFragment)));
        stackFragments.add(new ArrayList<BaseFragment>(Collections.singletonList(employeesFragment)));
        stackFragments.add(new ArrayList<BaseFragment>(Collections.singletonList(roomsFragment)));

        //handling tab click event
        replaceFragment(stackFragments.get(0).get(0));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                List<BaseFragment> stackAtIndex = stackFragments.get(tab.getPosition());
                replaceFragment(stackAtIndex.get(stackAtIndex.size() - 1));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TakePhotoFragment.ACTIVITY_RESULT_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            Fragment fragment = visitsFragment.getChildFragmentManager().findFragmentByTag("TakePhotoFragment");
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            AppDelegate.getInstance(this).getFirebaseAuth().signOut();
            setResult(RESULT_OK);
            finish();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStackImmediate();

                if (getFragmentManager().findFragmentById(R.id.fragment_container) != null && getFragmentManager().findFragmentById(R.id.fragment_container) instanceof BaseToolbarFragment)
                    ((BaseToolbarFragment) getFragmentManager().findFragmentById(R.id.fragment_container)).setToolbarTitle();

//                if (getFragmentManager().findFragmentById(R.id.fragment_container).getChildFragmentManager().getBackStackEntryCount() > 0)
//                    getFragmentManager().findFragmentById(R.id.fragment_container).getChildFragmentManager().popBackStack();
            }
            else
                super.onBackPressed();
        }
    }
}
