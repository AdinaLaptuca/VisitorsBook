package com.adinalaptuca.visitorsbook.activities.main.VisitsFragment;

import android.app.Activity;
import android.util.Log;

import com.adinalaptuca.visitorsbook.Constants;
import com.adinalaptuca.visitorsbook.model.AutoValueGsonTypeAdapterFactory;
import com.adinalaptuca.visitorsbook.model.EmployeeRole;
import com.adinalaptuca.visitorsbook.model.Visit;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Presenter implements VisitsContract.Presenter {

    private VisitsContract.View view;

    private List<Visit> listVisits = new ArrayList<>();

    Presenter(VisitsContract.View view) {
        this.view = view;
    }

    @Override
    public List<Visit> getVisits() {
        return listVisits;
    }

    @Override
    public void getData() {

        final CollectionReference ref = FirebaseFirestore.getInstance().collection(Constants.DB_COLLECTION_VISITS);

        ref.addSnapshotListener((Activity) view.getContext(), (documentSnapshot, e) -> {
            Gson gson = AutoValueGsonTypeAdapterFactory.autovalueGson();

            if (documentSnapshot != null && !documentSnapshot.isEmpty()) {
                listVisits.clear();

                DocumentSnapshot snapshot = documentSnapshot.getDocuments().get(0);
                if (snapshot.contains("visitors")) {
                    List<HashMap<String, Object>> visitors = (List<HashMap<String, Object>>) snapshot.get("visitors");

                    for (HashMap<String, Object> map : visitors) {
                        try {
                            Visit visit = gson.fromJson(gson.toJson(map), Visit.class);
                            listVisits.add(visit);
                        }
                        catch (Exception error) {
                            Log.e("presenter", "error: " + error.getMessage());
                        }
                    }
                }
            }

            Collections.sort(listVisits);

            view.notifyDataChanged();
        });
    }
}
