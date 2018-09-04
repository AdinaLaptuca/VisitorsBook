package com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.UpcomingVisitor;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;

import com.adinalaptuca.visitorsbook.AppDelegate;
import com.adinalaptuca.visitorsbook.model.Checkin;
import com.adinalaptuca.visitorsbook.model.Employee;
import com.adinalaptuca.visitorsbook.model.Office;
import com.adinalaptuca.visitorsbook.model.Person;
import com.adinalaptuca.visitorsbook.model.Room;
import com.adinalaptuca.visitorsbook.model.Visit;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UpcomingVisitorPresenter implements UpcomingVisitorContract.Presenter {
    private UpcomingVisitorContract.View view;

    UpcomingVisitorPresenter(UpcomingVisitorContract.View view) {
        this.view = view;
    }

    @Override
    public void saveVisit(String firstName,
                          String lastName,
                          Date timeStart,
                          Date timeEnd,
                          Room room,
                          List<Employee> participants) {
        Visit visit = Visit.builder()
                .setTimeStart(timeStart)
                .setTimeEnd(timeEnd)
                .setParticipants(participants)
                .setPerson(Person.builder()
                        .setFirstName(firstName)
                        .setLastName(lastName)
                        .build())
                .build();

        String path = String.format(Locale.getDefault(), "%s/%s",
                AppDelegate.getInstance(view.getContext()).getLoginPath(),
                Office.SERIALIZE_VISITS);

        CollectionReference ref = FirebaseFirestore.getInstance().collection(path);
        ref.add(visit)
            .addOnCompleteListener((Activity) view.getContext(),
                    task -> view.visitSaved());
    }

    @Override
    public void saveVisitAndCheckin(String firstName,
                                    String lastName,
                                    Date timeStart,
                                    Date timeEnd,
                                    Room room,
                                    List<Employee> participants,
                                    String cnp,
                                    String idSeries,
                                    Bitmap photo,
                                    Bitmap electronicSignature) {

        // TODO salveaza imagini

        Visit visit = Visit.builder()
                .setTimeStart(timeStart)
                .setTimeEnd(timeEnd)
                .setParticipants(participants)
                .setPerson(Person.builder()
                        .setFirstName(firstName)
                        .setLastName(lastName)
                        .setCNP(cnp)
                        .setSerialNumber(idSeries)
                        .build())
                .setCheckin(Checkin.builder()
                        .setTimeStart(timeStart)
                        .build())
                .build();

        String path = String.format(Locale.getDefault(), "%s/%s",
                AppDelegate.getInstance(view.getContext()).getLoginPath(),
                Office.SERIALIZE_VISITS);

        CollectionReference ref = FirebaseFirestore.getInstance().collection(path);
        ref.add(visit)
                .addOnCompleteListener((Activity) view.getContext(),
                        task -> view.visitSaved());
    }
}
