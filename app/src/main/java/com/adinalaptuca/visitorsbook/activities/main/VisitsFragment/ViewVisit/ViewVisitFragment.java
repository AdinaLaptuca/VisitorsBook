package com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.ViewVisit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adinalaptuca.visitorsbook.Constants;
import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.custom.BaseToolbarFragment;
import com.adinalaptuca.visitorsbook.model.Checkin;
import com.adinalaptuca.visitorsbook.model.Visit;
import com.adinalaptuca.visitorsbook.utils.DateUtils;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class ViewVisitFragment extends BaseToolbarFragment {

    private static final String EXTRA_VISIT = "EXTRA_VISIT";

    @BindView(R.id.imgPhoto)
    protected ImageView imgPhoto;

    @BindView(R.id.viewPhotoPlaceholder)
    protected View viewPhotoPlaceholder;

    @BindView(R.id.txtName)
    protected TextView txtName;

    @BindView(R.id.txtCNP)
    protected TextView txtCNP;

    @BindView(R.id.txtIdSerialNumber)
    protected TextView txtIdSerialNumber;

    @BindView(R.id.txtCheckinTime)
    protected TextView txtCheckinTime;

    @BindView(R.id.txtCheckoutTime)
    protected TextView txtCheckoutTime;

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(EXTRA_VISIT)) {
            Visit visit = (Visit) bundle.getParcelable(EXTRA_VISIT);

            if (visit != null) {
                // TODO show photo
//                viewPhotoPlaceholder.setVisibility(View.INVISIBLE);

                Checkin checkin = visit.getCheckin();

                setLabelInfo(txtName, R.string.name, visit.getFullName());
                setLabelInfo(txtCNP, R.string.cnp, checkin.getCNP());
                setLabelInfo(txtIdSerialNumber, R.string.idSerialNumber, checkin.getSerialNumber());
                setLabelInfo(txtCheckinTime, R.string.checkinTime, DateUtils.dateToString(checkin.getTimeStart(), Constants.DATE_FORMATTER_PATTERN_VIEW_VISITOR_DATE_TIME));
                setLabelInfo(txtCheckoutTime, R.string.checkoutTime, DateUtils.dateToString(checkin.getTimeEnd(), Constants.DATE_FORMATTER_PATTERN_VIEW_VISITOR_DATE_TIME));
            }
        }
    }

    private void setLabelInfo(TextView textView, int stringResourceId, String info) {
        textView.setText(String.format(Locale.getDefault(), "%s %s", getResources().getString(stringResourceId), info != null ? info : ""));
    }

    @OnClick(R.id.btnExport)
    public void exportPdf() {
        // TODO
    }
}
