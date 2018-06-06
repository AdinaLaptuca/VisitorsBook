package com.adinalaptuca.visitorsbook.custom;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

public abstract class BaseToolbarFragment extends BaseFragment {
    BaseToolbarActivityInterface toolbarActivityInterface;

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
    }

    public abstract String getToolbarTitle();

    public void setToolbarTitle(){
        if (toolbarActivityInterface != null) {
            toolbarActivityInterface.getToolbar().setTitle(getToolbarTitle());
        }
    }
}
