package com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.ViewVisit;

import android.os.Bundle;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.PreviewVisitorData.PreviewVisitorDataFragment;
import com.adinalaptuca.visitorsbook.custom.BaseToolbarFragment;
import com.adinalaptuca.visitorsbook.model.Visit;

public class ViewVisitFragment extends BaseToolbarFragment {

    private static final String EXTRA_VISIT = "EXTRA_VISIT";

    public static ViewVisitFragment newInstance(Visit visit) {
        ViewVisitFragment fragment = new ViewVisitFragment();

        Bundle args = new Bundle();
        args.putParcelable(EXTRA_VISIT, visit);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.viewVisit);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_view_visit;
    }
}
