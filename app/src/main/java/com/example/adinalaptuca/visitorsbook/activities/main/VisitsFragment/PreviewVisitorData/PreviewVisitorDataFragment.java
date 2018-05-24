package com.example.adinalaptuca.visitorsbook.activities.main.VisitsFragment.PreviewVisitorData;

import com.example.adinalaptuca.visitorsbook.R;
import com.example.adinalaptuca.visitorsbook.custom.BaseFragment;
import com.example.adinalaptuca.visitorsbook.custom.BaseToolbarFragment;

public class PreviewVisitorDataFragment extends BaseToolbarFragment {

    @Override
    public String getToolbarTitle() {
        return getString(R.string.checkInVisitor);
    }

    @Override
    protected int layoutId() {
        return R.layout.component_visits_view_data;
    }

}
