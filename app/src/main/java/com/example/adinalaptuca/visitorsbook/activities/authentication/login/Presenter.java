package com.example.adinalaptuca.visitorsbook.activities.authentication.login;

import android.app.Activity;
import android.util.Log;
import com.example.adinalaptuca.visitorsbook.AppDelegate;
import com.example.adinalaptuca.visitorsbook.R;

public class Presenter implements LoginContract.Presenter {

    private LoginContract.View view;

    public Presenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public boolean isSignedIn() {
        return AppDelegate.getInstance(view.getContext()).getFirebaseAuth().getCurrentUser() != null;
    }

    @Override
    public void trySignIn(String email, String password) {
        AppDelegate.getInstance(view.getContext()).getFirebaseAuth()
                .signInWithEmailAndPassword(email.trim(), password)
                .addOnCompleteListener((Activity) view.getContext(), task -> {
                    view.dismissLoadingDialog();

                    if (task.isSuccessful()) {
                        view.goToMainscreen();
                    }
                    else
                        view.showToast(view.getContext().getResources().getString(R.string.authentication_failed));
                })
                .addOnFailureListener((Activity) view.getContext(), e -> {
                    view.dismissLoadingDialog();
                    view.showToast(view.getContext().getResources().getString(R.string.authentication_failed) + " ." + e.getMessage());
                    Log.e("login", "failed, e: " + e.getMessage());
                });
    }
}
