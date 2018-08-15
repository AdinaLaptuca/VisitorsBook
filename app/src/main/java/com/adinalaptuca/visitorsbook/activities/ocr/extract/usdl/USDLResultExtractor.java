package com.adinalaptuca.visitorsbook.activities.ocr.extract.usdl;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.BaseResultExtractor;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.RecognitionResultEntry;
import com.microblink.entities.recognizers.blinkbarcode.usdl.UsdlKeys;
import com.microblink.entities.recognizers.blinkbarcode.usdl.UsdlRecognizer;

public class USDLResultExtractor extends BaseResultExtractor<UsdlRecognizer.Result, UsdlRecognizer>
{

    @Override
    protected void extractData(UsdlRecognizer.Result result) {
        for (UsdlKeys key : UsdlKeys.values()) {
            mExtractedData.add(new RecognitionResultEntry(key.name(), result.getField(key)));
        }

        StringBuilder optionalDataBuilder = new StringBuilder();
        String[] optionalElements = result.getOptionalElements();
        for (String d : optionalElements) {
            optionalDataBuilder.append(d);
            optionalDataBuilder.append('\n');
        }
        String optionalData = optionalDataBuilder.toString().trim();
        add(R.string.PPOptionalData, optionalData);
    }
}
