package com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid;

import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.austria.AustrianIDBackSideRecognitionResultExtractor;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.austria.AustrianIDCombinedRecognitionResultExtractor;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.austria.AustrianIDFrontSideRecognitionResultExtractor;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.austria.AustrianPassportRecognitionResultExtractor;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.croatia.CroatianIDBackSideRecognitionResultExtractor;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.croatia.CroatianIDCombinedRecognitionResultExtractor;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.croatia.CroatianIDFrontSideRecognitionResultExtractor;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.czechia.CzechIDBackSideRecognitionResultExtractor;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.czechia.CzechIDCombinedRecognitionResultExtractor;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.czechia.CzechIDFrontSideRecognitionResultExtractor;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.documentface.DocumentFaceRecognitionResultExtractor;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.germany.GermanIDBackSideRecognitionResultExtractor;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.germany.GermanIDCombinedRecognitionResultExtractor;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.germany.GermanIDFrontSideRecognitionResultExtractor;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.germany.GermanOldIDRecognitionResultExtractor;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.germany.GermanPassportRecognitionResultExtractor;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.mrtd.MRTDCombinedRecognitionResultExtractor;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.mrtd.MrtdRecognitionResultExtractor;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.poland.PolishIDBackSideRecognitionResultExtractor;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.poland.PolishIDCombinedRecognitionResultExtractor;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.poland.PolishIDFrontSideRecognitionResultExtractor;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.romania.RomanianIDFrontSideRecognitionResultExtractor;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.slovenia.SlovenianIDBackSideRecognitionResultExtractor;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.slovenia.SlovenianIDCombinedRecognitionResultExtractor;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.slovenia.SlovenianIDFrontSideRecognitionResultExtractor;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.sweden.SwedenDlFrontRecognitionResultExtractor;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.switzerland.SwissDLFrontSideRecognitionResultExtractor;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.switzerland.SwissIDBackSideRecognitionResultExtractor;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.switzerland.SwissIDFrontSideRecognitionResultExtractor;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.switzerland.SwissPassportRecognitionResultExtractor;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkinput.BlinkInputResultExtractorFactory;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.usdl.USDLResultExtractor;
import com.microblink.entities.recognizers.blinkbarcode.usdl.UsdlRecognizer;
import com.microblink.entities.recognizers.blinkid.austria.AustriaCombinedRecognizer;
import com.microblink.entities.recognizers.blinkid.austria.AustriaIdBackRecognizer;
import com.microblink.entities.recognizers.blinkid.austria.AustriaIdFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.austria.AustriaPassportRecognizer;
import com.microblink.entities.recognizers.blinkid.croatia.CroatiaCombinedRecognizer;
import com.microblink.entities.recognizers.blinkid.croatia.CroatiaIdBackRecognizer;
import com.microblink.entities.recognizers.blinkid.croatia.CroatiaIdFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.czechia.CzechiaCombinedRecognizer;
import com.microblink.entities.recognizers.blinkid.czechia.CzechiaIdBackRecognizer;
import com.microblink.entities.recognizers.blinkid.czechia.CzechiaIdFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.documentface.DocumentFaceRecognizer;
import com.microblink.entities.recognizers.blinkid.germany.GermanyCombinedRecognizer;
import com.microblink.entities.recognizers.blinkid.germany.GermanyIdBackRecognizer;
import com.microblink.entities.recognizers.blinkid.germany.GermanyIdFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.germany.GermanyOldIdRecognizer;
import com.microblink.entities.recognizers.blinkid.germany.GermanyPassportRecognizer;
import com.microblink.entities.recognizers.blinkid.mrtd.MrtdCombinedRecognizer;
import com.microblink.entities.recognizers.blinkid.mrtd.MrtdRecognizer;
import com.microblink.entities.recognizers.blinkid.newzealand.NewZealandDlFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.poland.PolandCombinedRecognizer;
import com.microblink.entities.recognizers.blinkid.poland.PolandIdBackRecognizer;
import com.microblink.entities.recognizers.blinkid.poland.PolandIdFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.romania.RomaniaIdFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.slovenia.SloveniaCombinedRecognizer;
import com.microblink.entities.recognizers.blinkid.slovenia.SloveniaIdBackRecognizer;
import com.microblink.entities.recognizers.blinkid.slovenia.SloveniaIdFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.sweden.dl.SwedenDlFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.switzerland.SwitzerlandDlFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.switzerland.SwitzerlandIdBackRecognizer;
import com.microblink.entities.recognizers.blinkid.switzerland.SwitzerlandIdFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.switzerland.SwitzerlandPassportRecognizer;

