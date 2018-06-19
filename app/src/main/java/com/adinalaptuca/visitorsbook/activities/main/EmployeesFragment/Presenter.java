package com.adinalaptuca.visitorsbook.activities.main.EmployeesFragment;

import android.app.Activity;
import android.util.Log;

import com.adinalaptuca.visitorsbook.model.Employee;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
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

//        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference doc = FirebaseFirestore.getInstance().document("v1/employeesData");

        List<Employee> list = new ArrayList<>();
//        list.add(Employee.builder().setFirstName("John").setLastName("Smith").setFunction("Lead dev").build());
//        list.add(Employee.builder().setFirstName("John").setLastName("Doe").setFunction("Junior dev").build());
//        list.add(Employee.builder().setFirstName("Duncan").setLastName("McCloud").setFunction("Junior dev").build());
//        list.add(Employee.builder().setFirstName("John").setLastName("Wick").setFunction("Junior dev").build());
//        list.add(Employee.builder().setFirstName("Indiana").setLastName("jones").setFunction("Junior dev").build());
//        list.add(Employee.builder().setFirstName("Han").setLastName("Solo").setFunction("The solo one").build());
//        list.add(Employee.builder().setFirstName("Chewbaka").setFunction("Junior dev").build());
//        list.add(Employee.builder().setFirstName("Darth").setLastName("Vader").setFunction("Most evil").build());
//        list.add(Employee.builder().setFirstName("Simona").setLastName("Halep").setFunction("#1 Mondial").build());

        Map<String, List<Employee>> listData = new HashMap<>();
        listData.put("employees", list);

        doc.collection("v1/employeesData/employees")
                .get()
                .addOnSuccessListener((Activity) view.getContext(), (queryDocumentSnapshots) -> {
                    Log.e("pres", "success");
        });


//        doc.addSnapshotListener((Activity) view.getContext(), (documentSnapshot, e) -> {
////            Employee employee = Employee.map2Object(documentSnapshot.fetchCompanies());
////                    new Gson().fromJson(documentSnapshot.fetchCompanies().toString(), Employee.class);
//            //documentSnapshot.toObject(Employee.class);
//            Log.e("pres", "1, document: " + documentSnapshot.fetchCompanies());
//        });
//
        doc.collection("employees").addSnapshotListener((Activity) view.getContext(), (querySnapshot, e) -> {
//            Log.e("pres", "2, document: " + querySnapshot);
            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                Log.e("pres", "2.1, document: " + querySnapshot);
            }
        });
//
//        db.collection("employees").get().addOnSuccessListener((Activity) view.getContext(), queryDocumentSnapshots -> {
//            Log.e("asd", "debug");
//        });
//
//        db.collection("employees").document()
//
//                .set(listData)
////                .add(Employee.builder().setFirstName("Harry 4").setLastName("Shitter").setFunction("Shitter").build())
//        .addOnSuccessListener(documentReference -> {
//            Log.e("pres", "3, document: " + documentReference);
//        })
//        .addOnFailureListener(e -> {
//                        Log.e("employees pres", "3, failure: " + e.getMessage());
//        });

//        doc.collection("employees").add(Employee.builder().setFirstName("Harry").setLastName("Shitter").setFunction("Shitter").build());


//        doc.set(listData)         // write
//                .addOnSuccessListener(aVoid -> {
//            Log.e("employees pres", "success");
//        })
//        .addOnFailureListener(e -> {
//            Log.e("employees pres", "failure: " + e.getMessage());
//        });


//        Observable.just(
//                Employee.builder().setFirstName("John").setLastName("Smith").setFunction("Lead dev").build()
//        )
//                .asObservable()
//                .subscribe(new Action1<Employee>() {
//                    @Override
//                    public void call(Employee employee) {
//                        listEmployees.add(employee);
//                        view.notifyDataChanged();
//                    }
//                });
    }
}
