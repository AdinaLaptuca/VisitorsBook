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
import com.adinalaptuca.visitorsbook.activities.authentication.AuthenticationUtils;
import com.adinalaptuca.visitorsbook.custom.BaseActivity;
import com.adinalaptuca.visitorsbook.model.Office;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class SignupActivity extends BaseActivity implements SignupContract.View, AdapterView.OnItemSelectedListener {

    public static final int ACTIVITY_RESULT = 11;

    private static final String TAG = "SignupActivity";

    private SignupPresenter presenter;

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

    @BindView(R.id.txtEmployeeFirstName)
    protected EditText txtEmployeeFirstName;

    @BindView(R.id.txtEmployeeLastName)
    protected EditText txtEmployeeLastName;

    @BindView(R.id.spinnerRole)
    protected Spinner spinnerRole;
    private ArrayAdapter<String> adapterRoles;

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

        presenter = new SignupPresenter(this);
        presenter.fetchCompanies();
        presenter.fetchRoles();

        adapterCompany = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, presenter.getCompanies());
        adapterCompany.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCompany.setAdapter(adapterCompany);
        spinnerCompany.setOnItemSelectedListener(this);

        spinnerOffice.setOnItemSelectedListener(this);
    }

    @Override
    public void rolesFetched(List<String> roles) {
        adapterRoles = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
        adapterRoles.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(adapterRoles);
    }

    @Override
    public void companiesFetched() {
        adapterCompany.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView == spinnerCompany) {
            presenter.fetchOfficesForCompany(adapterCompany.getItem(i));
        }
        else if (adapterView == spinnerOffice) {
            Office office = presenter.getOffice((String) spinnerCompany.getSelectedItem(), (String) spinnerOffice.getSelectedItem());
            ((View) txtOfficeName.getParent()).setVisibility(i == 0 ? View.VISIBLE : View.GONE);
            txtAddress.setEnabled(i == 0);

            txtOfficeName.setText(i == 0 ? "" : office.getFullName());
            txtAddress.setText(i == 0 ? "" : office.getAddress());
            txtEmployeeFirstName.setText("");
            txtEmail.setText("");
            txtPassword.setText("");
            txtRetypePassword.setText("");
        }
    }

    @Override
    public void officesFetched(String companyName, List<String> result) {
        adapterOffice = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, result);
        adapterOffice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOffice.setAdapter(adapterOffice);
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
        if (!AuthenticationUtils.validateCredentials(txtEmail, txtPassword, txtRetypePassword))
            return;

        if (spinnerOffice.getSelectedItemPosition() == 0) {                 // new office

            if (txtOfficeName.getText().toString().trim().isEmpty()) {      // empty office name
                txtOfficeName.requestFocus();
                txtOfficeName.setError(getResources().getString(R.string.cant_be_empty));
                return;
            }
            else if (txtAddress.getText().toString().trim().isEmpty()) {      // empty address
                txtAddress.requestFocus();
                txtAddress.setError(getResources().getString(R.string.cant_be_empty));
                return;
            }

            else {                  // check if entered office name already exists
                Office office = presenter.getOffice(adapterCompany.getItem(spinnerCompany.getSelectedItemPosition()), txtOfficeName.getText().toString().trim());

                if (office != null) {
                    txtOfficeName.requestFocus();
                    txtOfficeName.setError(getResources().getString(R.string.newOfficeNameError_defined));
                    return;
                }
            }
        }

        showLoadingDialog(null);

        String companyName = adapterCompany.getItem(spinnerCompany.getSelectedItemPosition());
        String officeName = txtOfficeName.getText().toString().trim();
        String address = txtAddress.getText().toString().trim();
        String firstName = txtEmployeeFirstName.getText().toString().trim();
        String lastName = txtEmployeeLastName.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString();

        if (spinnerOffice.getSelectedItemPosition() == 0) {     // new office
            presenter.trySignUpUserNewCompany(companyName,
                    officeName,
                    address,
                    firstName,
                    lastName,
                    spinnerRole.getSelectedItem().toString(),
                    email,
                    password);
        }
        else {
            presenter.trySignUpInCompany(companyName,
                    officeName,
                    firstName,
                    lastName,
                    spinnerRole.getSelectedItem().toString(),
                    email, password);
        }
    }

    @Override
    public void signedUpSuccesfull() {
        setResult(Activity.RESULT_OK);
        finish();
    }
}
