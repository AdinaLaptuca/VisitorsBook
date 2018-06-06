package com.adinalaptuca.visitorsbook.activities.authentication.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.authentication.signup.SignupActivity;
import com.adinalaptuca.visitorsbook.activities.authentication.AuthenticationUtils;
import com.adinalaptuca.visitorsbook.activities.main.MainActivity;
import com.adinalaptuca.visitorsbook.custom.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    private LoginContract.Presenter presenter;

    @BindView(R.id.txtUsername)
    protected EditText txtUsername;

    @BindView(R.id.txtPassword)
    protected EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new Presenter(this);

        unbinder = ButterKnife.bind(this);

        if (presenter.isSignedIn())
            goToMainscreen();
    }

    @OnClick(R.id.btnSignIn)
    public void signIn(View v) {
        if (!AuthenticationUtils.validateCredentials(txtUsername, txtPassword, null))
            return;

        showLoadingDialog(null);
        presenter.trySignIn(txtUsername.getText().toString(), txtPassword.getText().toString());
    }

    @Override
    public void goToMainscreen() {
        startActivityForResult(new Intent(this, MainActivity.class), MainActivity.ACTIVITY_RESULT);
    }

    @OnClick(R.id.btnCreateAccount)
    public void signUp(View v) {
        startActivityForResult(new Intent(this, SignupActivity.class), SignupActivity.ACTIVITY_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MainActivity.ACTIVITY_RESULT && resultCode != Activity.RESULT_OK)
            finish();
        else if (requestCode == SignupActivity.ACTIVITY_RESULT && resultCode == Activity.RESULT_OK)
            goToMainscreen();
    }
}
