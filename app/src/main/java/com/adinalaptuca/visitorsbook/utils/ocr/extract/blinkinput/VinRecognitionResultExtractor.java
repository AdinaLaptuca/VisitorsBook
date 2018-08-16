package com.adinalaptuca.visitorsbook.utils.ocr.extract.blinkinput;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.utils.ocr.extract.BaseResultExtractor;
import com.microblink.entities.recognizers.blinkbarcode.vin.VinRecognizer;

public class VinRecognitionResultExtractor extends BaseResultExtractor<VinRecognizer.Result, VinRecognizer>
{

    @Override
    protected void extractData(VinRecognizer.Result result) {
        add(R.string.PPVin, result.getVin());
    }
}