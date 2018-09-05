package com.adinalaptuca.visitorsbook.activities.drawerMenu.externalRegisters;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.custom.BaseToolbarFragment;

public class ExternalRegisterFragment extends BaseToolbarFragment implements ExternalRegisterContract.Presenter {
    private ExternalRegisterPresenter presenter;

    @Override
    public String getToolbarTitle() {
        return getResources().getString(R.string.external_registers);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_external_register;
    }
}
