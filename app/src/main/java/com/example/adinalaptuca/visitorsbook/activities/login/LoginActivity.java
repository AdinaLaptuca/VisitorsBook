package com.example.adinalaptuca.visitorsbook.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.adinalaptuca.visitorsbook.R;
import com.example.adinalaptuca.visitorsbook.custom.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.txtUsername)
    protected EditText txtUsername;

    @BindView(R.id.txtPassword)
    protected EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        unbinder = ButterKnife.bind(this);
    }

    @OnClick(R.id.btnSignIn)
    public void signIn(View v) {
        startActivity(new Intent(this, VisitorsActivity.class));
    }
}
