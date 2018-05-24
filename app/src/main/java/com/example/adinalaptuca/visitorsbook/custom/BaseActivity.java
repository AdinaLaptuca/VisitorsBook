package com.example.adinalaptuca.visitorsbook.custom;

import android.app.Activity;
import android.app.Fragment;
import android.util.Log;
import android.widget.Toolbar;

import com.example.adinalaptuca.visitorsbook.R;

import butterknife.Unbinder;

public class BaseActivity extends Activity implements BaseActivityInterface {
    protected Unbinder unbinder;

    public void replaceFragment(Fragment fragment) {

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName())
                .commit();
    }

    public void addFragment(Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(null)
//                .addToBackStack(fragment.getClass().getName())
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }
        else
            super.onBackPressed();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        unbinder.unbind();
    }
}
