package com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.ElectronicSignature;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.custom.BaseToolbarFragment;

public class ElectronicSignatureFragment extends BaseToolbarFragment {
    @Override
    public String getToolbarTitle() {
        return getString(R.string.enterSignature);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_enter_signature;
    }
}
