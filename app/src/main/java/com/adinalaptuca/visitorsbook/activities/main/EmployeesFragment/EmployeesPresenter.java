package com.adinalaptuca.visitorsbook.activities.main.EmployeesFragment;

import android.app.Activity;

import com.adinalaptuca.visitorsbook.AppDelegate;
import com.adinalaptuca.visitorsbook.model.AutoValueGsonTypeAdapterFactory;
import com.adinalaptuca.visitorsbook.model.Employee;
import com.adinalaptuca.visitorsbook.model.Office;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class EmployeesPresenter implements EmployeesContract.Presenter {

    private EmployeesContract.View view;

    private List<Employee> listFilteredEmployees = new ArrayList<>();
    private List<Employee> listAllEmployess = new ArrayList<>();

    private String searchStr = "";

    EmployeesPresenter(EmployeesContract.View view) {
        this.view = view;
    }

    @Override
    public List<Employee> getEmployees() {
        return listFilteredEmployees;
    }

    @Override
    public void fetchEmployees() {
        String loginPath = AppDelegate.getInstance(view.getContext()).getLoginPath();
        String path =
        String.format(Locale.getDefault(), "%s",
                loginPath.substring(0, loginPath.lastIndexOf("/")));

        FirebaseFirestore.getInstance().collection(path)
                .addSnapshotListener((Activity) view.getContext(), (querySnapshot, e) -> {
                    if (e == null) {
                        Gson gson = AutoValueGsonTypeAdapterFactory.autovalueGson();
                        listAllEmployess.clear();

                        for (DocumentSnapshot snapshot : querySnapshot.getDocuments()) {
                            Office office = gson.fromJson(gson.toJson(snapshot.getData()), Office.class);
                            listAllEmployess.addAll(office.getEmployees());
                        }

                        searchEmployees(searchStr);
                    }
                });
    }

    @Override
    public void searchEmployees(String filter) {

        Locale locale = Locale.getDefault();
        this.searchStr = filter.toLowerCase(locale);

        listFilteredEmployees.clear();

        if (!searchStr.trim().isEmpty()) {
            listFilteredEmployees.addAll(
                listAllEmployess.stream()
                        .filter(employee -> employee.getPerson().getFullName().toLowerCase(locale).contains(searchStr))
                        .collect(Collectors.toList()));
        }
        else
            listFilteredEmployees.addAll(listAllEmployess);

        view.employeesFetched();
    }
}
