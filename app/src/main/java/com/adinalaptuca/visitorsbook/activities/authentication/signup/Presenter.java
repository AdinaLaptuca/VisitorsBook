package com.adinalaptuca.visitorsbook.activities.authentication.signup;

import android.app.Activity;
import android.os.Build;
import android.util.Log;

import com.adinalaptuca.visitorsbook.AppDelegate;
import com.adinalaptuca.visitorsbook.Constants;
import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.model.AutoValueGsonTypeAdapterFactory;
import com.adinalaptuca.visitorsbook.model.Company;
import com.adinalaptuca.visitorsbook.model.Employee;
import com.adinalaptuca.visitorsbook.model.Office;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

public class Presenter implements SignupContract.Presenter {

    private SignupContract.View view;

    private static final String DB_DOC_COMPANIES = "companies";

    private QuerySnapshot documentSnapshot;
    private List<Company> listCompanies = new ArrayList<>();
    private List<String> listCompaniesNames = new ArrayList<>();

    Presenter(SignupContract.View view) {
        this.view = view;
    }

    @Override
    public void getData() {
        final CollectionReference companies = FirebaseFirestore.getInstance().collection(DB_DOC_COMPANIES);
        companies.addSnapshotListener((Activity) view.getContext(), (documentSnapshot, e) -> {
            Presenter.this.documentSnapshot = documentSnapshot;

            listCompanies.clear();

            Gson gson = AutoValueGsonTypeAdapterFactory.autovalueGson();

            for (DocumentSnapshot snapshot : documentSnapshot.getDocuments()) {
                if (!snapshot.exists() || snapshot.getData() == null)
                    continue;

                Map<String, Object> data = snapshot.getData();
                data.put(Company.SERIALIZE_NAME_SHORT_NAME, snapshot.getId());      // set the name from document's id
                Company company = gson.fromJson(gson.toJson(data), Company.class);
                listCompanies.add(company);
            }

            listCompaniesNames.clear();

            if (listCompanies != null) {
                if (Build.VERSION.SDK_INT > 24) {
                    listCompaniesNames.addAll(listCompanies.stream()
                            .map(x -> x.getShortname())
                            .collect(Collectors.toList()));
                }
                else {
                    for (Company company : listCompanies)
                        listCompaniesNames.add(company.getShortname());
                }
            }

            view.dataFetched();
        });
    }

    @Override
    public List<String> getCompanies() {
        return listCompaniesNames;
    }

    @Override
    public List<String> getOfficesForCompany(String companyName) {
        List<String> listOffices = new ArrayList<>();
        listOffices.add(view.getContext().getResources().getString(R.string.newOffice));            // add new office entry

        if (Build.VERSION.SDK_INT > 24) {
            listOffices.addAll(listCompanies.stream()
                    .filter(x -> x.getShortname().equalsIgnoreCase(companyName))
                    .findFirst()
                    .get()
                    .getOffices()
                    .stream()
                    .map(x -> x.getFullname())
                    .collect(Collectors.toList()));
        }

        return listOffices;
    }

    @Override
    public Office getOffice(String companyName, String officeName) {

        for (Company company : listCompanies) {
            if (company.getShortname().equalsIgnoreCase(companyName)) {
                for (Office office : company.getOffices()) {
                    if (office.getFullname().equalsIgnoreCase(officeName))
                        return office;
                }
            }
        }

//        if (Build.VERSION.SDK_INT > 24)
//            return listCompanies.stream()
//                    .filter(x -> x.getShortname().equalsIgnoreCase(officeName))
//                    .findFirst()
//                    .get()
//                    .getOffices()
//                    .stream()
//                    .filter(x -> x.getFullname().equalsIgnoreCase(officeName))
//                    .findFirst()
//                    .get();

        return Office.builder().build();
    }

    @Override
    public void trySignUp(String email, String password) {
        AppDelegate.getInstance(view.getContext()).getFirebaseAuth()
//                .setLanguageCode()
                .createUserWithEmailAndPassword(email, password)
//                .addOnFailureListener(this, e -> {
//
//                    Log.e("singup", "failed: " + e.getMessage());
//                    dismissLoadingDialog();
//                    Toast.makeText(SignupActivity.this, getResources().getString(R.string.registration_failed) + e.getMessage(),
//                            Toast.LENGTH_SHORT).show();
//                })
                .addOnCompleteListener(task -> {
                    view.dismissLoadingDialog();

                    if (!task.isSuccessful()) {
                        view.showToast(view.getContext().getResources().getString(R.string.registration_failed) +
                                (task.getException() != null ? task.getException().getMessage() : ""));
                    } else {
                        view.signedUpSuccesfull();
                    }
                });
    }
}
