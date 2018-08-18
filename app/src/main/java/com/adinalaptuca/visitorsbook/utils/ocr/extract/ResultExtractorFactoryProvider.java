package com.adinalaptuca.visitorsbook.utils.ocr.extract;

import com.adinalaptuca.visitorsbook.utils.ocr.extract.blinkid.BlinkIdResultExtractorFactory;

public class ResultExtractorFactoryProvider {

    private static final BlinkIdResultExtractorFactory extractorFactory = new BlinkIdResultExtractorFactory();

    public static BaseResultExtractorFactory get() {
        return extractorFactory;
    }

}
