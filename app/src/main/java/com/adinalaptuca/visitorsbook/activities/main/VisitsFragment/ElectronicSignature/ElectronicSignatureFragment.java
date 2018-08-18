package com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.ElectronicSignature;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.custom.BaseToolbarFragment;
import com.ajithvgiri.canvaslibrary.CanvasView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

public class ElectronicSignatureFragment extends BaseToolbarFragment {

    private CanvasView viewSignature;

    @Override
    public String getToolbarTitle() {
        return getString(R.string.enterSignature);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_enter_signature;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewSignature= new CanvasView(getActivity());
        ((ViewGroup) getView().findViewById(R.id.viewContent)).addView(viewSignature, 0);
    }

    @OnClick(R.id.fab)
    public void clearSignatureClicked() {
        viewSignature.clearCanvas();
    }

    private void saveSignature() {
        // TODO call method, use bitmap, choose name

        Bitmap bitmap = viewSignature.getDrawingCache();

        final File Path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Canvas");
        Path.mkdirs();
        String fileName = "Canvas-" + System.currentTimeMillis() + ".jpg";
        File saveFile = new File(Path, fileName);
        FileOutputStream FOS = null;
        try {
            FOS = new FileOutputStream(saveFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, FOS);
            FOS.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
