package com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.PreviewVisitorData;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adinalaptuca.visitorsbook.Constants;
import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.ElectronicSignature.ElectronicSignatureFragment;
import com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.UpcomingVisitor.UpcomingVisitorFragment;
import com.adinalaptuca.visitorsbook.custom.BaseToolbarFragment;
import com.adinalaptuca.visitorsbook.model.Visit;
import com.adinalaptuca.visitorsbook.utils.ImageUtils;
import java.io.File;
import java.util.Date;
import java.util.Locale;
import butterknife.BindView;
import butterknife.OnClick;

public class PreviewVisitorDataFragment extends BaseToolbarFragment {

    private static final String EXTRA_IMAGE_PATH = "EXTRA_IMAGE_PATH";
    private static final String EXTRA_VISIT = "EXTRA_VISIT";

    public static final int ACTIVITY_RESULT_TAKE_PHOTO = 12;
    public static final int PERMISSION_REQUEST_TAKE_PHOTO = 12;

    private File pickedImagePath;

    @BindView(R.id.btnTakePhoto)
    protected View btnTakePhoto;

    @BindView(R.id.imgPhoto)
    protected ImageView imgPhoto;

    @BindView(R.id.lblName)
    protected TextView lblName;

    @BindView(R.id.lblSurname)
    protected TextView lblSurname;

    @BindView(R.id.lblCNP)
    protected TextView lblCNP;

    public static PreviewVisitorDataFragment newInstance(Visit visit) {
        PreviewVisitorDataFragment fragment = new PreviewVisitorDataFragment();

        Bundle args = new Bundle();
        args.putParcelable(EXTRA_VISIT, visit);
        fragment.setArguments(args);

        return fragment;
    }

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

//        Bundle bundle = getArguments();
//        if (bundle != null && bundle.containsKey(EXTRA_IMAGE_PATH))
//            imgPhoto.setImageBitmap(ImageUtils.decodeFile(bundle.getString(EXTRA_IMAGE_PATH)));
    }

    @OnClick({R.id.imgPhoto, R.id.btnTakePhoto})
    public void takePhotoClicked() {

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] {
                    android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    PERMISSION_REQUEST_TAKE_PHOTO);
            return;
        }

        File storagePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), Constants.FOLDER_VISITORS);
        Log.e("PreviewVisitorData", "make: " + storagePath.mkdirs());

        long timestamp = new Date().getTime();
        pickedImagePath = new File(storagePath, String.format(Locale.getDefault(), "vb_%d.jpg", timestamp));

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(pickedImagePath));
        getActivity().startActivityForResult(intent, ACTIVITY_RESULT_TAKE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTIVITY_RESULT_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            imgPhoto.setImageBitmap(ImageUtils.decodeFile(pickedImagePath));
            ((ViewGroup) btnTakePhoto.getParent()).removeView(btnTakePhoto);
        }
    }

    @OnClick(R.id.btnSignature)
    protected void signatureClicked() {
        addFragment(new ElectronicSignatureFragment());
    }

    @OnClick(R.id.btnFinish)
    public void finishClicked() {
        addFragment(new UpcomingVisitorFragment());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (pickedImagePath != null && pickedImagePath.exists())
            pickedImagePath.delete();
    }

}
