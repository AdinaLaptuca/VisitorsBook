package com.adinalaptuca.visitorsbook.utils.ocr.extract.blinkid.mrtd;

import com.adinalaptuca.visitorsbook.utils.ocr.extract.blinkid.BlinkIdExtractor;
import com.adinalaptuca.visitorsbook.utils.ocr.extract.blinkinput.TemplateDataExtractor;
import com.microblink.entities.recognizers.blinkid.mrtd.MrtdRecognizer;

public class MrtdRecognitionResultExtractor extends BlinkIdExtractor<MrtdRecognizer.Result, MrtdRecognizer>
{

    @Override
    protected void extractData(MrtdRecognizer.Result result) {
        extractMRZResult(result.getMrzResult());

        TemplateDataExtractor templateDataExtractor = new TemplateDataExtractor();
        mExtractedData.addAll(templateDataExtractor.extract(mContext, mRecognizer));
    }

}
