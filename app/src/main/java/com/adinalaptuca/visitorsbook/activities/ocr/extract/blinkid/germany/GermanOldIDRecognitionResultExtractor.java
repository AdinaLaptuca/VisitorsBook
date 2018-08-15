package com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.germany;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.mrtd.MrtdResultExtractor;
import com.microblink.entities.recognizers.blinkid.germany.GermanyOldIdRecognizer;

public class GermanOldIDRecognitionResultExtractor extends MrtdResultExtractor<GermanyOldIdRecognizer.Result, GermanyOldIdRecognizer>
{

    @Override
    protected void extractData(GermanyOldIdRecognizer.Result result) {
        super.extractData(result);

        String placeOfBirth = result.getPlaceOfBirth();
        if (placeOfBirth != null) {
            add(R.string.PPPlaceOfBirth, placeOfBirth);
        }
    }

}
