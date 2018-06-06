package com.adinalaptuca.visitorsbook.activities.authentication.signup;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.custom.BaseActivity;
import com.adinalaptuca.visitorsbook.activities.authentication.AuthenticationUtils;
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

        presenter.trySignUp(email, password);
    }

    @Override
    public void signedUpSuccesfull() {
        setResult(Activity.RESULT_OK);
        finish();
    }
}
