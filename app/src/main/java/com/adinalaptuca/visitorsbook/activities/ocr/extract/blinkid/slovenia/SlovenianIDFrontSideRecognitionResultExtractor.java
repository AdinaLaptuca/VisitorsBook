package com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.slovenia;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.slovenia.SloveniaIdFrontRecognizer;

public class SlovenianIDFrontSideRecognitionResultExtractor extends BlinkIdExtractor<SloveniaIdFrontRecognizer.Result, SloveniaIdFrontRecognizer>
{

    @Override
    protected void extractData(SloveniaIdFrontRecognizer.Result sloIdFrontResult) {
        add(R.string.PPLastName, sloIdFrontResult.getLastName());
        add(R.string.PPFirstName, sloIdFrontResult.getFirstName());
        add(R.string.PPSex, sloIdFrontResult.getSex());
        add(R.string.PPNationality, sloIdFrontResult.getNationality());
        add(R.string.PPDateOfBirth, sloIdFrontResult.getDateOfBirth());
        add(R.string.PPDateOfExpiry, sloIdFrontResult.getDateOfExpiry());
    }

}
