package com.adinalaptuca.visitorsbook.utils.ocr.extract.blinkid.croatia;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.utils.ocr.extract.blinkid.mrtd.MrtdResultExtractor;
import com.microblink.entities.recognizers.blinkid.croatia.CroatiaIdBackRecognizer;

public class CroatianIDBackSideRecognitionResultExtractor extends MrtdResultExtractor<CroatiaIdBackRecognizer.Result, CroatiaIdBackRecognizer>
{

    @Override
    protected void extractData(CroatiaIdBackRecognizer.Result result) {
        super.extractData(result);

        add(R.string.PPAddress, result.getAddress());
        add(R.string.PPDocumentForNonResidents, result.documentForNonResident());
        add(R.string.PPIssuingAuthority, result.getIssuingAuthority());
        add(R.string.PPIssueDate, result.getDateOfIssue());
        add(R.string.PPDateOfExpiryPermanent, result.getDateOfExpiryPermanent());
    }

}
