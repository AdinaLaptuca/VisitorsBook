package com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.austria;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.austria.AustriaIdBackRecognizer;

public class AustrianIDBackSideRecognitionResultExtractor extends BlinkIdExtractor<AustriaIdBackRecognizer.Result, AustriaIdBackRecognizer>
{

    @Override
    protected void extractData(AustriaIdBackRecognizer.Result ausIDBackResult) {
        extractMRZResult(ausIDBackResult.getMrzResult());

        add(R.string.PPHeight, ausIDBackResult.getHeight());
        add(R.string.PPPlaceOfBirth, ausIDBackResult.getPlaceOfBirth());
        add(R.string.PPIssuingAuthority, ausIDBackResult.getIssuingAuthority());
        add(R.string.PPIssueDate, ausIDBackResult.getDateOfIssuance().getDate());
        add(R.string.PPPrincipalResidenceAtIssuance, ausIDBackResult.getPrincipalResidence());
        add(R.string.PPEyeColour, ausIDBackResult.getEyeColour());
        add(R.string.PPDocumentNumber, ausIDBackResult.getDocumentNumber());
    }

}
