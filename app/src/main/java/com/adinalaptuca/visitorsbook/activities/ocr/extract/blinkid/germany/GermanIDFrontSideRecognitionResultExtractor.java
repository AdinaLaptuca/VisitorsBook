package com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.germany;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.germany.GermanyIdFrontRecognizer;

public class GermanIDFrontSideRecognitionResultExtractor extends BlinkIdExtractor<GermanyIdFrontRecognizer.Result, GermanyIdFrontRecognizer>
{

    @Override
    protected void extractData(GermanyIdFrontRecognizer.Result result) {
        add(R.string.PPLastName, result.getLastName());
        add(R.string.PPFirstName, result.getFirstName());
        add(R.string.PPNationality, result.getNationality());
        add(R.string.PPPlaceOfBirth, result.getPlaceOfBirth());
        add(R.string.PPDateOfBirth, result.getDateOfBirth());
        add(R.string.PPDocumentNumber, result.getDocumentNumber());
        add(R.string.PPDateOfExpiry, result.getDateOfExpiry());
        add(R.string.PPCANNumber, result.getCanNumber());
    }

}
