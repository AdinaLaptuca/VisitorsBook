package com.adinalaptuca.visitorsbook.utils.ocr.extract.pdf417mobi;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.utils.ocr.extract.BaseResultExtractor;
import com.microblink.entities.recognizers.blinkbarcode.simnumber.SimNumberRecognizer;

public class SimNumberRecognitionResultExtractor extends BaseResultExtractor<SimNumberRecognizer.Result, SimNumberRecognizer>
{

    @Override
    protected void extractData(SimNumberRecognizer.Result result) {
        add(R.string.PPSimNumber, result.getSimNumber());
    }
}