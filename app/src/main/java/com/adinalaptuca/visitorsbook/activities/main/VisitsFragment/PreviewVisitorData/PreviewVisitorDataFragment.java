package com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.PreviewVisitorData;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.ElectronicSignature.ElectronicSignatureFragment;
import com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.UpcomingVisitor.UpcomingVisitorFragment;
import com.adinalaptuca.visitorsbook.activities.ocr.ImageSettings;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.BaseResultExtractor;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.RecognitionResultEntry;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.ResultExtractorFactoryProvider;
import com.adinalaptuca.visitorsbook.custom.BaseToolbarFragment;
import com.adinalaptuca.visitorsbook.model.Visit;
import com.adinalaptuca.visitorsbook.utils.ImageUtils;
import com.microblink.entities.parsers.config.fieldbyfield.FieldByFieldBundle;
import com.microblink.entities.recognizers.Recognizer;
import com.microblink.entities.recognizers.RecognizerBundle;
import com.microblink.entities.recognizers.blinkid.romania.RomaniaIdFrontRecognizer;
import com.microblink.uisettings.ActivityRunner;
import com.microblink.uisettings.BaseScanUISettings;
import com.microblink.uisettings.DocumentUISettings;
import com.microblink.uisettings.UISettings;
import com.microblink.uisettings.options.BeepSoundUIOptions;
import com.microblink.uisettings.options.ShowOcrResultMode;
import com.microblink.uisettings.options.ShowOcrResultUIOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PreviewVisitorDataFragment extends BaseToolbarFragment {

    private static final String EXTRA_IMAGE_PATH = "EXTRA_IMAGE_PATH";
    private static final String EXTRA_VISIT = "EXTRA_VISIT";

    public static final int ACTIVITY_RESULT_OCR = 123;
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

    private RecognizerBundle mRecognizerBundle;

    @OnClick({R.id.imgPhoto, R.id.btnTakePhoto})
    public void takePhotoClicked() {
        RomaniaIdFrontRecognizer romaniaFront = new RomaniaIdFrontRecognizer();
        ImageSettings.enableAllImages(romaniaFront);

        mRecognizerBundle = new RecognizerBundle(romaniaFront);
        UISettings activitySettings = new DocumentUISettings(mRecognizerBundle);
        setupActivitySettings(activitySettings, null);
        ActivityRunner.startActivityForResult(this, ACTIVITY_RESULT_OCR, activitySettings);
    }

    private void setupActivitySettings(@NonNull UISettings settings, @Nullable Intent helpIntent) {
        if (settings instanceof BeepSoundUIOptions) {
            // optionally, if you want the beep sound to be played after a scan
            ((BeepSoundUIOptions) settings).setBeepSoundResourceID(R.raw.beep);
        }
        if (settings instanceof ShowOcrResultUIOptions) {
            // If you want, you can disable drawing of OCR results on scan activity. Drawing OCR results can be visually
            // appealing and might entertain the user while waiting for scan to complete, but might introduce a small
            // performance penalty.
            // ((ShowOcrResultUIOptions) settings).setShowOcrResult(false);

            // Enable showing of OCR results as animated dots. This does not have effect if non-OCR recognizer like
            // barcode recognizer is active.
            ((ShowOcrResultUIOptions) settings).setShowOcrResultMode(ShowOcrResultMode.ANIMATED_DOTS);
        }
        if (settings instanceof BaseScanUISettings) {
            // If you want you can have scan activity display the focus rectangle whenever camera
            // attempts to focus, similarly to various camera app's touch to focus effect.
            // By default this is off, and you can turn this on by setting EXTRAS_SHOW_FOCUS_RECTANGLE
            // extra to true.
            // ((BaseScanUISettings) settings).setShowingFocusRectangle(true);

            // If you want, you can enable the pinch to zoom feature of scan activity.
            // By enabling this you allow the user to use the pinch gesture to zoom the camera.
            // By default this is off
            ((BaseScanUISettings) settings).setPinchToZoomAllowed(true);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTIVITY_RESULT_OCR && resultCode == Activity.RESULT_OK && data != null) {
            showOcrData(data);
        } else {
            Toast.makeText(getActivity(), "Scan incrrect!", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == ACTIVITY_RESULT_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            imgPhoto.setImageBitmap(ImageUtils.decodeFile(pickedImagePath));
            ((ViewGroup) btnTakePhoto.getParent()).removeView(btnTakePhoto);
        }
    }

    private void showOcrData(Intent data) {
        RecognizerBundle mRecognizerBundle = new RecognizerBundle();
        mRecognizerBundle.loadFromIntent(data);

        FieldByFieldBundle mFieldByFieldBundle = new FieldByFieldBundle();
        mFieldByFieldBundle.saveToIntent(data);
        mFieldByFieldBundle.getElements();

        Recognizer<Recognizer, Recognizer.Result> recognizers[] = mRecognizerBundle.getRecognizers();

        if (recognizers.length > 0 && recognizers[0].getResult().getResultState() != Recognizer.Result.State.Empty) {
            Recognizer<Recognizer, Recognizer.Result> recognizer = recognizers[0];

            BaseResultExtractor resultExtractor = ResultExtractorFactoryProvider.get().createExtractor(recognizer);
            List<RecognitionResultEntry> extractedData = resultExtractor.extractData(getActivity(), recognizer);
            extractedData.get(0).getKey();

            
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
