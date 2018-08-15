package com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.mrtd;

import com.adinalaptuca.visitorsbook.R;
import com.microblink.entities.recognizers.blinkid.mrtd.MrtdCombinedRecognizer;

public class MRTDCombinedRecognitionResultExtractor extends MrtdResultExtractor<MrtdCombinedRecognizer.Result, MrtdCombinedRecognizer> {

    @Override
    protected void extractData(MrtdCombinedRecognizer.Result result) {
        super.extractData(result);
        add(R.string.PPDocumentBothSidesMatch, result.isDocumentDataMatch());
    }

}
