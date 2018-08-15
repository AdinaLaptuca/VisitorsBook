package com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.documentface;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.documentface.DocumentFaceRecognizer;

public class DocumentFaceRecognitionResultExtractor extends BlinkIdExtractor<DocumentFaceRecognizer.Result, DocumentFaceRecognizer>
{

    @Override
    protected void extractData(DocumentFaceRecognizer.Result result) {
        add(R.string.MBDocumentLocation, result.getDocumentLocation().toString());
        add(R.string.MBFaceLocation, result.getFaceLocation().toString());
    }
}