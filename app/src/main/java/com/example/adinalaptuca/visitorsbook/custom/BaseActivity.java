package com.example.adinalaptuca.visitorsbook.custom;

import android.app.Activity;

import butterknife.Unbinder;

public class BaseActivity extends Activity {
    protected Unbinder unbinder;

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        unbinder.unbind();
    }
}
