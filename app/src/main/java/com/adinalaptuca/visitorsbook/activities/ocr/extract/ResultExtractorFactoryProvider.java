package com.adinalaptuca.visitorsbook.activities.ocr.extract;

import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.BlinkIdResultExtractorFactory;

public class ResultExtractorFactoryProvider {

    private static final BlinkIdResultExtractorFactory extractorFactory = new BlinkIdResultExtractorFactory();

    public static BaseResultExtractorFactory get() {
        return extractorFactory;
    }

}
