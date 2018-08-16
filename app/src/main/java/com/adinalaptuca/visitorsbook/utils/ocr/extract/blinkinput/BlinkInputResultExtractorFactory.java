package com.adinalaptuca.visitorsbook.utils.ocr.extract.blinkinput;

import com.adinalaptuca.visitorsbook.utils.ocr.extract.BaseResultExtractorFactory;
import com.adinalaptuca.visitorsbook.utils.ocr.extract.pdf417mobi.BarcodeRecognitionResultExtractor;
import com.adinalaptuca.visitorsbook.utils.ocr.extract.pdf417mobi.Pdf417RecognitionResultExtractor;
import com.adinalaptuca.visitorsbook.utils.ocr.extract.pdf417mobi.SimNumberRecognitionResultExtractor;
import com.adinalaptuca.visitorsbook.utils.ocr.extract.pdf417mobi.SuccessFrameGrabberResultExtractor;
import com.microblink.entities.recognizers.blinkbarcode.barcode.BarcodeRecognizer;
import com.microblink.entities.recognizers.blinkbarcode.pdf417.Pdf417Recognizer;
import com.microblink.entities.recognizers.blinkbarcode.simnumber.SimNumberRecognizer;
import com.microblink.entities.recognizers.blinkbarcode.vin.VinRecognizer;
import com.microblink.entities.recognizers.detector.DetectorRecognizer;
import com.microblink.entities.recognizers.successframe.SuccessFrameGrabberRecognizer;

public class BlinkInputResultExtractorFactory extends BaseResultExtractorFactory
{

    @Override
    protected void addExtractors() {
        add(DetectorRecognizer.class,
                new DetectorRecognitionResultExtractor());
        add(VinRecognizer.class,
                new VinRecognitionResultExtractor());

        // from pdf 417, excluding USDL
        add(SuccessFrameGrabberRecognizer.class,
                new SuccessFrameGrabberResultExtractor());
        add(BarcodeRecognizer.class,
                new BarcodeRecognitionResultExtractor());
        add(Pdf417Recognizer.class,
                new Pdf417RecognitionResultExtractor());
        add(SimNumberRecognizer.class,
                new SimNumberRecognitionResultExtractor());
    }

}
