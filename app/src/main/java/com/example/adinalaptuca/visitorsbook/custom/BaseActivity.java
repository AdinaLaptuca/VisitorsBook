package com.example.adinalaptuca.visitorsbook.custom;

import android.app.Activity;
import android.app.Fragment;

import com.example.adinalaptuca.visitorsbook.R;

import butterknife.Unbinder;

public class BaseActivity extends Activity implements BaseFragmentInterface {
    protected Unbinder unbinder;

    public void replaceFragment(Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    public void addFragment(Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .addToBackStack(null)
//                .addToBackStack(fragment.getClass().getName())
                .commit();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        unbinder.unbind();
    }
}
