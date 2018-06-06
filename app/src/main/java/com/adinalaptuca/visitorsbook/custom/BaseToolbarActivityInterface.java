package com.adinalaptuca.visitorsbook.custom;

import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;

public interface BaseToolbarActivityInterface extends BaseActivityInterface {

    @NonNull Toolbar getToolbar();
}
