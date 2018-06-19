package com.adinalaptuca.visitorsbook.activities.authentication.signup;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.custom.BaseActivity;
import com.adinalaptuca.visitorsbook.activities.authentication.AuthenticationUtils;
import com.adinalaptuca.visitorsbook.model.Office;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class SignupActivity extends BaseActivity implements SignupContract.View, AdapterView.OnItemSelectedListener {

    public static final int ACTIVITY_RESULT = 11;

    private static final String TAG = "SignupActivity";

    private Presenter presenter;

    @BindView(R.id.spinnerCompany)
    protected Spinner spinnerCompany;
    private ArrayAdapter<String> adapterCompany;

    @BindView(R.id.spinnerOffice)
    protected Spinner spinnerOffice;
    private ArrayAdapter<String> adapterOffice;

    @BindView(R.id.txtOfficeName)
    protected EditText txtOfficeName;

    @BindView(R.id.txtAddress)
    protected EditText txtAddress;

    @BindView(R.id.txtEmployeeName)
    protected EditText txtEmployeeName;

    @BindView(R.id.spinnerRole)
    protected Spinner spinnerRole;

    @BindView(R.id.txtEmail)
    protected EditText txtEmail;

    @BindView(R.id.txtPassword)
    protected EditText txtPassword;

    @BindView(R.id.txtRetypePassword)
    protected EditText txtRetypePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        unbinder = ButterKnife.bind(this);

        presenter = new Presenter(this);
        presenter.getData();

        adapterCompany = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, presenter.getCompanies());
        adapterCompany.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCompany.setAdapter(adapterCompany);
        spinnerCompany.setOnItemSelectedListener(this);

        spinnerOffice.setOnItemSelectedListener(this);
    }

    @Override
    public void dataFetched() {
        adapterCompany.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView == spinnerCompany) {
            adapterOffice = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, presenter.getOfficesForCompany(adapterCompany.getItem(i)));
            adapterOffice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerOffice.setAdapter(adapterOffice);
        }
        else if (adapterView == spinnerOffice) {
            Office office = presenter.getOffice((String) spinnerCompany.getSelectedItem(), (String) spinnerOffice.getSelectedItem());
            ((View) txtOfficeName.getParent()).setVisibility(i == 0 ? View.GONE : View.VISIBLE);

            txtOfficeName.setText(office.getFullname());
            txtAddress.setText(office.getAddress());
            txtEmployeeName.setText("");
//                spinnerRole
            txtEmail.setText("");
            txtPassword.setText("");
            txtRetypePassword.setText("");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.e(TAG, "nothing");
    }

    @OnEditorAction(R.id.txtRetypePassword)
    public boolean passwordDone(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_GO) {
            signUp(null);
            return true;
        }

        return false;
    }

    @OnClick(R.id.btnSignUp)
    public void signUp(View v) {
//        FirebaseFirestore.getInstance().collection("companies").addSnapshotListener((documentSnapshot, e) -> {
//            Log.e(TAG, "something");
//        });


//        if (!AuthenticationUtils.validateCredentials(txtEmail, txtPassword, txtRetypePassword))
//            return;
//
//        showLoadingDialog(null);
//
//        String email = txtEmail.getText().toString().trim();
//        String password = txtPassword.getText().toString();
//
//        presenter.trySignUp(email, password);
    }

    @Override
    public void signedUpSuccesfull() {
        setResult(Activity.RESULT_OK);
        finish();
    }
}
