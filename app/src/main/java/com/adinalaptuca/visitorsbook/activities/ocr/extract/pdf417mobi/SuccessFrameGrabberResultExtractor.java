package com.adinalaptuca.visitorsbook.activities.ocr.extract.pdf417mobi;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.BaseResultExtractor;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.ResultExtractorFactoryProvider;
import com.microblink.entities.recognizers.Recognizer;
import com.microblink.entities.recognizers.successframe.SuccessFrameGrabberRecognizer;

public class SuccessFrameGrabberResultExtractor extends BaseResultExtractor<SuccessFrameGrabberRecognizer.Result, SuccessFrameGrabberRecognizer>
{

    @Override
    protected void extractData(SuccessFrameGrabberRecognizer.Result result) {
        Recognizer slaveRecognizer = mRecognizer.getSlaveRecognizer();
        BaseResultExtractor slaveExtractor = ResultExtractorFactoryProvider.get().createExtractor(slaveRecognizer);
        mExtractedData.addAll(slaveExtractor.extractData(mContext, slaveRecognizer));
        mExtractedData.add(mBuilder.build(
                R.string.PPSuccessFrame,
                mRecognizer.getResult().getSuccessFrame()
        ));
    }
}
