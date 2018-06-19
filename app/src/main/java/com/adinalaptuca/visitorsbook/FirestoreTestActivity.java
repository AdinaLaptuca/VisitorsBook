package com.adinalaptuca.visitorsbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.adinalaptuca.visitorsbook.model.AutoValueGsonTypeAdapterFactory;
import com.adinalaptuca.visitorsbook.model.Employee;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FirestoreTestActivity extends AppCompatActivity {

    private static final String TAG = "FirestoreTestActivity";

    @BindView(R.id.txtNume)
    EditText txtNume;

    @BindView(R.id.txtPrenume)
    EditText txtPrenume;

    @BindView(R.id.txtFunctie)
    EditText txtFunctie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_firestore);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.fab)
    public void fabClicked() {
        Log.e(TAG, "fab clicked");

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference doc = FirebaseFirestore.getInstance().document("v1/employeesData");

        final List<Employee> list = new ArrayList<>();
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

//        Employee employee = Employee.builder()
//                .setFirstName(txtNume.getText().toString())
//                .setLastName(txtPrenume.getText().toString())
//                .setFunction(txtFunctie.getText().toString())
//                .build();

//        doc.collection("/employees")
//                .get()
//                .addOnSuccessListener(FirestoreTestActivity.this, (querySnapshot) -> {
//                    Log.e("pres", "1, success: " + querySnapshot.getDocuments());       // return []
//                });
//
//        doc.get()
//                .addOnSuccessListener(FirestoreTestActivity.this, (querySnapshot) -> {
//                    Log.e("pres", "2, success: " + querySnapshot.getData());        // returns data
//                });
//
//        db.document("v1/employeesData")
//                .get()
//                .addOnSuccessListener(this, documentSnapshot -> {
//            Log.e("pres", "3, success: " + documentSnapshot.getData());     // returns data
//        });

//        doc.collection("v1/employeesData/employees")
//                .get()
//                .addOnSuccessListener(this, (querySnapshot) -> {
//                    Log.e("pres", "success: " + querySnapshot);     // empty
//                });

        doc.addSnapshotListener(this, (querySnapshot, e) -> {
            if (querySnapshot.exists() && querySnapshot.contains("employees")) {

                List<Employee> employeeList = new ArrayList<>();
                List<HashMap<String, Object>> asd = (List<HashMap<String, Object>>) querySnapshot.get("employees");

                Gson gson = AutoValueGsonTypeAdapterFactory.autovalueGson();

                try {
                    for (HashMap<String, Object> map : asd) {
                        employeeList.add(gson.fromJson(gson.toJson(map), Employee.class));
                    }
                }
                catch (Exception error) {
                    Log.e("pres", "error: " + error.getMessage());
                }
//                new Gson().fromJson(documentSnapshot.getData().toString(), Employee.class);
                Log.e("pres", "2, document: " + employeeList.size());
            }
        });

////        doc.set(employee, SetOptions.merge());
//        doc.update("employees", "test");


//        db.collection("employees").document()     // scriere
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


//        doc.collection("employees").add(employee)
//        doc.collection("employees").add(listData)
//        doc.set(listData)         // write
//                .addOnSuccessListener(aVoid -> {
//            Log.e("employees pres", "success");
//        })
//        .addOnFailureListener(e -> {
//            Log.e("employees pres", "failure: " + e.getMessage());
//        });
    }
}
