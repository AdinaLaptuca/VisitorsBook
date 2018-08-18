package com.adinalaptuca.visitorsbook.activities.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.adinalaptuca.visitorsbook.AppDelegate;
import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.PreviewVisitorData.PreviewVisitorDataFragment;
import com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.VisitsFragment;
import com.adinalaptuca.visitorsbook.custom.BaseToolbarActivity;
import com.adinalaptuca.visitorsbook.custom.BaseToolbarFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseToolbarActivity {

    public static final int ACTIVITY_RESULT = 10;

    @BindView(R.id.drawerLayout)
    protected DrawerLayout drawerLayout;

    private VisitsFragment visitsFragment;

    //fullname, date, time start, time end/ intervievator, sala
    // cnp, ora getCheckin, ora checkout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        visitsFragment = new VisitsFragment();

        //handling tab click event
        replaceFragment(visitsFragment);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PreviewVisitorDataFragment.ACTIVITY_RESULT_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            Fragment fragment = getFragmentManager().findFragmentByTag(PreviewVisitorDataFragment.class.getSimpleName());
            if (fragment != null)
                fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PreviewVisitorDataFragment.PERMISSION_REQUEST_TAKE_PHOTO) {
            PreviewVisitorDataFragment fragment = (PreviewVisitorDataFragment) getFragmentManager().findFragmentByTag(PreviewVisitorDataFragment.class.getSimpleName());
            fragment.takePhotoClicked();
        }
    }

    @OnClick(R.id.lblLogout)
    protected void logoutClicked() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.logoutQuestion)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.OK, ((dialogInterface, which) -> {
                    AppDelegate.getInstance(MainActivity.this).getFirebaseAuth().signOut();
                    setResult(RESULT_OK);
                    finish();
                }))
        .show();
    }

    private void closeDrawer() {
        drawerLayout.closeDrawer(Gravity.START);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main_screen, menu);
//        return true;
//    }

    @OnClick(R.id.lblLanguage)
    protected void changeLanguage() {
        closeDrawer();

        new AlertDialog.Builder(this)
                .setTitle(R.string.change_language)
                .setSingleChoiceItems(getResources().getStringArray(R.array.languages), 0, (dialogInterface, i) -> {
                    Log.e("asd", "clicked: " + i);
                })
                .setPositiveButton(R.string.OK, null)
                .setNegativeButton(R.string.cancel, null)
        .show();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
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
