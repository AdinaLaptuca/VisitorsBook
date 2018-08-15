package com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.czechia;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.mrtd.MrtdResultExtractor;
import com.microblink.entities.recognizers.blinkid.czechia.CzechiaIdBackRecognizer;

public class CzechIDBackSideRecognitionResultExtractor extends MrtdResultExtractor<CzechiaIdBackRecognizer.Result, CzechiaIdBackRecognizer>
{

    @Override
    protected void extractData(CzechiaIdBackRecognizer.Result result) {
        super.extractData(result);
        add(R.string.PPAddress, result.getPermanentStay());
        add(R.string.PPPersonalNumber, result.getPersonalNumber());
        add(R.string.PPAuthority, result.getAuthority());
    }

}
