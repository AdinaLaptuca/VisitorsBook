package com.adinalaptuca.visitorsbook.utils.ocr.extract.blinkid.switzerland;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.utils.ocr.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.switzerland.SwitzerlandIdFrontRecognizer;

public class SwissIDFrontSideRecognitionResultExtractor extends BlinkIdExtractor<SwitzerlandIdFrontRecognizer.Result, SwitzerlandIdFrontRecognizer>
{

    @Override
    protected void extractData(SwitzerlandIdFrontRecognizer.Result result) {
        add(R.string.PPLastName, result.getSurname());
        add(R.string.PPFirstName, result.getGivenName());
        add(R.string.PPDateOfBirth, result.getDateOfBirth());
    }

}

