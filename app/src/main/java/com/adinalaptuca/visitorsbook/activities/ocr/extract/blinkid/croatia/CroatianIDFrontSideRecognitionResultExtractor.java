package com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.croatia;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.croatia.CroatiaIdFrontRecognizer;

public class CroatianIDFrontSideRecognitionResultExtractor extends BlinkIdExtractor<CroatiaIdFrontRecognizer.Result,CroatiaIdFrontRecognizer>
{

    @Override
    protected void extractData(CroatiaIdFrontRecognizer.Result croIdFrontResult) {
        add(R.string.PPLastName, croIdFrontResult.getLastName());
        add(R.string.PPFirstName, croIdFrontResult.getFirstName());
        add(R.string.PPDocumentNumber, croIdFrontResult.getDocumentNumber());
        add(R.string.PPSex, croIdFrontResult.getSex());
        add(R.string.PPCitizenship, croIdFrontResult.getCitizenship());
        add(R.string.PPDateOfBirth, croIdFrontResult.getDateOfBirth());
        add(R.string.PPDateOfExpiry, croIdFrontResult.getDateOfExpiry());
        add(R.string.PPDateOfExpiryPermanent, croIdFrontResult.isDateOfExpiryPermanent());
        add(R.string.PPDocumentBilingual, croIdFrontResult.isDocumentBilingual());
    }

}
