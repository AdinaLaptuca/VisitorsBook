package com.adinalaptuca.visitorsbook.activities.authentication.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ViewAnimator;

import com.adinalaptuca.visitorsbook.AppDelegate;
import com.adinalaptuca.visitorsbook.BuildConfig;
import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.authentication.signup.SignupActivity;
import com.adinalaptuca.visitorsbook.activities.authentication.AuthenticationUtils;
import com.adinalaptuca.visitorsbook.activities.main.MainActivity;
import com.adinalaptuca.visitorsbook.custom.BaseActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import rx.Observable;
import rx.Scheduler;
import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    private LoginContract.Presenter presenter;

    @BindView(R.id.txtEmail)
    protected EditText txtEmail;

    @BindView(R.id.txtPassword)
    protected EditText txtPassword;

    @BindView(R.id.imgSplash)
    protected View imgSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new Presenter(this);

        unbinder = ButterKnife.bind(this);

        Observable.timer(BuildConfig.DEBUG ? 2 : 4, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(time -> {

                    if (presenter.isSignedIn()) {
                        presenter.fetchLoginPath();
                    }
                    else {
                        ObjectAnimator animator = ObjectAnimator.ofFloat(imgSplash, "alpha", 1f, 0f);
                        animator.setDuration(600);
                        animator.setInterpolator(new DecelerateInterpolator());
                        animator.start();
                    }
        });
    }

    @OnEditorAction(R.id.txtPassword)
    public boolean passwordDone(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_GO) {
            signIn(null);
            return true;
        }

        return false;
    }

    @OnClick(R.id.btnSignIn)
    public void signIn(View v) {
        if (!AuthenticationUtils.validateCredentials(txtEmail, txtPassword, null))
            return;

        showLoadingDialog(null);
        presenter.trySignIn(txtEmail.getText().toString(), txtPassword.getText().toString());
    }

    @OnClick(R.id.btnCreateAccount)
    public void signUpClicked(View v) {
        startActivityForResult(new Intent(this, SignupActivity.class), SignupActivity.ACTIVITY_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MainActivity.ACTIVITY_RESULT && resultCode != Activity.RESULT_OK)
            finish();
//        else if (requestCode == MainActivity.ACTIVITY_RESULT && resultCode == Activity.RESULT_OK)
//            imgSplash.setAlpha(0f);
        else if (requestCode == SignupActivity.ACTIVITY_RESULT && resultCode == Activity.RESULT_OK)
            presenter.fetchLoginPath();
    }

    @Override
    public void loginPathFetched(String path) {
        AppDelegate.getInstance(this).setLoginPath(path);

        goToMainscreen();

        imgSplash.postDelayed(() -> {
            imgSplash.setAlpha(0f);
        }, 1500);
    }

    @Override
    public void goToMainscreen() {
        startActivityForResult(new Intent(this, MainActivity.class), MainActivity.ACTIVITY_RESULT);
    }

    @OnClick(R.id.btnForgotPassword)
    public void forgotPasswordClicked() {
        final EditText txtEmail = new EditText(this);
        txtEmail.setLines(1);
        txtEmail.setSingleLine(true);
        txtEmail.setImeOptions(EditorInfo.IME_ACTION_GO);
        txtEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        txtEmail.setHint(R.string.email);

        int margins = (int) getResources().getDimension(R.dimen.activity_horizontal_margin);
        txtEmail.setPadding(margins, margins, margins, margins);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.forgot_password)
                .setView(txtEmail)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.OK, null)
                .create();

        dialog.setOnShowListener(dialogInterface -> {
            InputMethodManager imm = (InputMethodManager) LoginActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(txtEmail, 0);

            final Button btnPositive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

            txtEmail.setOnKeyListener((v, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    btnPositive.callOnClick();
                    return true;
                }

                return false;
            });

            btnPositive.setOnClickListener(v -> {
                if (!AuthenticationUtils.validateCredentials(txtEmail, null, null))
                    return;

                dialog.dismiss();
                presenter.forgotPassword(txtEmail.getText().toString());
            });
        });

        dialog.show();
    }
}
