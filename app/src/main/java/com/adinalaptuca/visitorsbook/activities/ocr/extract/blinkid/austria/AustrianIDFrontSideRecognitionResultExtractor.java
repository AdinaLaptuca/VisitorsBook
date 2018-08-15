package com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.austria;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.austria.AustriaIdFrontRecognizer;

public class AustrianIDFrontSideRecognitionResultExtractor extends BlinkIdExtractor<AustriaIdFrontRecognizer.Result, AustriaIdFrontRecognizer>
{
    @Override
    protected void extractData(AustriaIdFrontRecognizer.Result ausIdFrontResult) {
        add(R.string.PPLastName, ausIdFrontResult.getSurname());
        add(R.string.PPFirstName, ausIdFrontResult.getGivenName());
        add(R.string.PPDocumentNumber, ausIdFrontResult.getDocumentNumber());
        add(R.string.PPSex, ausIdFrontResult.getSex());
        add(R.string.PPDateOfBirth, ausIdFrontResult.getDateOfBirth().getDate());
    }

}
