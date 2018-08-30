package com.adinalaptuca.visitorsbook.custom;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;

public abstract class BaseToolbarFragment extends BaseFragment {
    protected BaseToolbarActivityInterface toolbarActivityInterface;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        if (context instanceof BaseToolbarActivityInterface)
            toolbarActivityInterface = (BaseToolbarActivityInterface) getActivity();
    }

    @Override
    public void onResume()
    {
        super.onResume();

        setToolbarTitle();
        toolbarActivityInterface.getToolbar().getMenu().clear();
    }

    public abstract String getToolbarTitle();

    public void setToolbarTitle(){
        if (toolbarActivityInterface != null) {
            toolbarActivityInterface.getToolbar().setTitle(getToolbarTitle());
        }
    }

//    @Override
//    public void onPrepareOptionsMenu(Menu menu) {
//        menu.clear();
//    }
}
