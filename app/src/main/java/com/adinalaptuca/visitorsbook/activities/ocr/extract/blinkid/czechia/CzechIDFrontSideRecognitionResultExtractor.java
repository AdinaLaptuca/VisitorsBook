package com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.czechia;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.czechia.CzechiaIdFrontRecognizer;

public class CzechIDFrontSideRecognitionResultExtractor extends BlinkIdExtractor<CzechiaIdFrontRecognizer.Result, CzechiaIdFrontRecognizer>
{

    @Override
    protected void extractData(CzechiaIdFrontRecognizer.Result result) {
        add(R.string.PPLastName, result.getLastName());
        add(R.string.PPFirstName, result.getFirstName());
        add(R.string.PPDocumentNumber, result.getIdentityCardNumber());
        add(R.string.PPSex, result.getSex());
        add(R.string.PPDateOfBirth, result.getDateOfBirth());
        add(R.string.PPIssueDate, result.getDateOfIssue());
        add(R.string.PPDateOfExpiry, result.getDateOfExpiry());
        add(R.string.PPPlaceOfBirth, result.getPlaceOfBirth());
    }

}
