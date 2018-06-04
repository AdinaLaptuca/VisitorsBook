package com.example.adinalaptuca.visitorsbook.activities.authentication.signup;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.adinalaptuca.visitorsbook.R;
import com.example.adinalaptuca.visitorsbook.activities.main.MainActivity;
import com.example.adinalaptuca.visitorsbook.custom.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupActivity extends BaseActivity {
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

        unbinder = ButterKnife.bind(this);
    }

    @OnClick(R.id.btnSignUp)
    public void signIn(View v) {
        if (!txtPassword.getText().toString().equals(txtRetypePassword.getText().toString()))
        {
            new AlertDialog.Builder(this)
                    .setMessage(R.string.passwords_dont_match)
                    .setNegativeButton(R.string.OK, null)
                    .show();
            return;
        }
    }
}
