package com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.germany;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.ocr.extract.blinkid.mrtd.MrtdResultExtractor;
import com.microblink.entities.recognizers.blinkid.germany.GermanyPassportRecognizer;
import com.microblink.results.date.Date;

public class GermanPassportRecognitionResultExtractor extends MrtdResultExtractor<GermanyPassportRecognizer.Result, GermanyPassportRecognizer>
{

    @Override
    protected void extractData(GermanyPassportRecognizer.Result result) {
        super.extractData(result);

        String name = result.getName();
        if (name != null && !name.isEmpty()) {
            add(R.string.PPFirstName, name);
        }

        String surname = result.getSurname();
        if (surname != null && !surname.isEmpty()) {
            add(R.string.PPLastName, surname);
        }

        String authority = result.getAuthority();
        if (authority != null && !authority.isEmpty()) {
            add(R.string.PPAuthority, authority);
        }

        Date dateOfIssue = result.getDateOfIssue();
        if (dateOfIssue != null) {
            add(R.string.PPIssueDate, dateOfIssue);
        }

        String nat = result.getNationality();
        if (nat != null && !nat.isEmpty()) {
            add(R.string.PPNationality, nat);
        }

        String placeOfBirth = result.getPlaceOfBirth();
        if (placeOfBirth != null) {
            add(R.string.PPPlaceOfBirth, placeOfBirth);
        }
    }

}
