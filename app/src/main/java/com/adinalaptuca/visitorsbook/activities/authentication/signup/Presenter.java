package com.adinalaptuca.visitorsbook.activities.authentication.signup;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import com.adinalaptuca.visitorsbook.AppDelegate;
import com.adinalaptuca.visitorsbook.Constants;
import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.model.AutoValueGsonTypeAdapterFactory;
import com.adinalaptuca.visitorsbook.model.Company;
import com.adinalaptuca.visitorsbook.model.Employee;
import com.adinalaptuca.visitorsbook.model.EmployeeRole;
import com.adinalaptuca.visitorsbook.model.Office;
import com.adinalaptuca.visitorsbook.model.Person;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Presenter implements SignupContract.Presenter {

    private SignupContract.View view;

    private List<EmployeeRole> listRoles = new ArrayList<>();
    private List<Company> listCompanies = new ArrayList<>();
    private List<String> listCompaniesNames = new ArrayList<>();
    private Map<String, List<Office>> listOffices = new HashMap<>();

    Presenter(SignupContract.View view) {
        this.view = view;
    }

    @Override
    public void fetchRoles() {
        final CollectionReference ref = FirebaseFirestore.getInstance().collection(Constants.DB_COLLECTION_ROLES);
        ref.addSnapshotListener((Activity) view.getContext(), (documentSnapshot, e) -> {
            Gson gson = AutoValueGsonTypeAdapterFactory.autovalueGson();

            if (documentSnapshot != null && !documentSnapshot.isEmpty()) {
                listRoles.clear();

                for (DocumentSnapshot snapshot : documentSnapshot.getDocuments()) {
                    if (!snapshot.exists() || snapshot.getData() == null)
                        continue;

                    Map<String, Object> data = snapshot.getData();
                    data.put(EmployeeRole.SERIALIZE_NAME_ROLE, snapshot.getId());      // set the role from document's id
                    EmployeeRole employeeRole = gson.fromJson(gson.toJson(data), EmployeeRole.class);
                    listRoles.add(employeeRole);
                }

                view.rolesFetched(listRoles.stream().map(employeeRole -> employeeRole.getRole()).collect(Collectors.toList()));
            }
        });
    }

    @Override
    public void fetchCompanies() {
        final CollectionReference ref = FirebaseFirestore.getInstance().collection(Constants.DB_COLLECTION_COMPANIES);

        ref.addSnapshotListener((Activity) view.getContext(), (documentSnapshot, e) -> {

            listCompanies.clear();
            listCompaniesNames.clear();

            if (documentSnapshot == null || documentSnapshot.getDocuments() == null) {
                view.companiesFetched();
                return;
            }

            Gson gson = AutoValueGsonTypeAdapterFactory.autovalueGson();

            for (DocumentSnapshot snapshot : documentSnapshot.getDocuments()) {
                if (!snapshot.exists() || snapshot.getData() == null)
                    continue;

                Map<String, Object> data = snapshot.getData();
                data.put(Company.SERIALIZE_NAME_SHORT_NAME, snapshot.getId());      // set the getFullName from document's id
                data.remove("offices");
                Company company = gson.fromJson(gson.toJson(data), Company.class);
                listCompanies.add(company);
            }

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

            view.companiesFetched();
        });
    }

    @Override
    public List<String> getCompanies() {
        return listCompaniesNames;
    }

    @Override
    public void fetchOfficesForCompany(final String companyName) {
        if (listOffices.containsKey(companyName)) {
            view.officesFetched(companyName, getOfficesForCompany(companyName));
            return;
        }

        String path = String.format(Locale.getDefault(), "%s/%s/%s", Constants.DB_COLLECTION_COMPANIES, companyName, Company.SERIALIZE_NAME_OFFICES);

        CollectionReference cr = FirebaseFirestore.getInstance().collection(path);

        cr.addSnapshotListener((Activity) view.getContext(), (querySnapshot, error) -> {
            if (error == null)
            {
                Gson gson = AutoValueGsonTypeAdapterFactory.autovalueGson();

                if (listOffices.containsKey(companyName))
                    listOffices.get(companyName).clear();
                else
                    listOffices.put(companyName, new ArrayList<>());

                for (DocumentSnapshot snapshot : querySnapshot.getDocuments()) {
                    if (!snapshot.exists() || snapshot.getData() == null)
                        continue;

                    Map<String, Object> data = snapshot.getData();
                    data.put(Office.SERIALIZE_REFERENCE_ID, snapshot.getId());
                    Office office = gson.fromJson(gson.toJson(data), Office.class);
                    listOffices.get(companyName).add(office);
                }

                view.officesFetched(companyName, getOfficesForCompany(companyName));
            }
        });
    }

    @Override
    public List<String> getOfficesForCompany(String companyName) {
        List<String> result = new ArrayList<>();
        result.add(view.getContext().getResources().getString(R.string.newOffice));            // add new office entry

        result.addAll(listOffices.get(companyName)
                .stream()
                .map(x -> x.getFullName())
                .collect(Collectors.toList()));

        return result;
    }

    @Override
    public Office getOffice(String companyName, String officeName) {
        if (officeName == null || officeName.length() == 0)
            return null;

        Optional<Office> result = listOffices.get(companyName)
                .stream()
                .filter(office -> office.getFullName().equalsIgnoreCase(officeName))
                .findAny();

        if (result.isPresent())
            return result.get();

        return null;
    }

    @Override
    public void trySignUpUserNewCompany(final String companyName,
                                        final String officeName,
                                        final String address,
                                        final String employeeFirstName,
                                        final String employeeLastName,
                                        final String employeeRole,
                                        final String email,
                                        final String password) {

//        AppDelegate.getInstance(view.getContext()).getFirebaseAuth()        // register the user
////                .setLanguageCode()
//                .createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener((Activity) view.getContext(), registerTask -> {
//
//                    if (!registerTask.isSuccessful()) {             // user couldn't be registered
//                        view.dismissLoadingDialog();
//                        view.showToast(view.getContext().getResources().getString(R.string.registration_failed) +
//                                (registerTask.getException() != null ? registerTask.getException().getMessage() : ""));
//                    }
//                    else {            // user registered correctly
//
//                    }
//                });

        createUser(email, password, () -> {
            EmployeeRole role = getEmployeeRole(employeeRole);

            Office office = Office.builder()
                    .setFullName(officeName)
                    .setAddress(address)
                    .setEmployees(Collections.singletonList(
                            Employee.builder()
                                    .setEmail(email)
                                    .setUserID(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setPerson(Person.builder().setFirstName(employeeFirstName).setLastName(employeeLastName).build())
                                    .setEmployeeRole(role)
                                    .build()))
                    .build();

            String path = String.format(Locale.getDefault(), "%s/%s/%s", Constants.DB_COLLECTION_COMPANIES, companyName, Company.SERIALIZE_NAME_OFFICES);
            CollectionReference cr = FirebaseFirestore.getInstance().collection(path);
            cr.add(office)
                    .addOnCompleteListener((Activity) view.getContext(), task -> {
                        view.dismissLoadingDialog();

                        if (task.isSuccessful())
                            view.signedUpSuccesfull();
                        else
                            view.showToast(view.getContext().getResources().getString(R.string.registration_failed) +
                                    (task.getException() != null ? task.getException().getMessage() : ""));
                    });
        });
    }

    @Override
    public void trySignUpInCompany(final String companyName,
                                   final String officeName,
                                   final String employeeFirstName,
                                   final String employeeLastName,
                                   final String employeeRole,
                                   final String email,
                                   final String password) {

//        AppDelegate.getInstance(view.getContext()).getFirebaseAuth()        // register the user
////                .setLanguageCode()
//                .createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener((Activity) view.getContext(), registerTask -> {
//
//                            if (!registerTask.isSuccessful()) {             // user couldn't be registered
//                                view.dismissLoadingDialog();
//                                view.showToast(view.getContext().getResources().getString(R.string.registration_failed) +
//                                        (registerTask.getException() != null ? registerTask.getException().getMessage() : ""));
//                            }
//                            else {            // user registered correctly
//
//                            }
//                        });

        createUser(email, password, () -> {
            Office office = getOffice(companyName, officeName);

            String path = String.format(Locale.getDefault(), "%s/%s/%s/%s",
                    Constants.DB_COLLECTION_COMPANIES,
                    companyName,
                    Company.SERIALIZE_NAME_OFFICES,
                    office.getReferenceID());

            Employee newEmployee = Employee.builder()
                    .setPerson(Person.builder()
                            .setFirstName(employeeFirstName)
                            .setLastName(employeeLastName)
                            .build())
                    .setEmployeeRole(getEmployeeRole(employeeRole))
                    .setEmail(email)
                    .setUserID(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .build();
            office.getEmployees().add(newEmployee);

            DocumentReference cr = FirebaseFirestore.getInstance().document(path);
            cr.set(office, SetOptions.merge()).addOnCompleteListener((Activity) view.getContext(), task -> {
                view.dismissLoadingDialog();

                if (task.isSuccessful())
                    view.signedUpSuccesfull();
                else
                    view.showToast(view.getContext().getResources().getString(R.string.registration_failed) +
                            (task.getException() != null ? task.getException().getMessage() : ""));
            });
        });
    }

    private void createUser(String email, String password, final Runnable callback) {
        AppDelegate.getInstance(view.getContext()).getFirebaseAuth()        // register the user
//                .setLanguageCode()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) view.getContext(), registerTask -> {

//                    registerTask.getResult().getUser().getUid();      TODO add user to loginPaths

                    if (!registerTask.isSuccessful()) {             // user couldn't be registered
                        view.dismissLoadingDialog();
                        view.showToast(view.getContext().getResources().getString(R.string.registration_failed) +
                                (registerTask.getException() != null ? registerTask.getException().getMessage() : ""));
                    }
                    else {            // user registered correctly
                        new Handler().post(callback);
                    }
                });
    }

    private EmployeeRole getEmployeeRole(String employeeRole) {
        return listRoles.stream().filter(str -> str.getRole().equalsIgnoreCase(employeeRole)).findFirst().get();
    }
}
