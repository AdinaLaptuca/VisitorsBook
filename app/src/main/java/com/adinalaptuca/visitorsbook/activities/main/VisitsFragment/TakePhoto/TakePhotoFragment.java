package com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.TakePhoto;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.PreviewVisitorData.PreviewVisitorDataFragment;
import com.adinalaptuca.visitorsbook.custom.BaseToolbarFragment;
import butterknife.OnClick;

public class TakePhotoFragment extends BaseToolbarFragment {

    @Override
    public String getToolbarTitle() {
        return getString(R.string.takePhotoFragmentTitle);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_visits_take_photo;
    }

    @OnClick(R.id.btnTakePhoto)
    public void takePhotoClicked() {
        addFragment(new PreviewVisitorDataFragment());
    }
}
