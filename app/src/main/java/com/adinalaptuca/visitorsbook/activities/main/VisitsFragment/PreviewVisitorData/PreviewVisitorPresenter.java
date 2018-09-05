package com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.PreviewVisitorData;

import android.app.Activity;
import android.graphics.Bitmap;

import com.adinalaptuca.visitorsbook.AppDelegate;
import com.adinalaptuca.visitorsbook.model.Checkin;
import com.adinalaptuca.visitorsbook.model.Office;
import com.adinalaptuca.visitorsbook.model.Visit;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.Locale;

class PreviewVisitorPresenter implements PreviewVisitorContract.Presenter {
    private PreviewVisitorContract.View view;

    PreviewVisitorPresenter(PreviewVisitorContract.View view) {
        this.view = view;
    }

    @Override
    public void doCheckin(Visit visit,
                          String cnp,
                          String idSerial,
                          Bitmap photo,
                          Bitmap signature) {
        // TODO upload photo and signature

//        FirebaseFirestore.getInstance().getFirestoreSettings().

        String path = visit.getReferenceId(view.getContext());
        DocumentReference ref = FirebaseFirestore.getInstance().document(path);

        Visit newVisit = visit.toBuilder()
                .setPerson(visit.getPerson().toBuilder().setCNP(cnp).setSerialNumber(idSerial).build())
                .setCheckin(Checkin.builder().setTimeStart(new Date()).build())
                .build();

        ref.set(newVisit).addOnCompleteListener((Activity) view.getContext(), task -> {
            view.checkinDone();
        });
    }
}
