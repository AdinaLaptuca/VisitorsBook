package com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.PreviewVisitorData;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.ElectronicSignature.ElectronicSignatureFragment;
import com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.UpcomingVisitor.UpcomingVisitorFragment;
import com.adinalaptuca.visitorsbook.utils.ocr.ImageSettings;
import com.adinalaptuca.visitorsbook.utils.ocr.extract.BaseResultExtractor;
import com.adinalaptuca.visitorsbook.utils.ocr.extract.RecognitionResultEntry;
import com.adinalaptuca.visitorsbook.utils.ocr.extract.ResultExtractorFactoryProvider;
import com.adinalaptuca.visitorsbook.custom.BaseToolbarFragment;
import com.adinalaptuca.visitorsbook.model.Visit;
import com.microblink.entities.recognizers.Recognizer;
import com.microblink.entities.recognizers.RecognizerBundle;
import com.microblink.entities.recognizers.blinkid.eudl.EudlCountry;
import com.microblink.entities.recognizers.blinkid.eudl.EudlRecognizer;
import com.microblink.entities.recognizers.blinkid.romania.RomaniaIdFrontRecognizer;
import com.microblink.uisettings.ActivityRunner;
import com.microblink.uisettings.BaseScanUISettings;
import com.microblink.uisettings.DocumentUISettings;
import com.microblink.uisettings.UISettings;
import com.microblink.uisettings.options.BeepSoundUIOptions;
import com.microblink.uisettings.options.ShowOcrResultMode;
import com.microblink.uisettings.options.ShowOcrResultUIOptions;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class PreviewVisitorDataFragment extends BaseToolbarFragment {

    private static final String EXTRA_IMAGE_PATH = "EXTRA_IMAGE_PATH";
    private static final String EXTRA_VISIT = "EXTRA_VISIT";

    public static final int ACTIVITY_RESULT_OCR_ROMANIAN         = 121;
    public static final int ACTIVITY_RESULT_OCR_DRIVER_LICENSE   = 122;
    public static final int ACTIVITY_RESULT_OCR_GERMAN           = 123;
    
    public static final int ACTIVITY_RESULT_TAKE_PHOTO = 12;
    public static final int PERMISSION_REQUEST_TAKE_PHOTO = 12;


    private RecognizerBundle mRecognizerBundle;

    @BindView(R.id.btnTakePhoto)
    protected View btnTakePhoto;

    @BindView(R.id.imgPhoto)
    protected ImageView imgPhoto;

    @BindView(R.id.viewPhotoPlaceholder)
    protected View viewPhotoPlaceholder;

    @BindView(R.id.lblName)
    protected TextView lblName;

    @BindView(R.id.lblSurname)
    protected TextView lblSurname;

    @BindView(R.id.lblCNP)
    protected TextView lblCNP;

    @BindView(R.id.lblSeries)
    protected TextView lblSeries;

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

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(EXTRA_VISIT)) {
            Visit visit = (Visit) bundle.getParcelable(EXTRA_VISIT);

            lblName.setText(visit.getFirstName());
            lblSurname.setText(visit.getLastName());
        }
