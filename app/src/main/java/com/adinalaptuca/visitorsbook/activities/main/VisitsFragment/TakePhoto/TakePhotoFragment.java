package com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.TakePhoto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.PreviewVisitorData.PreviewVisitorDataFragment;
import com.adinalaptuca.visitorsbook.custom.BaseToolbarFragment;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.Locale;

import butterknife.OnClick;

public class TakePhotoFragment extends BaseToolbarFragment {

    public static final int ACTIVITY_RESULT_TAKE_PHOTO = 12;

    private String pickedImagePath;

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
        File storagePath = Environment.getExternalStorageDirectory();
        storagePath.mkdirs();

        long timestamp = new Date().getTime();
        File outFile = new File(storagePath, String.format(Locale.getDefault(), "vb_%d.jpg", timestamp));

        pickedImagePath = Uri.fromFile(outFile).toString();

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));
        getActivity().startActivityForResult(intent, ACTIVITY_RESULT_TAKE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        addFragment(PreviewVisitorDataFragment.newInstance(pickedImagePath));
    }
}
