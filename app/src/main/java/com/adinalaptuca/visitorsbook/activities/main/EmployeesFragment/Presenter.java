package com.adinalaptuca.visitorsbook.activities.main.EmployeesFragment;

import android.util.Log;

import com.adinalaptuca.visitorsbook.model.Employee;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Action1;

public class Presenter implements EmployeesContract.Presenter {

    private EmployeesContract.View view;

    private List<Employee> listEmployees = new ArrayList<>();

    Presenter(EmployeesContract.View view) {
        this.view = view;
    }

    @Override
    public List<Employee> getEmployees() {
        return listEmployees;
    }

    @Override
    public void getData() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference doc = FirebaseFirestore.getInstance().document("test1/employees/");
        // snapshot

        final ArrayList<Map<String, Object>> listData = new ArrayList<>();

        {
            Map<String, Object> map = new HashMap<>();
            map.put("getFirstName", "John");
            map.put("getLastName", "Smith");
            map.put("getFunction", "Lead Android dev");
            listData.add(map);
        }

        List<Employee> list = new ArrayList<>();
        list.add(Employee.builder().setFirstName("John").setLastName("Smith").setFunction("Lead dev").build());
        list.add(Employee.builder().setFirstName("John").setLastName("Doe").setFunction("Junior dev").build());
        list.add(Employee.builder().setFirstName("Duncan").setLastName("McCloud").setFunction("Junior dev").build());
        list.add(Employee.builder().setFirstName("John").setLastName("Wick").setFunction("Junior dev").build());
        list.add(Employee.builder().setFirstName("Indiana").setLastName("jones").setFunction("Junior dev").build());
        list.add(Employee.builder().setFirstName("Han").setLastName("Solo").setFunction("Junior dev").build());
        list.add(Employee.builder().setFirstName("Chewbaka").setFunction("Junior dev").build());
        list.add(Employee.builder().setFirstName("Darth").setLastName("Vader").setFunction("Junior dev").build());

        doc.get().addOnSuccessListener(documentSnapshot -> {

            Employee employee = new Gson().fromJson(documentSnapshot.getData().toString(), Employee.class);
            //documentSnapshot.toObject(Employee.class);
            Log.e("pres", "document: " + employee);
        });

//        doc.set(list.get(0))
//                .addOnSuccessListener(aVoid -> {
//            Log.e("employees pres", "success");
//        })
//        .addOnFailureListener(e -> {
//            Log.e("employees pres", "failure: " + e.getMessage());
//        });

        Observable.just(
                Employee.builder().setFirstName("John").setLastName("Smith").setFunction("Lead dev").build(),
                Employee.builder().setFirstName("John").setLastName("Doe").setFunction("Junior dev").build(),
                Employee.builder().setFirstName("Duncan").setLastName("McCloud").setFunction("Junior dev").build(),
                Employee.builder().setFirstName("John").setLastName("Wick").setFunction("Junior dev").build(),
                Employee.builder().setFirstName("Indiana").setLastName("jones").setFunction("Junior dev").build(),
                Employee.builder().setFirstName("Han").setLastName("Solo").setFunction("Junior dev").build(),
                Employee.builder().setFirstName("Chewbaka").setFunction("Junior dev").build(),
                Employee.builder().setFirstName("Darth").setLastName("Vader").setFunction("Junior dev").build()
        )
                .asObservable()
                .subscribe(new Action1<Employee>() {
                    @Override
                    public void call(Employee employee) {
                        listEmployees.add(employee);
                        view.notifyDataChanged();
                    }
                });
    }
}
