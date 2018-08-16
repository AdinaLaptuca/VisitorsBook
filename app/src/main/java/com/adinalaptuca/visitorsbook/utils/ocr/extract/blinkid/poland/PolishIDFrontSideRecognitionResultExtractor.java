package com.adinalaptuca.visitorsbook.utils.ocr.extract.blinkid.poland;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.utils.ocr.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.poland.PolandIdFrontRecognizer;

public class PolishIDFrontSideRecognitionResultExtractor extends BlinkIdExtractor<PolandIdFrontRecognizer.Result, PolandIdFrontRecognizer>
{

    @Override
    protected void extractData(PolandIdFrontRecognizer.Result result) {
        add(R.string.PPLastName, result.getSurname());
        add(R.string.PPFirstName, result.getGivenNames());
        add(R.string.PPFamilyName, result.getFamilyName());
        add(R.string.PPParentNames, result.getParentsGivenNames());
        add(R.string.PPSex, result.getSex());
        add(R.string.PPDateOfBirth, result.getDateOfBirth());
    }

}

