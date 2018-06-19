package com.adinalaptuca.visitorsbook;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.adinalaptuca.visitorsbook.model.Employee;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RealtimeDbTestActivity extends AppCompatActivity {

    private static final String TAG = "RealtimeDbTestActivity";

    @BindView(R.id.txtNume)
    EditText txtNume;

    @BindView(R.id.txtPrenume)
    EditText txtPrenume;

    @BindView(R.id.txtFunctie)
    EditText txtFunctie;

    private DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_firestore);

        ButterKnife.bind(this);
    }

//    private Employee getEmployee() {
//        return Employee.builder()
//                .setFirstName(txtNume.getText().toString())
//                .setLastName(txtPrenume.getText().toString())
//                .setFunction(txtFunctie.getText().toString())
//                .build();
//    }
//
//    @OnClick(R.id.fab2)
//    public void fab2Clicked() {
//        dbRef.child("employees").push().setValue(getEmployee())
//                .addOnSuccessListener(this, aVoid -> {
//                    Log.e(TAG, "succes: " + dbRef.child("employees").push().getKey());
//                })
//                .addOnFailureListener(this, e -> {
//                    Log.e(TAG, "failure: " + dbRef.child("employees").push().getKey());
//                });
//    }
//
//    @OnClick(R.id.fab)
//    public void fabClicked() {
//        Log.e(TAG, "fab clicked");
//
//        final List<Employee> list = new ArrayList<>();
//        list.add(Employee.builder().setFirstName("John").setLastName("Smith").setFunction("Lead dev").build());
//        list.add(Employee.builder().setFirstName("John").setLastName("Doe").setFunction("Junior dev").build());
//        list.add(Employee.builder().setFirstName("Duncan").setLastName("McCloud").setFunction("Junior dev").build());
//        list.add(Employee.builder().setFirstName("John").setLastName("Wick").setFunction("Junior dev").build());
//        list.add(Employee.builder().setFirstName("Indiana").setLastName("jones").setFunction("Junior dev").build());
//        list.add(Employee.builder().setFirstName("Han").setLastName("Solo").setFunction("The solo one").build());
//        list.add(Employee.builder().setFirstName("Chewbaka").setFunction("Junior dev").build());
//        list.add(Employee.builder().setFirstName("Darth").setLastName("Vader").setFunction("Most evil").build());
//        list.add(Employee.builder().setFirstName("Simona").setLastName("Halep").setFunction("#1 Mondial").build());
//
//        Map<String, List<Employee>> listData = new HashMap<>();
//        listData.put("employees", list);
//
//        dbRef.child("employees").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//        //        GenericTypeIndicator<List<Employee>> t = new GenericTypeIndicator<>();
//        //        Log.e(TAG, "1, changed: " + dataSnapshot.getValue(t));
//                Log.e(TAG, "changed: " + dataSnapshot.getValue() + "; key: " + dataSnapshot.getKey());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.e(TAG, "2, changed: " + databaseError.getMessage());
//            }
//        });
//
//
//        dbRef.setValue(listData)
//                .addOnSuccessListener(this, aVoid -> {
//                    Log.e(TAG, "succes: " + dbRef.child("employees").push().getKey());
//                })
//                .addOnFailureListener(this, e -> {
//                    Log.e(TAG, "failure: " + dbRef.child("employees").push().getKey());
//                });
//    }
}
