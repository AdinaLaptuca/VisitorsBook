package com.adinalaptuca.visitorsbook.activities.drawerMenu.externalRegisters;

import android.app.Activity;

import com.adinalaptuca.visitorsbook.AppDelegate;
import com.adinalaptuca.visitorsbook.model.AutoValueGsonTypeAdapterFactory;
import com.adinalaptuca.visitorsbook.model.Office;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

class ExternalRegisterPresenter implements ExternalRegisterContract.Presenter {
    private ExternalRegisterContract.View view;

    private List<Office> listOffices = new ArrayList<>();

    ExternalRegisterPresenter(ExternalRegisterContract.View view) {
        this.view = view;
    }

    @Override
    public void fetchOffices() {
        String loginPath = AppDelegate.getInstance(view.getContext()).getLoginPath();
        String currentOfficePath = loginPath.substring(loginPath.lastIndexOf("/") + 1);
        final String companyPath = loginPath.substring(0, loginPath.lastIndexOf("/"));

        FirebaseFirestore.getInstance().collection(companyPath)
                .addSnapshotListener((Activity) view.getContext(), (documentSnapshot, e) -> {
                    Gson gson = AutoValueGsonTypeAdapterFactory.autovalueGson();

                    listOffices.clear();

                    for (DocumentSnapshot snapshot : documentSnapshot.getDocuments()) {
                        if (!snapshot.exists() || snapshot.getData() == null)
                            continue;

                        if (snapshot.getId().equals(currentOfficePath))
                            continue;

                        Map<String, Object> data = snapshot.getData();
                        data.put(Office.SERIALIZE_REFERENCE_ID, snapshot.getId());
                        Office office = gson.fromJson(gson.toJson(data), Office.class);
                        listOffices.add(office);
                    }

                    view.officesFetched(listOffices);
                });
    }

    public String getOfficeRefferenceId(int index) {
        String loginPath = AppDelegate.getInstance(view.getContext()).getLoginPath();
        final String companyPath = loginPath.substring(0, loginPath.lastIndexOf("/"));

        return String.format(Locale.getDefault(), "%s/%s",
                companyPath,
                listOffices.get(index).getReferenceID());
    }
}
