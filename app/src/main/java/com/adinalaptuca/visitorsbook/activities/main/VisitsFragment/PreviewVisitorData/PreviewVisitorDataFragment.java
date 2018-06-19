package com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.PreviewVisitorData;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;
import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.UpcomingVisitor.UpcomingVisitorFragment;
import com.adinalaptuca.visitorsbook.custom.BaseToolbarFragment;
import com.adinalaptuca.visitorsbook.utils.ImageUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class PreviewVisitorDataFragment extends BaseToolbarFragment {

    private static final String EXTRA_IMAGE_PATH = "EXTRA_IMAGE_PATH";

    @BindView(R.id.imgPhoto)
    protected ImageView imgPhoto;

    @BindView(R.id.lblName)
    protected TextView lblName;

    @BindView(R.id.lblSurname)
    protected TextView lblSurname;

    @BindView(R.id.lblCNP)
    protected TextView lblCNP;

    public static PreviewVisitorDataFragment newInstance(String photoPath) {
        PreviewVisitorDataFragment fragment = new PreviewVisitorDataFragment();

        Bundle args = new Bundle();
        args.putString(EXTRA_IMAGE_PATH, photoPath);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.checkInVisitor);
    }

    @Override
    protected int layoutId() {
        return R.layout.component_visits_view_data;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(EXTRA_IMAGE_PATH))
            imgPhoto.setImageBitmap(ImageUtils.decodeFile(bundle.getString(EXTRA_IMAGE_PATH)));
    }

    @OnClick(R.id.btnFinish)
    public void finishClicked() {
        addFragment(new UpcomingVisitorFragment());
    }

}
