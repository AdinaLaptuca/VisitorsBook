package com.adinalaptuca.visitorsbook.activities.ocr.extract.pdf417mobi;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.BaseResultExtractor;
import com.microblink.entities.recognizers.blinkbarcode.pdf417.Pdf417Recognizer;
import java.util.Arrays;

public class Pdf417RecognitionResultExtractor extends BaseResultExtractor<Pdf417Recognizer.Result, Pdf417Recognizer>
{

    @Override
    protected void extractData(Pdf417Recognizer.Result result) {
        add(R.string.PPUncertain, result.isUncertain());
        add(R.string.PPBarcodeData, result.getStringData());
        byte[] rawDataBytes = result.getRawData();
        add(R.string.PPBarcodeRawData, Arrays.toString(rawDataBytes));
    }

}
