package com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.PreviewVisitorData;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.print.PrintHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.custom.BaseToolbarFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class PrintBadgeFragment extends BaseToolbarFragment {
    private final static String EXTRA_IMAGE         = "EXTRA_IMAGE";
    private final static String EXTRA_FIRST_NAME    = "EXTRA_FIRST_NAME";
    private final static String EXTRA_LAST_NAME     = "EXTRA_LAST_NAME";

    static PrintBadgeFragment newInstance(Bitmap image, String firstName, String lastName) {
        PrintBadgeFragment fragment = new PrintBadgeFragment();

        Bundle args = new Bundle();
        args.putParcelable(EXTRA_IMAGE, image);
        args.putString(EXTRA_FIRST_NAME, firstName);
        args.putString(EXTRA_LAST_NAME, lastName);
        fragment.setArguments(args);

        return fragment;
    }

    @BindView(R.id.viewContentBadge)
    protected View viewContentBadge;

    @BindView(R.id.imgPhoto)
    protected ImageView imgPhoto;

    @BindView(R.id.lblName)
    protected TextView lblName;

    @BindView(R.id.lblSurname)
    protected TextView lblSurname;

    @Override
    public String getToolbarTitle() {
        return getResources().getString(R.string.printBadge);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_print_badge;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();

        if (bundle != null) {
            imgPhoto.setImageBitmap(bundle.getParcelable(EXTRA_IMAGE));
            lblName.setText(bundle.getString(EXTRA_FIRST_NAME));
            lblSurname.setText(bundle.getString(EXTRA_LAST_NAME));
        }
    }

    @OnClick(R.id.btnPrintBadge)
    protected void printBadge() {
        PrintHelper photoPrinter = new PrintHelper(getActivity());
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);

        viewContentBadge.setDrawingCacheEnabled(true);
        Bitmap bitmap = viewContentBadge.getDrawingCache();
        photoPrinter.printBitmap("visitor badge", bitmap);
    }
}