public class BlinkIdResultExtractorFactory extends BlinkInputResultExtractorFactory
{
    @Override
    protected void addExtractors() {
        super.addExtractors();

        add(UsdlRecognizer.class,
                new USDLResultExtractor());
        add(AustriaIdFrontRecognizer.class,
                new AustrianIDFrontSideRecognitionResultExtractor());
        add(AustriaIdBackRecognizer.class,
                new AustrianIDBackSideRecognitionResultExtractor());
        add(AustriaCombinedRecognizer.class,
                new AustrianIDCombinedRecognitionResultExtractor());
        add(AustriaPassportRecognizer.class,
                new AustrianPassportRecognitionResultExtractor());
        add(CroatiaIdFrontRecognizer.class,
                new CroatianIDFrontSideRecognitionResultExtractor());
        add(CroatiaIdBackRecognizer.class,
                new CroatianIDBackSideRecognitionResultExtractor());
        add(CroatiaCombinedRecognizer.class,
                new CroatianIDCombinedRecognitionResultExtractor());
        add(CzechiaIdBackRecognizer.class,
                new CzechIDBackSideRecognitionResultExtractor());
        add(CzechiaIdFrontRecognizer.class,
                new CzechIDFrontSideRecognitionResultExtractor());
        add(CzechiaCombinedRecognizer.class,
                new CzechIDCombinedRecognitionResultExtractor());
        add(GermanyOldIdRecognizer.class,
                new GermanOldIDRecognitionResultExtractor());
        add(GermanyIdBackRecognizer.class,
                new GermanIDBackSideRecognitionResultExtractor());
        add(GermanyCombinedRecognizer.class,
                new GermanIDCombinedRecognitionResultExtractor());
        add(GermanyIdFrontRecognizer.class,
                new GermanIDFrontSideRecognitionResultExtractor());
        add(GermanyPassportRecognizer.class,
                new GermanPassportRecognitionResultExtractor());
        add(SwitzerlandIdBackRecognizer.class,
                new SwissIDBackSideRecognitionResultExtractor());
        add(SwitzerlandPassportRecognizer.class,
                new SwissPassportRecognitionResultExtractor());
        add(SwitzerlandIdFrontRecognizer.class,
                new SwissIDFrontSideRecognitionResultExtractor());
        add(SwitzerlandDlFrontRecognizer.class,
                new SwissDLFrontSideRecognitionResultExtractor());
        add(DocumentFaceRecognizer.class,
                new DocumentFaceRecognitionResultExtractor());
        add(PolandIdBackRecognizer.class,
                new PolishIDBackSideRecognitionResultExtractor());
        add(PolandIdFrontRecognizer.class,
                new PolishIDFrontSideRecognitionResultExtractor());
        add(PolandCombinedRecognizer.class,
                new PolishIDCombinedRecognitionResultExtractor());
        add(SloveniaIdFrontRecognizer.class,
                new SlovenianIDFrontSideRecognitionResultExtractor());
        add(SloveniaIdBackRecognizer.class,
                new SlovenianIDBackSideRecognitionResultExtractor());
        add(SloveniaCombinedRecognizer.class,
                new SlovenianIDCombinedRecognitionResultExtractor());
        add(RomaniaIdFrontRecognizer.class,
                new RomanianIDFrontSideRecognitionResultExtractor());
        add(MrtdCombinedRecognizer.class,
                new MRTDCombinedRecognitionResultExtractor());
        add(MrtdRecognizer.class,
                new MrtdRecognitionResultExtractor());
        add(SwedenDlFrontRecognizer.class,
                new SwedenDlFrontRecognitionResultExtractor());
    }

}
