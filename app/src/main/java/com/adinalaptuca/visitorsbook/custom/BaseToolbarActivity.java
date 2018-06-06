package com.adinalaptuca.visitorsbook.custom;

import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import com.adinalaptuca.visitorsbook.R;

public class BaseToolbarActivity extends BaseActivity implements BaseToolbarActivityInterface {
    protected Toolbar toolbar;

    @Override
    @NonNull
    public Toolbar getToolbar() {
        return findViewById(R.id.toolbar);
    }
}
