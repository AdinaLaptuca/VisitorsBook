package com.example.adinalaptuca.visitorsbook.activities.main.VisitsFragment.UpcomingVisitor;

import com.example.adinalaptuca.visitorsbook.R;
import com.example.adinalaptuca.visitorsbook.activities.main.VisitsFragment.PreviewVisitorData.PreviewVisitorDataFragment;
import com.example.adinalaptuca.visitorsbook.custom.BaseToolbarFragment;

import butterknife.OnClick;

public class UpcomingVisitorFragment extends BaseToolbarFragment {

    @Override
    public String getToolbarTitle() {
        return getString(R.string.addVisitor);
    }

    @Override
    protected int layoutId() {
        return R.layout.component_visits_upcoming_visitor;
    }
}
