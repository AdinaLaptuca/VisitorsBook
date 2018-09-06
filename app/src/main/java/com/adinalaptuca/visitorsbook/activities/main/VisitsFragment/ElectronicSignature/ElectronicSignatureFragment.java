package com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.ElectronicSignature;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.custom.BaseToolbarFragment;
import com.adinalaptuca.visitorsbook.custom.OnItemSelectListener;
import com.ajithvgiri.canvaslibrary.CanvasView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

public class ElectronicSignatureFragment extends BaseToolbarFragment {

    private CanvasView viewSignature;
    private OnItemSelectListener<Bitmap> mOnItemSelectListener;

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

    @Override
    public void onResume() {
        super.onResume();

        setHasOptionsMenu(true);
        setMenuVisibility(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.done, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_done)
            doneClicked();

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab)
    public void clearSignatureClicked() {
        viewSignature.clearCanvas();
    }

    private void doneClicked() {
        mOnItemSelectListener.onItemSelected(this, viewSignature.getDrawingCache());
        getActivity().onBackPressed();
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

    public void setOnItemSelectListener(OnItemSelectListener<Bitmap> listener) {
        this.mOnItemSelectListener = listener;
    }
}
