package com.adinalaptuca.visitorsbook.utils.ocr.extract.blinkid.slovenia;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.utils.ocr.extract.blinkid.mrtd.MrtdResultExtractor;
import com.microblink.entities.recognizers.blinkid.slovenia.SloveniaIdBackRecognizer;

public class SlovenianIDBackSideRecognitionResultExtractor extends MrtdResultExtractor<SloveniaIdBackRecognizer.Result, SloveniaIdBackRecognizer>
{

    @Override
    protected void extractData(SloveniaIdBackRecognizer.Result result) {
        super.extractData(result);
        add(R.string.PPAddress, result.getAddress());
        add(R.string.PPIssuingAuthority, result.getAuthority());
        add(R.string.PPIssueDate, result.getDateOfIssue());
    }

}
