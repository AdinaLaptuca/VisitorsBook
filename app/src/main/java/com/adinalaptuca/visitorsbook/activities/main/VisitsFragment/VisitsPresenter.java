package com.adinalaptuca.visitorsbook.activities.main.VisitsFragment;

import android.app.Activity;
import android.util.Log;

import com.adinalaptuca.visitorsbook.AppDelegate;
import com.adinalaptuca.visitorsbook.Constants;
import com.adinalaptuca.visitorsbook.model.AutoValueGsonTypeAdapterFactory;
import com.adinalaptuca.visitorsbook.model.Checkin;
import com.adinalaptuca.visitorsbook.model.Office;
import com.adinalaptuca.visitorsbook.model.Visit;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class VisitsPresenter implements VisitsContract.Presenter {

    private VisitsContract.View view;

    private List<Visit> listAllVisits = new ArrayList<>();
    private List<Visit> listFilteredVisits = new ArrayList<>();

    private String searchedString = "";

    VisitsPresenter(VisitsContract.View view) {
        this.view = view;
    }

    @Override
    public List<Visit> getVisits() {
        return listFilteredVisits;
    }

    @Override
    public void setSearchString(String query) {
        this.searchedString = query.trim().toLowerCase(Locale.getDefault());
        applyFilter();
    }

    private void applyFilter() {
        listFilteredVisits.clear();

        if (searchedString.isEmpty())
            listFilteredVisits.addAll(listAllVisits);
        else {
            for (Visit visit : listAllVisits) {
                if (visit.getPerson().getFullName().toLowerCase(Locale.getDefault()).contains(searchedString))
                    listFilteredVisits.add(visit);
            }
        }

        view.notifyDataChanged();
    }

    @Override
    public void getData() {

        String path = String.format(Locale.getDefault(), "%s/%s",
                AppDelegate.getInstance(view.getContext()).getLoginPath(),
                Office.SERIALIZE_VISITS);
        final CollectionReference ref = FirebaseFirestore.getInstance().collection(path);

        ref.addSnapshotListener((Activity) view.getContext(), (documentSnapshot, e) -> {
            Gson gson = AutoValueGsonTypeAdapterFactory.autovalueGson();

            if (documentSnapshot != null && !documentSnapshot.isEmpty()) {
                listAllVisits.clear();

                for (DocumentSnapshot snapshot : documentSnapshot.getDocuments()) {
                    try {
                        Map<String, Object> map = snapshot.getData();
                        map.put("id", snapshot.getId());
                        Visit visit = gson.fromJson(gson.toJson(map), Visit.class);
                        listAllVisits.add(visit);
                    }
                    catch (Exception error) {
                        Log.e("presenter", "error: " + error.getMessage());
                    }
                }
            }

            Collections.sort(listAllVisits);

            applyFilter();
        });
    }

    @Override
    public void checkoutVisit(Visit visit) {
        String path = visit.getReferenceId(view.getContext());
        DocumentReference ref = FirebaseFirestore.getInstance().document(path);

        Visit newVisit = visit.toBuilder()
                .setCheckin(visit.getCheckin().toBuilder().setTimeEnd(new Date()).build())
                .build();

        ref.set(newVisit).addOnCompleteListener((Activity) view.getContext(), task -> {
            view.dismissLoadingDialog();
            view.notifyDataChanged();
        });
    }

    @Override
    public void removeVisit(Visit visit) {
        DocumentReference ref = FirebaseFirestore.getInstance().document(visit.getReferenceId(view.getContext()));

        ref.delete().addOnCompleteListener((Activity) view.getContext(), task -> {
            view.dismissLoadingDialog();
            view.notifyDataChanged();
        });
    }
}
