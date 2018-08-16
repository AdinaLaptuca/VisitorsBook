package com.adinalaptuca.visitorsbook.utils.ocr.extract.blinkid.usdl;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.utils.ocr.extract.RecognitionResultEntry;
import com.adinalaptuca.visitorsbook.utils.ocr.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkbarcode.usdl.UsdlKeys;
import com.microblink.entities.recognizers.blinkid.usdl.UsdlCombinedRecognizer;

public class USDLCombinedResultExtractor extends BlinkIdExtractor<UsdlCombinedRecognizer.Result, UsdlCombinedRecognizer>
{

    @Override
    protected void extractData(UsdlCombinedRecognizer.Result result) {
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
