package com.example.adinalaptuca.visitorsbook.activities.authentication.signup;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adinalaptuca.visitorsbook.AppDelegate;
import com.example.adinalaptuca.visitorsbook.R;
import com.example.adinalaptuca.visitorsbook.activities.authentication.AuthenticationUtils;
import com.example.adinalaptuca.visitorsbook.custom.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class SignupActivity extends BaseActivity implements SignupContract.View {

    public static final int ACTIVITY_RESULT = 11;

    private Presenter presenter;

    @BindView(R.id.txtUsername)
    protected EditText txtUsername;

    @BindView(R.id.txtPassword)
    protected EditText txtPassword;

    @BindView(R.id.txtRetypePassword)
    protected EditText txtRetypePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        presenter = new Presenter(this);

        unbinder = ButterKnife.bind(this);
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
        if (!AuthenticationUtils.validateCredentials(txtUsername, txtPassword, txtRetypePassword))
            return;

        showLoadingDialog(null);

        String email = txtUsername.getText().toString().trim();
        String password = txtPassword.getText().toString();
Log.e("signup", "create");
        AppDelegate.getInstance(this).getFirebaseAuth()
//                .setLanguageCode()
                .createUserWithEmailAndPassword(email, password)
                .addOnFailureListener(this, e -> {

                    Log.e("singup", "failed: " + e.getMessage());
                    dismissLoadingDialog();
                    Toast.makeText(SignupActivity.this, getResources().getString(R.string.registration_failed) + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                })
                .addOnCompleteListener(SignupActivity.this, task -> {
                    Log.e("singup", "complete: " + task);

                    Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                    dismissLoadingDialog();
                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        setResult(Activity.RESULT_OK);
                        finish();
                    }
                });
    }
}