//            imgPhoto.setImageBitmap(ImageUtils.decodeFile(bundle.getString(EXTRA_IMAGE_PATH)));
    }

    @OnClick({R.id.imgPhoto, R.id.btnTakePhoto})
    public void takePhotoClicked() {
        RomaniaIdFrontRecognizer romaniaFront = new RomaniaIdFrontRecognizer();
        ImageSettings.enableAllImages(romaniaFront);

        mRecognizerBundle = new RecognizerBundle(romaniaFront);
        UISettings activitySettings = new DocumentUISettings(mRecognizerBundle);
        setupActivitySettings(activitySettings, null);
        ActivityRunner.startActivityForResult(this, ACTIVITY_RESULT_OCR_ROMANIAN, activitySettings);
    }

    @OnLongClick({R.id.imgPhoto, R.id.btnTakePhoto})
    public boolean chooseOcrOption() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Choose input method")        //TODO translate
                .setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Arrays.asList("Romanian ID card", "Driver license", "German ID card")),    // TODO translate
                        ((dialog, which) -> {
//                            Log.e("asd", "which");
                            if (which == 0)
                                takePhotoClicked();
                            else if (which == 1) {
                                EudlRecognizer eudl = new EudlRecognizer(EudlCountry.EUDL_COUNTRY_GERMANY);
                                ImageSettings.enableAllImages(eudl);

                                mRecognizerBundle = new RecognizerBundle(eudl);
                                UISettings activitySettings = new DocumentUISettings(mRecognizerBundle);
                                setupActivitySettings(activitySettings, null);
                                ActivityRunner.startActivityForResult(this, ACTIVITY_RESULT_OCR_DRIVER_LICENSE, activitySettings);
                            }
                        }))
                .setNegativeButton(R.string.cancel, null)
                .show();

        return true;
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
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == ACTIVITY_RESULT_OCR_ROMANIAN || requestCode == ACTIVITY_RESULT_OCR_GERMAN) {
                showOcrData(extractData(data), R.string.PPFirstName, R.string.PPLastName, R.string.PPCNP, R.string.PPSeries, R.string.PPIdentityCardNumber);
            }
            else if (requestCode == ACTIVITY_RESULT_OCR_DRIVER_LICENSE) {
                showOcrData(extractData(data), R.string.PPFirstName, R.string.PPLastName, R.string.PPDriverNumber, 0, 0);
            }
            else {
                Toast.makeText(getActivity(), "Scan incrrect!", Toast.LENGTH_SHORT).show();     // TODO translate
            }
        }
    }

    private List<RecognitionResultEntry> extractData(Intent data) {
        RecognizerBundle mRecognizerBundle = new RecognizerBundle();
        mRecognizerBundle.loadFromIntent(data);

        Recognizer<Recognizer, Recognizer.Result> recognizers[] = mRecognizerBundle.getRecognizers();

        if (recognizers.length > 0 && recognizers[0].getResult().getResultState() != Recognizer.Result.State.Empty) {
            Recognizer<Recognizer, Recognizer.Result> recognizer = recognizers[0];

            BaseResultExtractor resultExtractor = ResultExtractorFactoryProvider.get().createExtractor(recognizer);
            return resultExtractor.extractData(getActivity(), recognizer);
        }

        return null;
    }

    private void showOcrData(List<RecognitionResultEntry> extractedData,
            int idFirstName,
            int idLastName,
            int idCNP,
            int idSeries, int idSeriesNumber) {

        if (extractedData != null)
            return;

            btnTakePhoto.setVisibility(View.GONE);

            imgPhoto.setImageBitmap(getResultEntry(extractedData, R.string.MBFaceImage).getImageValue());
            viewPhotoPlaceholder.setVisibility(View.INVISIBLE);

            lblName.setText(getResultEntry(extractedData, idFirstName).getValue());
            lblSurname.setText(getResultEntry(extractedData, idLastName).getValue());
            lblCNP.setText(getResultEntry(extractedData, idCNP).getValue());

            if (idSeries != 0)
                lblSeries.setText(String.format("%s %s",
                        getResultEntry(extractedData, idSeries).getValue(), getResultEntry(extractedData, idSeriesNumber).getValue()));
    }

    private RecognitionResultEntry getResultEntry(List<RecognitionResultEntry> extractedData, int keyId) {
        return getResultEntry(extractedData, getResources().getString(keyId));
    }

    private RecognitionResultEntry getResultEntry(List<RecognitionResultEntry> extractedData, String key) {
        return extractedData.stream().filter(entry -> entry.getKey().equals(key)).findAny().get();
    }

    @OnClick(R.id.btnEnterManually)
    public void enterManuallyClicked() {
        btnTakePhoto.setVisibility(View.GONE);
    }

    @OnClick(R.id.btnSignature)
    protected void signatureClicked() {
        addFragment(new ElectronicSignatureFragment());
    }

    @OnClick(R.id.btnFinish)
    public void finishClicked() {
//        addFragment(new UpcomingVisitorFragment());

        if (!validateData())
            return;


    }

    private boolean validateData() {
        for (TextView mandatoryField : Arrays.asList(lblName, lblSurname, lblCNP)) {
            if (mandatoryField.getText().toString().isEmpty()) {
                mandatoryField.requestFocus();
                mandatoryField.setError(getResources().getString(R.string.cant_be_empty));
                return false;
            }
        }

        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
