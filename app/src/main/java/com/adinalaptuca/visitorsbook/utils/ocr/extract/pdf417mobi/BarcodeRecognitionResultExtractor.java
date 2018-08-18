package com.adinalaptuca.visitorsbook.utils.ocr.extract.pdf417mobi;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.utils.ocr.extract.BaseResultExtractor;
import com.microblink.entities.recognizers.blinkbarcode.barcode.BarcodeRecognizer;
import java.util.Arrays;

public class BarcodeRecognitionResultExtractor extends BaseResultExtractor<BarcodeRecognizer.Result, BarcodeRecognizer>
{

    @Override
    protected void extractData(BarcodeRecognizer.Result result) {
        add(R.string.PPBarcodeType, result.getBarcodeType().name());
        add(R.string.PPUncertain, result.isUncertain());
        add(R.string.PPBarcodeData, result.getStringData());
        byte[] rawDataBytes = result.getRawData();
        add(R.string.PPBarcodeRawData, Arrays.toString(rawDataBytes));
    }

}
